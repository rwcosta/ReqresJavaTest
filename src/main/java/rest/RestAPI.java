package rest;

import config.rest.RestConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import rest.request.Request;

import java.util.Objects;

import static io.restassured.RestAssured.given;

public class RestAPI {
    private static final RestConfig restConfig = RestConfig.loadConfig();

    public Response doRequest(Request request, Method method, String path) {
        request.getRequestSpecification().filter(new AllureRestAssured());

        return given()
                .spec(request.getRequestSpecification())
                .log().all()
                .when()
                .request(method, path)
                .then()
                .log().all()
                .extract()
                .response();
    }

    public static RequestSpecification prepare() {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri(restConfig.getBaseURL())
                .build();

        if (Objects.nonNull(restConfig.getPort()))
            requestSpecification.port(restConfig.getPort());

        if (Objects.nonNull(restConfig.getBasePath()))
            requestSpecification.basePath(restConfig.getBasePath());

        if (Objects.nonNull(restConfig.getHeaders()))
            requestSpecification.headers(restConfig.getHeaders());

        return requestSpecification;
    }
}
