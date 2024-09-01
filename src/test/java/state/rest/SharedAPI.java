package state.rest;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import lombok.Data;
import lombok.NoArgsConstructor;
import rest.request.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static utils.APIUtils.prepareRequest;

@Data
@NoArgsConstructor
public class SharedAPI {
    public static final String SCHEMA_PATH = "schemas/";

    private Map<String, String> headers = new HashMap<>();
    private Map<String, Object> pathParams = new HashMap<>();
    private Map<String, Object> queryParams = new HashMap<>();
    private Map<String, Object> body;
    private String schemaFile;

    public Request prepare() {
        return prepareRequest(
                headers,
                pathParams,
                queryParams,
                body,
                SCHEMA_PATH + schemaFile
        );
    }

    public void addHeader(String header, String value) {
        headers.put(header, value);
    }

    public void addPathParam(String param, Object value) {
        pathParams.put(param, value);
    }

    public void addQueryParam(String param, Object value) {
        queryParams.put(param, value);
    }

    public void addFieldToBody(String field, Object value) {
        if (Objects.isNull(body))
            body = new HashMap<>();

        body.put(field, value);
    }

    public void validateJSONSchema(Response response, String schemaPath) {
        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }
}
