package tests.courier;

import api.client.CourierClient;
import api.models.courier.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import tests.base.BaseTest;

import static io.restassured.RestAssured.given;

public class CreateCourierTest extends BaseTest {

    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Проверка статуса ответа и значения поля для /api/v1/courier (успешный запрос)")
    public void createCourierTest() {
        CourierClient courierClient = new CourierClient();
        String login = RandomStringUtils.randomAlphanumeric(1, 10);
        String password = RandomStringUtils.randomAlphanumeric(6, 8);
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        Courier courier = new Courier(login, password, firstName);
        Response response = courierClient.create(courier);
        response.then().log().all()
                .assertThat().body("ok", Matchers.is(true)).and().statusCode(201);

    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверка статуса ответа и наличия сообщения при создании двух одинаковых курьеров")
    public void createTwoIdenticalCouriersTest() {
        CourierClient courierClient = new CourierClient();
        String login = RandomStringUtils.randomAlphanumeric(1, 10);
        String password = RandomStringUtils.randomAlphanumeric(6, 8);
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        Courier courier = new Courier(login, password, firstName);
        courierClient.create(courier);
        Response response = courierClient.create(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(409);
    }

    @Test
    @DisplayName("Создание двух курьеров с одинаковыми логинами")
    @Description("Проверка статуса ответа и наличия сообщения при создании двух курьеров с одинаковыми логинами")
    public void createTwoIdenticalLoginTest() {
        CourierClient courierClient = new CourierClient();
        String login = RandomStringUtils.randomAlphanumeric(1, 10);
        String password = RandomStringUtils.randomAlphanumeric(6, 8);
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        Courier courier = new Courier(login, password, firstName);
        courierClient.create(courier);
        courier.setPassword("abracadabra");
        Response response = courierClient.create(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(409);
    }

    @Test
    @DisplayName("Создание курьера без логина и пароля")
    @Description("Проверка статуса ответа и наличия сообщения при создании курьера без логина и пароля (неверный запрос)")
    public void createCourierWithoutLoginAndPassword() {
        CourierClient courierClient = new CourierClient();
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        Courier courier = new Courier();
        courier.setFirstName(firstName);
        Response response = courierClient.create(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

    @Test
    @DisplayName("Создание курьера без логина и имени")
    @Description("Проверка статуса ответа и наличия сообщения при создании курьера без логина и имени (неверный запрос)")
    public void createCourierWithoutLoginAndFirstName() {
        CourierClient courierClient = new CourierClient();
        String password = RandomStringUtils.randomAlphabetic(6, 8);
        Courier courier = new Courier();
        courier.setPassword(password);
        Response response = courierClient.create(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

    @Test
    @DisplayName("Создание курьера без пароля и имени")
    @Description("Проверка статуса ответа и наличия сообщения при создании курьера без пароля и имени (неверный запрос)")
    public void createCourierWithoutPasswordAndFirstName() {
        CourierClient courierClient = new CourierClient();
        String login = RandomStringUtils.randomAlphabetic(3, 10);
        Courier courier = new Courier();
        courier.setLogin(login);
        Response response = courierClient.create(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка статуса ответа и наличия сообщения при создании курьера без пароля (неверный запрос)")
    public void createCourierWithoutPassword() {
        CourierClient courierClient = new CourierClient();
        String login = RandomStringUtils.randomAlphabetic(1, 10);
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        Courier courier = new Courier();
        courier.setLogin(login);
        courier.setFirstName(firstName);
        Response response = courierClient.create(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка статуса ответа и наличия сообщения при создании курьера без логина (неверный запрос)")
    public void createCourierWithoutLogin() {
        CourierClient clientStep = new CourierClient();
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        String password = RandomStringUtils.randomAlphanumeric(6, 8);
        Courier courier = new Courier();
        courier.setFirstName(firstName);
        courier.setPassword(password);
        Response response = clientStep.create(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(400);
    }

}
