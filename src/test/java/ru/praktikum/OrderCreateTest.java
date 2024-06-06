package ru.praktikum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.steps.OrderSteps;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class OrderCreateTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][] {
                        {
                                "Naruto",
                                "Uchiha",
                                "Konoha, 142 apt.",
                                "4",
                                "+7 800 355 35 35",
                                5,
                                "2020-06-06",
                                "Saske, come back to Konoha",
                                new String[]{"BLACK"}
                        },
                        {
                                "Naruto",
                                "Uchiha",
                                "Konoha, 142 apt.",
                                "4",
                                "+7 800 355 35 35",
                                5,
                                "2020-06-06",
                                "Saske, come back to Konoha",
                                new String[]{"GREY"}
                        },
                        {
                                "Naruto",
                                "Uchiha",
                                "Konoha, 142 apt.",
                                "4",
                                "+7 800 355 35 35",
                                5,
                                "2020-06-06",
                                "Saske, come back to Konoha",
                                new String[]{"BLACK", "GRAY"}
                        },
                        {
                                "Naruto",
                                "Uchiha",
                                "Konoha, 142 apt.",
                                "4",
                                "+7 800 355 35 35",
                                5,
                                "2020-06-06",
                                "Saske, come back to Konoha",
                                null
                        },
                });
    }

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Integer rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;

    private Integer track;

    public OrderCreateTest(String firstName, String lastName, String address,
                           String metroStation, String phone, Integer rentTime,
                           String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Before
    public void setup() {
    }

    @Test
    public void createOrderReturnsSuccessTrack() {
        track = OrderSteps.create(firstName, lastName, address,
                        metroStation, phone, rentTime, deliveryDate, comment, color)
                .statusCode(201)
                .extract().body().path("track");

        assertNotNull(track);
    }

    @After
    public void tearDown() {
        if (track != null) {
            OrderSteps.cancel(track);
        }
    }
}
