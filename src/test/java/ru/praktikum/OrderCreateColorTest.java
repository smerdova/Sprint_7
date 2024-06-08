package ru.praktikum;

import com.github.javafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.model.OrderCancelRequest;
import ru.praktikum.model.OrderCreateRequest;
import ru.praktikum.steps.OrderSteps;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class OrderCreateColorTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][] {
                        {
                                new String[] {"BLACK"}
                        },
                        {
                                new String[] {"GREY"}
                        },
                        {
                                new String[] {"BLACK", "GRAY"}
                        },
                        {
                                null
                        },
                });
    }

    private final OrderCreateRequest orderCreateRequest;

    private Integer track;

    public OrderCreateColorTest(String[] color) {
        Faker faker = new Faker();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        this.orderCreateRequest = new OrderCreateRequest(faker.name().firstName(),
                faker.name().lastName(),
                faker.address().fullAddress(),
                faker.idNumber().valid(),
                faker.phoneNumber().cellPhone(),
                faker.number().numberBetween(1, 20),
                sdf.format(faker.date().future(1, TimeUnit.DAYS)),
                faker.food().vegetable(),
                color);
    }

    @Before
    public void setup() {
    }

    @Test
    public void createOrderReturnsSuccessTrack() {
        track = OrderSteps.create(orderCreateRequest)
                .statusCode(201)
                .extract().body().path("track");

        assertNotNull(track);
    }

    @After
    public void tearDown() {
        if (track != null) {
            OrderCancelRequest request = new OrderCancelRequest(track);
            OrderSteps.cancel(request);
        }
    }
}
