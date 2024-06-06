package ru.praktikum;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.steps.CourierSteps;

import static org.hamcrest.Matchers.is;

public class CourierCreateTest {

    private String login;
    private String password;
    private String firstName;

    @Before
    public void setup() {
        this.login = RandomStringUtils.randomAlphabetic(10);
        this.password = RandomStringUtils.randomAlphabetic(10);
        this.firstName = RandomStringUtils.randomAlphabetic(10);
    }

    @Test
    public void createCourierReturnsSuccess() {
        CourierSteps.create(login, password, firstName)
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    public void createTwoTheSameCouriersFails() {
        CourierSteps.create(login, password, firstName)
                .statusCode(201)
                .body("ok", is(true));

        CourierSteps.create(login, password, firstName)
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    public void createCourierWithoutLoginFails() {
        CourierSteps.create(null, password, firstName)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierWithoutPasswordFails() {
        CourierSteps.create(login, null, firstName)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierWithoutFirstNameSuccess() {
        CourierSteps.create(login, password, null)
                .statusCode(201)
                .body("ok", is(true));
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
