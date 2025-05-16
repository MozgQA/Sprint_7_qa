package tests.courier;

import api.client.CourierClient;
import api.models.courier.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import tests.base.BaseTest;

import static org.apache.http.HttpStatus.*;

public class CreateCourierTest extends BaseTest {
    private Courier courier;
    private CourierClient courierClient;
    @Override
    public void setUp() {
        super.setUp();
        courier = new Courier();
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Проверка статуса ответа и значения поля для /api/v1/courier (успешный запрос)")
    public void createCourierTest() {
        String login = RandomStringUtils.randomAlphanumeric(1, 10);
        String password = RandomStringUtils.randomAlphanumeric(6, 8);
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(firstName);
        Response response = courierClient.create(courier);
        response.then().log().all()
                .assertThat().body("ok", Matchers.is(true)).and().statusCode(SC_CREATED);

    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверка статуса ответа и наличия сообщения при создании двух одинаковых курьеров")
    public void createTwoIdenticalCouriersTest() {
        String login = RandomStringUtils.randomAlphanumeric(1, 10);
        String password = RandomStringUtils.randomAlphanumeric(6, 8);
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(firstName);

        courierClient.create(courier);
        Response response = courierClient.create(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(SC_CONFLICT);
    }

    @Test
    @DisplayName("Создание двух курьеров с одинаковыми логинами")
    @Description("Проверка статуса ответа и наличия сообщения при создании двух курьеров с одинаковыми логинами")
    public void createTwoIdenticalLoginTest() {
        String login = RandomStringUtils.randomAlphanumeric(1, 10);
        String password = RandomStringUtils.randomAlphanumeric(6, 8);
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(firstName);

        courierClient.create(courier);
        courier.setPassword("abracadabra");
        Response response = courierClient.create(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(SC_CONFLICT);
    }

    @Test
    @DisplayName("Создание курьера без логина и пароля")
    @Description("Проверка статуса ответа и наличия сообщения при создании курьера без логина и пароля (неверный запрос)")
    public void createCourierWithoutLoginAndPassword() {
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        courier.setFirstName(firstName);
        Response response = courierClient.create(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без логина и имени")
    @Description("Проверка статуса ответа и наличия сообщения при создании курьера без логина и имени (неверный запрос)")
    public void createCourierWithoutLoginAndFirstName() {
        String password = RandomStringUtils.randomAlphanumeric(6, 8);
        courier.setPassword(password);
        Response response = courierClient.create(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без пароля и имени")
    @Description("Проверка статуса ответа и наличия сообщения при создании курьера без пароля и имени (неверный запрос)")
    public void createCourierWithoutPasswordAndFirstName() {
        String login = RandomStringUtils.randomAlphanumeric(1, 10);
        courier.setLogin(login);
        Response response = courierClient.create(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка статуса ответа и наличия сообщения при создании курьера без пароля (неверный запрос)")
    public void createCourierWithoutPassword() {
        String login = RandomStringUtils.randomAlphanumeric(1, 10);
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        courier.setLogin(login);
        courier.setFirstName(firstName);
        Response response = courierClient.create(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка статуса ответа и наличия сообщения при создании курьера без логина (неверный запрос)")
    public void createCourierWithoutLogin() {
        String password = RandomStringUtils.randomAlphanumeric(6, 8);
        String firstName = RandomStringUtils.randomAlphabetic(3, 10);
        courier.setPassword(password);
        courier.setFirstName(firstName);
        Response response = courierClient.create(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.notNullValue()).and().statusCode(SC_BAD_REQUEST);
    }

}
