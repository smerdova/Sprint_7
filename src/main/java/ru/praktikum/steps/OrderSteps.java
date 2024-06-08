package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import ru.praktikum.Configuration;
import ru.praktikum.model.OrderCancelRequest;
import ru.praktikum.model.OrderCreateRequest;
import ru.praktikum.model.OrderListRequest;

import static io.restassured.RestAssured.given;

public abstract class OrderSteps {
    @Step("Отправляем POST запрос на /api/v1/orders")
    public static ValidatableResponse create(OrderCreateRequest order) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(Configuration.BASE_URL)
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then();
    }

    @Step("Отправляем GET запрос на /api/v1/orders")
    public static ValidatableResponse get(OrderListRequest query) {
        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .baseUri(Configuration.BASE_URL);

        if (query.getCourierId() != null) {
            request.param("courierId", query.getCourierId());
        }
        if (query.getNearestStation() != null) {
            request.param("nearestStation", query.getNearestStation());
        }
        if (query.getLimit() != null) {
            request.param("limit", query.getLimit());
        }
        if (query.getPage() != null) {
            request.param("page", query.getPage());
        }

        return request
                .when()
                .get("/api/v1/orders")
                .then();
    }

    @Step("Отправляем PUT запрос на /api/v1/orders/cancel")
    public static ValidatableResponse cancel(OrderCancelRequest request) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(Configuration.BASE_URL)
                .body(request)
                .when()
                .put("/api/v1/orders/cancel")
                .then();
    }
}
