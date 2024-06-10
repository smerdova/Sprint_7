package ru.praktikum;

import org.junit.Assert;
import org.junit.Test;
import ru.praktikum.model.OrderListRequest;
import ru.praktikum.steps.OrderSteps;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class OrderListTest {

    @Test
    public void ordersReturnsList() {
        OrderListRequest query = new OrderListRequest(null, null, 2, 0);
        List<Map<String, Object>> orders = OrderSteps.get(query)
                .statusCode(200)
                .extract().body().path("orders");

        Assert.assertFalse(orders.isEmpty());
        assertNotNull(orders.get(0).get("id"));
    }
}
