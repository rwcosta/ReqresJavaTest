package exception.rest;

import io.restassured.http.Method;
import io.restassured.response.Response;

public class InvalidResponseException extends RuntimeException {
    public InvalidResponseException(Method method, String path, Integer expectedStatusCode, Response response) {
        super(
                "Invalid response from %s %s Status Code expected %s but returned %s"
                        .formatted(
                                method,
                                path,
                                expectedStatusCode,
                                response.getStatusCode()
                        )
        );
    }
}
