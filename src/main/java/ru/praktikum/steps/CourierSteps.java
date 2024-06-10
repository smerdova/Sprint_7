package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.praktikum.Configuration;
import ru.praktikum.model.CourierCreateRequest;
import ru.praktikum.model.CourierLoginRequest;

import static io.restassured.RestAssured.given;

public abstract class CourierSteps {
    @Step("Отправляем POST запрос на /api/v1/courier")
    public static ValidatableResponse create(CourierCreateRequest request) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(Configuration.BASE_URL)
                .body(request)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    @Step("Отправляем POST запрос на /api/v1/courier/login")
    public static ValidatableResponse login(CourierLoginRequest request) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(Configuration.BASE_URL)
                .body(request)
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
