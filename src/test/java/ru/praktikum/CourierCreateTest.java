package ru.praktikum;

import com.github.javafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.model.CourierCreateRequest;
import ru.praktikum.model.CourierLoginRequest;
import ru.praktikum.steps.CourierSteps;

import static org.hamcrest.Matchers.is;

public class CourierCreateTest {

    private CourierCreateRequest request;

    @Before
    public void setup() {
        Faker faker = new Faker();
        this.request = new CourierCreateRequest(
                faker.name().username(),
                faker.internet().password(),
                faker.name().firstName()
        );
    }

    @Test
    public void createCourierReturnsSuccess() {
        CourierSteps.create(request)
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    public void createTwoTheSameCouriersFails() {
        CourierSteps.create(request)
                .statusCode(201)
                .body("ok", is(true));

        CourierSteps.create(request)
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    public void createCourierWithoutLoginFails() {
        CourierCreateRequest wrongRequest = new CourierCreateRequest(
                null,
                request.getPassword(),
                request.getFirstName()
        );
        CourierSteps.create(wrongRequest)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierWithoutPasswordFails() {
        CourierCreateRequest wrongRequest = new CourierCreateRequest(
                request.getLogin(),
                null,
                request.getFirstName()
        );
        CourierSteps.create(wrongRequest)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierWithoutFirstNameSuccess() {
        request.setFirstName(null);
        CourierSteps.create(request)
                .statusCode(201)
                .body("ok", is(true));
    }

    @After
    public void tearDown() {
        CourierLoginRequest loginRequest = new CourierLoginRequest(request.getLogin(), request.getPassword());
        Integer id = CourierSteps.login(loginRequest)
                .extract().body().path("id");

        if (id != null) {
            CourierSteps.delete(id);
        }
    }
}
