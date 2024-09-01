package factory.users;

import com.github.javafaker.Faker;
import request.users.UserRequest;

import java.util.Locale;

public class UserFactory {
    private final Faker faker = new Faker(new Locale("pt-BR"));

    public UserRequest registerUser() {
        return UserRequest
                .builder()
                .name("%s %s".formatted(faker.name().firstName(), faker.name().lastName()))
                .job(faker.job().title())
                .build();
    }
}
