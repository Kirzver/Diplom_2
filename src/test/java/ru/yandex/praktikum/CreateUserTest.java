package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.yandex.praktikum.config.BeforeAndAfterBaseClass;

import static org.hamcrest.CoreMatchers.is;
import static ru.yandex.praktikum.config.ConstantsMessage.REQUIRED_FIELDS;
import static ru.yandex.praktikum.config.ConstantsMessage.USER_EXIST;

public class CreateUserTest extends BeforeAndAfterBaseClass {

    @Test
    @DisplayName("Успешное создание пользователя")
    @Description("Проверка статус-кода 200 и сообщения в теле success - true")
    public void isPossibleToCreateAUserTest() {
        ValidatableResponse response = userClient.createUser(user);
        response.assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("success", is(true));

    }

    @Test
    @DisplayName("Созданих двух одинаковых пользователей")
    @Description("Проверка статус-кода 403 и сообщения: User already exists")
    public void cannotCreateTwoIdenticalUsersTest(){
        userClient.createUser(user);
        ValidatableResponse response = userClient.createUser(user);
        response.assertThat()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("message", is(USER_EXIST));
    }

    @Test
    @DisplayName("Создание пользователя без почты")
    @Description("Проверка статус-кода 403 и сообщения: Email, password and name are required fields")
    public void createUserWithoutEmailTest() {
        user.setEmail(null);
        ValidatableResponse response = userClient.createUser(user);
        response.assertThat()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("message", is(REQUIRED_FIELDS));

    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    @Description("Проверка статус-кода 403 и сообщения: Email, password and name are required fields")
    public void createUserWithoutPasswordTest() {
        user.setPassword(null);
        ValidatableResponse response = userClient.createUser(user);
        response.assertThat()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("message", is(REQUIRED_FIELDS));

    }

    @Test
    @DisplayName("Создание пользователя без имени")
    @Description("Проверка статус-кода 403 и сообщения: Email, password and name are required fields")
    public void createUserWithoutNameTest() {
        user.setName(null);
        ValidatableResponse response = userClient.createUser(user);
        response.assertThat()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("message", is(REQUIRED_FIELDS));

    }

}
