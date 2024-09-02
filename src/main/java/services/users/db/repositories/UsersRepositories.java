package services.users.db.repositories;

public class UsersRepositories {
    public static final String FIND_USER = """
            SELECT TOP 1
                u.ID_User AS id,
                u.Name AS name,
                u.Email AS email
            FROM Users u
            """;

    public static final String FIND_USER_BY_ID = FIND_USER + "WHERE u.ID_User = %s";
}
