package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import rest.RestAPI;
import rest.request.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static utils.ObjectUtils.coalesce;

public class APIUtils {
    public static Request prepareRequest() {
        return prepareRequest(null, null, null, null, null);
    }

    public static Request prepareRequest(Map<String, Object> pathParams) {
        return prepareRequest(null, pathParams, null, null, null);
    }

    public static Request prepareRequest(Map<String, String> headers, Map<String, Object> pathParams, Map<String, Object> queryParams, Map<String, Object> body, String schemaFile) {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addRequestSpecification(RestAPI.prepare())
                .addHeaders(coalesce(headers, new HashMap<>()))
                .addPathParams(coalesce(pathParams, new HashMap<>()))
                .addQueryParams(coalesce(queryParams, new HashMap<>()))
                .build();

        if (Objects.nonNull(body)) {
            requestSpecification.contentType("application/json");
            requestSpecification.body(body);
        }

        return Request
                .builder()
                .requestSpecification(requestSpecification)
                .jsonSchemaPath(schemaFile)
                .build();
    }
}
