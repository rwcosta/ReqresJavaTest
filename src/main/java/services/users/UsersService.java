package services.users;

import java.util.Map;

public interface UsersService {
    Map<String, Object> findRandomUser();
    Map<String, Object> findUserByID(String userID);
}
