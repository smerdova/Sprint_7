package ru.praktikum;

import com.github.javafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.model.CourierCreateRequest;
import ru.praktikum.model.CourierLoginRequest;
import ru.praktikum.steps.CourierSteps;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

public class CourierLoginTest {

    private CourierLoginRequest loginRequest;

    @Before
    public void setup() {
        Faker faker = new Faker();
        String login = faker.name().username();
        String password = faker.internet().password();
        String firstName = faker.name().firstName();

        CourierCreateRequest createRequest = new CourierCreateRequest(login, password, firstName);
        CourierSteps.create(createRequest);

        this.loginRequest = new CourierLoginRequest(login, password);
    }

    @Test
    public void loginCourierSuccessReturnsId() {
        Integer id = CourierSteps.login(loginRequest)
                .statusCode(200)
                .extract().body().path("id");

        assertNotNull(id);
    }

    @Test
    public void loginCourierWrongLoginFails() {
        Faker faker = new Faker();
        CourierLoginRequest wrongLoginRequest = new CourierLoginRequest(
                faker.name().username(),
                loginRequest.getPassword()
        );
        CourierSteps.login(wrongLoginRequest)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    public void loginCourierWrongPasswordFails() {
        Faker faker = new Faker();
        CourierLoginRequest wrongPasswordRequest = new CourierLoginRequest(
                loginRequest.getLogin(),
                faker.internet().password()
        );
        CourierSteps.login(wrongPasswordRequest)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    public void loginCourierWithoutLoginFails() {
        CourierLoginRequest noLoginRequest = new CourierLoginRequest(
                null,
                loginRequest.getPassword()
        );
        CourierSteps.login(noLoginRequest)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @After
    public void tearDown() {
        Integer id = CourierSteps.login(loginRequest)
                .extract().body().path("id");

        if (id != null) {
            CourierSteps.delete(id);
        }
    }
}
