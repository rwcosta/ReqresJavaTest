package steps;

import errors.Errors;
import io.cucumber.java.ParameterType;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.qameta.allure.Allure;
import io.restassured.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import rest.RestAPI;
import state.SharedData;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static state.rest.SharedAPI.SCHEMA_PATH;

@Slf4j
public class SharedSteps {
    private static final String LIST_OF_VALUES = "listOfValues";
    private static final String PAGE_FIELD = "page";
    private static final String PER_PAGE_FIELD = "per_page";

    private final SharedData sharedData;
    private final RestAPI restAPI;

    public SharedSteps(SharedData sharedData, RestAPI restAPI) {
        this.sharedData = sharedData;
        this.restAPI = restAPI;
    }

    @ParameterType("GET|POST|PUT|PATCH|DELETE")
    public Method method(String method) {
        return Method.valueOf(method);
    }

    @ParameterType("(?:[^,]*)(?:,\\s?[^,]*)*")
    public List<String> listOfValues(String values) {
        return (values.isEmpty() || values.equalsIgnoreCase(LIST_OF_VALUES)) ? null : Arrays.asList(values.split(",\\s?"));
    }

    @Dado("o contrato {word}")
    public void queOContrato(String schemaPath) {
        sharedData.getSharedAPI().setSchemaFile(schemaPath);
    }

    @Dado("(que )preparo uma requisicao com o path param {word} de valor {word}")
    @E("adiciono o path param {word} com valor {word}")
    public void adicionoOPathParamComValor(String param, Object valor) {
        sharedData.getSharedAPI().addPathParam(param, valor);
    }

    @Dado("(que )preparo uma/a requisicao com o query param {word} com valor {word}")
    @E("adiciono o query param {word} com valor {word}")
    public void adicionoOQueryParamComValor(String param, Object valor) {
        sharedData.getSharedAPI().addQueryParam(param, valor);
    }

    @Dado("(que )preparo uma/a requisicao com o campo {word} no body com valor {word}")
    @E("adiciono o campo {word} no body com valor {word}")
    public void adicionoOCampoNoBodyComValor(String param, Object valor) {
        sharedData.getSharedAPI().addFieldToBody(param, valor);
    }

    @Dado("(que )envio uma requisicao {method} para o path {word}")
    public void envioUmaRequisicaoParaOPath(Method method, String path) {
        sharedData.setRequest(sharedData.getSharedAPI().prepare());
        sharedData.setResponse(restAPI.doRequest(sharedData.getRequest(), method, path));

        String responseTime = "Response Time: %s ms".formatted(sharedData.getResponse().getTime());
        Allure.addAttachment("Response time", responseTime);
        log.debug(responseTime);
    }

    @Entao("deve retornar o status code {int}")
    public void deveRetornarOStatusCode(Integer statusCode) {
        Assert.assertEquals(sharedData.getResponse().getStatusCode(), statusCode);
    }

    @E("o contrato deve ser valido")
    public void oContratoDeveSerValido() {
        sharedData.getSharedAPI().validateJSONSchema(sharedData.getResponse(), SCHEMA_PATH + sharedData.getSharedAPI().getSchemaFile());
    }

    @E("deve listar a pagina {int}")
    public void deveListarAPagina(Integer page) {
        Assert.assertEquals(sharedData.getResponse().jsonPath().get(PAGE_FIELD), page);
    }

    @E("a lista {word} deve ter {int} resultados")
    public void aListaDeveTerResultados(String list, Integer perPage) {
        Map<String, Object> response = sharedData.getResponse().jsonPath().get();
        Assert.assertEquals(response.get(PER_PAGE_FIELD), perPage);

        List<Map<String, Object>> elements = (List<Map<String, Object>>) response.get(list);
        Assert.assertEquals(elements.size(), perPage);
    }

    @E("deve retornar a mensagem de erro {word}( com os valores ){listOfValues}")
    public void deveRetornarAMensagemDeErroComOsValores(String error, List<String> listOfValues) {
        Assert.assertEquals(this.sharedData.getResponse().jsonPath().get("error"), Errors.getMessage(error, listOfValues));
    }
}
