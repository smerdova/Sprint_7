package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.praktikum.Configuration;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class CourierSteps {
    @Step("Отправляем POST запрос на /api/v1/courier")
    public static ValidatableResponse create(String login, String password, String firstName) {
        Map<String, Object> jsonAsMap = new HashMap<>();
        if (login != null) {
            jsonAsMap.put("login", login);
        }
        if (password != null) {
            jsonAsMap.put("password", password);
        }
        if (firstName != null) {
            jsonAsMap.put("firstName", firstName);
        }

        return given()
                .contentType(ContentType.JSON)
                .baseUri(Configuration.BASE_URL)
                .body(jsonAsMap)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    @Step("Отправляем POST запрос на /api/v1/courier/login")
    public static ValidatableResponse login(String login, String password) {
        Map<String, Object> jsonAsMap = new HashMap<>();
        if (login != null) {
            jsonAsMap.put("login", login);
        }
        if (password != null) {
            jsonAsMap.put("password", password);
        }

        return given()
                .contentType(ContentType.JSON)
                .baseUri(Configuration.BASE_URL)
                .body(jsonAsMap)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    @Step("Отправляем DELETE запрос на /api/v1/courier/{id}")
    public static ValidatableResponse delete(int id) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(Configuration.BASE_URL)
                .pathParam("id", id)
                .when()
                .delete("/api/v1/courier/{id}")
                .then();
    }
}
