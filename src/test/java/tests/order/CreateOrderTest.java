package tests.order;

import api.client.OrderClient;
import api.models.order.Colors;
import api.models.order.Order;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import tests.base.BaseTest;

import java.util.Collections;
import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseTest {
    private Order order;
    private OrderClient orderClient;

    public CreateOrderTest(Order order) {
        this.order = order;
    }

    @Override
    public void setUp() {
        super.setUp();
        RestAssured.filters(new AllureRestAssured());
        orderClient = new OrderClient();
    }

    @After
    public void tearDown(){
        order = null;
        orderClient = null;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1}")
    public static Object[][] getOrderParameters() {
        return new Object[][]{
                {new Order("Petr", "Ershov", "Saint-Petersburg", "2",
                        "8-800-555-35-35", 6, "2020-12-14", "Millioner, filantrop, playboy",
                        List.of(Colors.GRAY.name()))},

                {new Order("Dmitry", "Egorov", "Moscow", "10",
                        "+7-999-999-99-99", 2, "2022-07-25", "I want to make a lot of money",
                        List.of(Colors.GRAY.name(), Colors.BLACK.name()))},

                {new Order("Vladimir", "Petrov", "Sochi", "1",
                        "+7-123-456-78-90", 8, "2014-02-28",
                        "not bad!!!!", Collections.emptyList())}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Успешное создание заказа")
    public void createOrderTest() {
        Response response = orderClient.create(order);
        response.then().log().all()
                .assertThat().body("track", Matchers.notNullValue()).and().statusCode(SC_CREATED);
    }

}
