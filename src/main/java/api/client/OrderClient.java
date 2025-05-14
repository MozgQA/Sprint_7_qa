package api.client;

import api.models.order.Order;
import api.models.order.OrderList;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class OrderClient {
    private final static String ENDPOINT_ORDERS_URL = "/api/v1/orders";

    @Step("Send GET request to /api/v1/orders")
    public OrderList getOrderList() {
        return given()
                .header("Content-Type", "application/json")
                .log().all()
                .when()
                .get(ENDPOINT_ORDERS_URL)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .body()
                .as(OrderList.class);
    }

    @Step("Send POST request to /api/v1/orders")
    public Response create(Order order) {
        return given().log().all()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ENDPOINT_ORDERS_URL);
    }

}
