package tests.order;

import api.client.OrderClient;
import api.models.order.OrderList;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.base.BaseTest;

import static io.restassured.RestAssured.given;

public class GetOrderListTest extends BaseTest {
    private OrderClient orderClient;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        orderClient = new OrderClient();
    }

    @After
    public void tearDown(){
        orderClient = null;
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Получение списка заказа и проверка статуса ответа")
    public void getOrderListTest() {
        OrderList orderList = orderClient.getOrderList();
        Assert.assertThat(orderList.getOrders(), Matchers.not(Matchers.empty()));
    }
}
