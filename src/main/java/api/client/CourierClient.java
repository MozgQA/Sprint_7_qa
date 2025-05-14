package api.client;

import api.models.courier.Courier;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private final static String ENDPOINT_CREATE_URL = "/api/v1/courier";
    private final static String ENDPOINT_LOGIN_URL = "/api/v1/courier/login";

    @Step("Send POST request to /api/v1/courier/login")
    public Response login(Courier courier){
        return given().log().all()
                .filter(new AllureRestAssured())
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(ENDPOINT_LOGIN_URL);
    }

    @Step("Send POST request to /api/v1/courier")
    public Response create(Courier courier){
        return given().log().all()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(ENDPOINT_CREATE_URL);
    }
}
