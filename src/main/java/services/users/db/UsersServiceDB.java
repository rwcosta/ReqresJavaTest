package services.users.db;

import db.GenericConnection;
import lombok.extern.slf4j.Slf4j;
import services.users.UsersService;

import java.util.Map;

import static db.DialectEnum.SQL_SERVER;
import static services.users.db.repositories.UsersRepositories.FIND_USER;
import static services.users.db.repositories.UsersRepositories.FIND_USER_BY_ID;
import static utils.QueriesUtils.RANDOM_CHECKSUM;

@Slf4j
public class UsersServiceDB implements UsersService {
    GenericConnection genericConnection = new GenericConnection(SQL_SERVER);

    @Override
    public Map<String, Object> findRandomUser() {
        log.debug("Searching random user");
        return genericConnection.getResult(FIND_USER + RANDOM_CHECKSUM.formatted("WHERE", "u.ID_User"));
    }

    @Override
    public Map<String, Object> findUserByID(String userID) {
        log.debug("Searching user by ID %s".formatted(userID));
        return genericConnection.getResult(FIND_USER_BY_ID.formatted(userID));
    }
}
