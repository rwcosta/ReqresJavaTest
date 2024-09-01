package services.users.rest;

import exception.rest.InvalidResponseException;
import io.restassured.http.Method;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import rest.RestAPI;
import services.users.UsersService;

import java.util.List;
import java.util.Map;

import static utils.APIUtils.prepareRequest;
import static utils.ListUtils.getRandomElement;

@Slf4j
public class UsersServiceRest implements UsersService {
    private static final String GET_USER_PATH = "/users";
    private static final String GET_USER_ID_PATH = GET_USER_PATH + "/{id}";

    private final RestAPI restAPI = new RestAPI();

    @Override
    public Map<String, Object> findRandomUser() {
        log.debug("Searching for user");

        Response userAPI = restAPI.doRequest(
                prepareRequest(),
                Method.GET,
                GET_USER_PATH
        );

        if (userAPI.getStatusCode() != HttpStatus.SC_OK) {
            throw new InvalidResponseException(Method.GET, GET_USER_PATH, HttpStatus.SC_OK, userAPI);
        }

        List<Map<String, Object>> users = userAPI.jsonPath().getList("data");
        return users.isEmpty() ? null : getRandomElement(users);
    }

    @Override
    public Map<String, Object> findUserByID(String userID) {
        log.debug("Finding user by ID %s".formatted(userID));

        Response userAPI = requestUserID(userID);
        if (userAPI.getStatusCode() != HttpStatus.SC_OK) {
            throw new InvalidResponseException(Method.GET, GET_USER_ID_PATH, HttpStatus.SC_OK, userAPI);
        }

        return userAPI.jsonPath().get("data");
    }

    private Response requestUserID(String userID) {
        return restAPI.doRequest(
                prepareRequest(Map.of("id", userID)),
                Method.GET,
                GET_USER_ID_PATH
        );
    }
}
