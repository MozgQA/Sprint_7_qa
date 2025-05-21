package api.client;

import api.models.courier.Courier;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private final static String ENDPOINT_COURIER_URL = "/api/v1/courier";
    private final static String ENDPOINT_COURIER_LOGIN_URL = "/api/v1/courier/login";
    private final RequestSpecification spec;

    public CourierClient() {
        this.spec = given()
                .header("Content-Type", "application/json")
                .log().all();
    }

    @Step("Send POST request to /api/v1/courier/login")
    public Response login(Courier courier) {
        return spec
                .filter(new AllureRestAssured())
                .body(courier)
                .when()
                .post(ENDPOINT_COURIER_LOGIN_URL);
    }

    @Step("Send POST request to /api/v1/courier")
    public Response create(Courier courier) {
        return spec
                .body(courier)
                .when()
                .post(ENDPOINT_COURIER_URL);
    }

    @Step("Delete courier")
    public void deleteCourier(Courier courier){
        Integer id = spec
                        .body(courier)
                        .when()
                        .post (ENDPOINT_COURIER_LOGIN_URL)
                        .then().extract().body().path("id");
        if (id != null) {
            spec.delete (ENDPOINT_COURIER_URL + "/{id}", id.toString());
        }
    }
}
