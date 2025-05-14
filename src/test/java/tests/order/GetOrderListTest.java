package tests.order;

import api.client.OrderClient;
import api.models.order.OrderList;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.base.BaseTest;

import static io.restassured.RestAssured.given;

public class GetOrderListTest extends BaseTest {

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Получение списка заказа и проверка статуса ответа")
    public void getOrderListTest(){
        OrderClient orderClient = new OrderClient();
        OrderList orderList = orderClient.getOrderList();
        Assert.assertThat(orderList.getOrders(), Matchers.not(Matchers.empty()));
    }
}
