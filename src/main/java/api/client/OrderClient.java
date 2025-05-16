package api.client;

import api.models.order.Order;
import api.models.order.OrderList;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.ProxySpecification;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;


public class OrderClient {
    private final static String ENDPOINT_ORDERS_URL = "/api/v1/orders";
    private final RequestSpecification spec;

    public OrderClient() {
        this.spec = given()
                .header("Content-Type", "application/json")
                .log().all();
    }

    @Step("Send GET request to /api/v1/orders")
    public OrderList getOrderList() {
        return spec
                .when()
                .get(ENDPOINT_ORDERS_URL)
                .then()
                .extract()
                .body()
                .as(OrderList.class);
    }

    @Step("Send POST request to /api/v1/orders")
    public Response create(Order order) {
        return spec
                .body(order)
                .when()
                .post(ENDPOINT_ORDERS_URL);
    }

}
