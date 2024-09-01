package state;

import io.restassured.response.Response;
import rest.request.Request;
import state.rest.SharedAPI;

import static java.lang.ThreadLocal.withInitial;

public class SharedData {
    private final ThreadLocal<Request> request = withInitial(Request::new);
    private final ThreadLocal<Response> response = new ThreadLocal<>();
    private final ThreadLocal<SharedAPI> sharedAPI = withInitial(SharedAPI::new);

    public Request getRequest() {
        return request.get();
    }

    public void setRequest(Request request) {
        this.request.set(request);
    }

    public Response getResponse() {
        return response.get();
    }

    public void setResponse(Response response) {
        this.response.set(response);
    }

    public SharedAPI getSharedAPI() {
        return sharedAPI.get();
    }

    public void setSharedAPI(SharedAPI sharedAPI) {
        this.sharedAPI.set(sharedAPI);
    }
}
