package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;;
import org.junit.Test;
import ru.yandex.praktikum.config.BeforeAndAfterBaseClass;
import ru.yandex.praktikum.data.RandomUser;

import static org.hamcrest.CoreMatchers.is;
import static ru.yandex.praktikum.config.ConstantsMessage.INCORRECT_EMAIL_OR_PASSWORD;

public class LoginUserTest extends BeforeAndAfterBaseClass {

    @Test
    @DisplayName("Авторизация пользователя")
    @Description("Проверка статус-кода 200 и сообщения c success - true")
    public void userCanAuthorizationTest(){
        userClient.createUser(user);
        ValidatableResponse response = userClient.loginUser(user);
        response.assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Авторизация пользователя c некорректной почтой")
    @Description("Проверка статус-кода 401 и сообщения: email or password are incorrect")
    public void tryLoginWithWrongEmailTest(){
        userClient.createUser(user);
        user.setEmail(RandomUser.getRandomEmail());
        ValidatableResponse response = userClient.loginUser(user);
        response.assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("message", is(INCORRECT_EMAIL_OR_PASSWORD));
    }

    @Test
    @DisplayName("Авторизация пользователя c некорректным паролем")
    @Description("Проверка статус-кода 401 и сообщения: email or password are incorrect")
    public void tryLoginWithWrongPasswordTest(){
        userClient.createUser(user);
        user.setPassword(RandomUser.getRandomNameOrPassword());
        ValidatableResponse response = userClient.loginUser(user);
        response.assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("message", is(INCORRECT_EMAIL_OR_PASSWORD));
    }

    @Test
    @DisplayName("Авторизация пользователя c некорректным паролем и почтой")
    @Description("Проверка статус-кода 401 и сообщения: email or password are incorrect")
    public void tryLoginWithWrongPasswordAndEmailTest(){
        userClient.createUser(user);
        user.setPassword(RandomUser.getRandomNameOrPassword());
        user.setEmail(RandomUser.getRandomEmail());
        ValidatableResponse response = userClient.loginUser(user);
        response.assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("message", is(INCORRECT_EMAIL_OR_PASSWORD));
    }
}
