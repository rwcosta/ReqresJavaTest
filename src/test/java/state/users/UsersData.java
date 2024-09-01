package state.users;

import java.util.Map;

public class UsersData {
    private final ThreadLocal<Map<String, Object>> user = new ThreadLocal<>();

    public Map<String, Object> getUser() {
        return user.get();
    }

    public void setUser(Map<String, Object> user) {
        this.user.set(user);
    }
}
