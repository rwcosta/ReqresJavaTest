package steps.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import factory.users.UserFactory;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import org.testng.Assert;
import org.testng.SkipException;
import services.users.UsersService;
import services.users.rest.UsersServiceRest;
import state.SharedData;
import state.users.UsersData;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static validation.AssertsUtils.validate;

public class UsersSteps {
    private final SharedData sharedData;
    private final UsersData usersData;
    private final UsersService usersService;
    private final UserFactory userFactory = new UserFactory();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UsersSteps(SharedData sharedData, UsersData usersData) {
        this.sharedData = sharedData;
        this.usersData = usersData;
        this.usersService = new UsersServiceRest();
    }

    @Dado("(que )preparo uma requisicao para consultar/remover um usuario")
    public void quePreparoUmaRequisicaoParaConsultarUmUsuario() {
        usersData.setUser(usersService.findRandomUser());
        if (Objects.isNull(usersData.getUser()))
            throw new SkipException("There is no user registered");

        sharedData.getSharedAPI().addPathParam("id", usersData.getUser().get("id"));
    }

    @Dado("(que )preparo uma requisicao para cadastrar um usuario")
    public void quePreparoUmaRequisicaoParaCadastrarUmUsuario() {
        sharedData
                .getSharedAPI()
                .setBody(objectMapper.convertValue(userFactory.registerUser(), Map.class));
    }

    @Dado("(que )preparo uma requisicao para alterar parcialmente um usuario informando todos os campos")
    @Dado("(que )preparo uma requisicao para alterar um usuario")
    public void quePreparoUmaRequisicaoParaAlterarUmUsuario() {
        quePreparoUmaRequisicaoParaConsultarUmUsuario();
        quePreparoUmaRequisicaoParaCadastrarUmUsuario();
    }

    @Dado("(que )preparo uma requisicao para alterar parcialmente um usuario apenas o campo {word}")
    public void quePreparoUmaRequisicaoParaAlterarParcialmenteUmUsuarioInformandoApenasOCampo(String field) {
        quePreparoUmaRequisicaoParaConsultarUmUsuario();

        Map<String, Object> requestBody = objectMapper.convertValue(userFactory.registerUser(), Map.class);
        sharedData.getSharedAPI().setBody(Map.of(field, requestBody.get(field)));
    }

    @E("os elementos listados sao validos")
    public void osElementosListadosSaoValidos() {
        List<Map<String, Object>> users = sharedData.getResponse().jsonPath().getList("data");
        for (Map<String, Object> user : users) {
            Assert.assertEquals(user, usersService.findUserByID(user.get("id").toString()));
        }
    }

    @E("o usuario consultado deve ser valido")
    public void oUsuarioConsultadoDeveSerValido() {
        Map<String, Object> user = sharedData.getResponse().jsonPath().get("data");
        Assert.assertEquals(user, usersService.findUserByID(user.get("id").toString()));
    }

    @E("o usuario deve ter sido cadastrado corretamente")
    public void oUsuarioDeveTerSidoCadastradoCorretamente() {
        // Skipping validation because Reqres API doesn't persist data
        if (true) {
            return;
        }

        Map<String, Object> user = sharedData.getResponse().jsonPath().get();
        Assert.assertEquals(user, usersService.findUserByID(user.get("id").toString()));
    }

    @E("o usuario deve ter sido alterado corretamente")
    public void oUsuarioDeveTerSidoAlteradoCorretamente() {
        // Skipping validation because Reqres API doesn't persist data
        if (true) {
            return;
        }

        Map<String, Object> user = sharedData.getResponse().jsonPath().get();
        Assert.assertEquals(user, usersService.findUserByID(usersData.getUser().get("id").toString()));
    }

    @E("a alteracao parcial do usuario deve ter sido feita corretamente")
    public void aAlteracaoParcialDoUsuarioDeveTerSidoFeitaCorretamente() {
        // Skipping validation because Reqres API doesn't persist data
        if (true) {
            return;
        }

        Map<String, Object> user = sharedData.getResponse().jsonPath().get();
        Map<String, Object> userExpected = usersService.findUserByID(usersData.getUser().get("id").toString());

        userExpected.putAll(sharedData.getSharedAPI().getBody());

        validate(user, userExpected);
    }

    @E("o usuario deve ter sido removido corretamente")
    public void oUsuarioDeveTerSidoRemovidoCorretamente() {
        // Skipping validation because Reqres API doesn't persist data
        if (true) {
            return;
        }

        Assert.assertNull(usersService.findUserByID(usersData.getUser().get("id").toString()));
    }
}
