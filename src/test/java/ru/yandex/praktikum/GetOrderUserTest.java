package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.yandex.praktikum.config.BeforeAndAfterBaseClass;

import static org.hamcrest.CoreMatchers.is;
import static ru.yandex.praktikum.config.ConstantsMessage.SHOULD_BE_AUTHORISED;

public class GetOrderUserTest extends BeforeAndAfterBaseClass {

    @Test
    @DisplayName("Получение заказа пользователя с авторизацией")
    @Description("Проверка статус-кода 200 и сообщения c success - true")
    public void createOrderWithAuthorisationTest(){
        userClient.createUser(user);
        String accessToken = userClient.loginUser(user).extract().body().path("accessToken");
        orderClient.createOrder(ingredients, accessToken);
        ValidatableResponse response = orderClient.getOrderUser(accessToken);
                response.assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Получение заказа пользователя без авторизации")
    @Description("Проверка статус-кода 401 и сообщения: You should be authorised")
    public void createOrderWithoutAuthorisationTest(){
        ValidatableResponse response = orderClient.getOrderUser("");
        response.assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("success", is(false))
                .and()
                .body("message", is(SHOULD_BE_AUTHORISED));
    }
}
