package ru.praktikum;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.steps.CourierSteps;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

public class CourierLoginTest {

    private String login;
    private String password;
    private String firstName;

    @Before
    public void setup() {
        this.login = RandomStringUtils.randomAlphabetic(10);
        this.password = RandomStringUtils.randomAlphabetic(10);
        this.firstName = RandomStringUtils.randomAlphabetic(10);

        CourierSteps.create(login, password, firstName);
    }

    @Test
    public void loginCourierSuccessReturnsId() {
        Integer id = CourierSteps.login(login, password)
                .statusCode(200)
                .extract().body().path("id");

        assertNotNull(id);
    }

    @Test
    public void loginCourierWrongLoginFails() {
        String wrongLogin = RandomStringUtils.randomAlphabetic(11);
        CourierSteps.login(wrongLogin, password)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    public void loginCourierWrongPasswordFails() {
        String wrongPassword = RandomStringUtils.randomAlphabetic(11);
        CourierSteps.login(login, wrongPassword)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    public void loginCourierWithoutLoginFails() {
        CourierSteps.login(null, password)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @After
    public void tearDown() {
        Integer id = CourierSteps.login(login, password)
                .extract().body().path("id");

        if (id != null) {
            CourierSteps.delete(id);
        }
    }
}
