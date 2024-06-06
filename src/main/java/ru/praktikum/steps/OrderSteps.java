package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import ru.praktikum.Configuration;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class OrderSteps {
    @Step("Отправляем POST запрос на /api/v1/orders")
    public static ValidatableResponse create(
                            String firstName,
                            String lastName,
                            String address,
                            String metroStation,
                            String phone,
                            Integer rentTime,
                            String deliveryDate,
                            String comment,
                            String[] color) {
        Map<String, Object> jsonAsMap = new HashMap<>();
        if (firstName != null) {
            jsonAsMap.put("firstName", firstName);
        }
        if (lastName != null) {
            jsonAsMap.put("lastName", lastName);
        }
        if (address != null) {
            jsonAsMap.put("address", address);
        }
        if (metroStation != null) {
            jsonAsMap.put("metroStation", metroStation);
        }
        if (phone != null) {
            jsonAsMap.put("phone", phone);
        }
        if (rentTime != null) {
            jsonAsMap.put("rentTime", rentTime);
        }
        if (deliveryDate != null) {
            jsonAsMap.put("deliveryDate", deliveryDate);
        }
        if (comment != null) {
            jsonAsMap.put("comment", comment);
        }
        if (color != null) {
            jsonAsMap.put("color", color);
        }

        return given()
                .contentType(ContentType.JSON)
                .baseUri(Configuration.BASE_URL)
                .body(jsonAsMap)
                .when()
                .post("/api/v1/orders")
                .then();
    }

    @Step("Отправляем GET запрос на /api/v1/orders")
    public static ValidatableResponse get(Integer courierId, String nearestStation,
                                             Integer limit, Integer page) {
        RequestSpecification request = given()
                .contentType(ContentType.JSON)
                .baseUri(Configuration.BASE_URL);

        if (courierId != null) {
            request.param("courierId", courierId);
        }
        if (nearestStation != null) {
            request.param("nearestStation", nearestStation);
        }
        if (limit != null) {
            request.param("limit", limit);
        }
        if (page != null) {
            request.param("page", page);
        }

        return request
                .when()
                .get("/api/v1/orders")
                .then();
    }

    @Step("Отправляем PUT запрос на /api/v1/orders/cancel")
    public static ValidatableResponse cancel(int track) {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("track", track);

        return given()
                .contentType(ContentType.JSON)
                .baseUri(Configuration.BASE_URL)
                .body(jsonAsMap)
                .when()
                .put("/api/v1/orders/cancel")
                .then();
    }
}
