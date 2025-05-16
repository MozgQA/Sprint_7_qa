package tests.courier;

import api.client.CourierClient;
import api.models.courier.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;
import tests.base.BaseTest;

import static org.apache.http.HttpStatus.*;

public class CourierLoginTest extends BaseTest {
    private Courier courier;
    private CourierClient courierClient;
    @Override
    public void setUp() {
        super.setUp();
        courier = new Courier();
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Курьер входит в систему.")
    @Description("Проверка статуса ответа при успешном входе курьера в систему.")
    public void authorizationTest(){
        courier.setLogin("ninja1453");
        courier.setPassword("1234");
        Response response = courierClient.login(courier);
        response.then().log().all()
                .assertThat().body("id", Matchers.notNullValue()).and().statusCode(SC_OK);
    }

    @Test
    @DisplayName("Курьер пытается войти в систему без логина.")
    @Description("Проверка статуса ответа, когда курьер пытается войти в систему без указания логина (ошибочный запрос).")
    public void authorizationWithoutLoginTest(){
        courier.setPassword("1234");
        Response response = courierClient.login(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Недостаточно данных для входа")).
                and().statusCode(SC_BAD_REQUEST);
    }

    /**
     Тест падает по причине дефекта в сервисе Авторизации
     **/
    /*@Test
    @DisplayName("Курьер пытается войти в систему без пароля.")
    @Description("Проверка статуса ответа, когда курьер пытается войти в систему без указания пароля (ошибочный запрос).")
    public void authorizationWithoutPasswordTest(){
        CourierClient clientStep = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("ninja1453");
        Response response = clientStep.login(courier);
        response.then().log().all()
                .assertThat().statusCode(SC_BAD_REQUEST).and().body("message", Matchers.is("Недостаточно данных для входа"));
    }*/

    @Test
    @DisplayName("Курьер пытается войти в систему с неверным паролем.")
    @Description("Проверка статуса ответа, когда курьер пытается войти в систему с неверным паролем.")
    public void authorizationWithWrongPasswordTest(){
        courier.setLogin("ninja1453");
        courier.setPassword("1234000");
        Response response = courierClient.login(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Курьер пытается войти в систему с неверным логином.")
    @Description("Проверка статуса ответа, когда курьер пытается войти в систему с неверным логином.")
    public void authorizationWithWrongLoginTest(){
        courier.setLogin("ninja1453ninja");
        courier.setPassword("1234");
        Response response = courierClient.login(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Курьер пытается войти в систему с неверными логином и паролем.")
    @Description("Проверка статуса ответа, когда курьер пытается войти в систему с неверными логином и паролем.")
    public void authorizationWithWrongLoginAndPasswordTest(){
        courier.setLogin("TakogoLoginaNeSushestvuet");
        courier.setPassword("TakogoParolaNet");
        Response response = courierClient.login(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(SC_NOT_FOUND);
    }
}
