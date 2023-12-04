package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import ru.yandex.praktikum.config.BeforeAndAfterBaseClass;
import ru.yandex.praktikum.data.RandomUser;
import ru.yandex.praktikum.data.UserData;

import static org.hamcrest.CoreMatchers.is;
import static ru.yandex.praktikum.config.ConstantsMessage.SHOULD_BE_AUTHORISED;

public class ChangeUserDataTest extends BeforeAndAfterBaseClass {

    @Test
    @DisplayName("Изменение почты авторизованного пользователя")
    @Description("Проверка статус-кода 200, сообщения c success - true и соответсвия email в user.email")
    public void userUpdateEmailTest() {
        userClient.createUser(user);
        String accessToken = userClient.loginUser(user).extract().body().path("accessToken");
        String updateEmail = RandomUser.getRandomEmail();
        ValidatableResponse response = userClient.updateUser(accessToken, new UserData(updateEmail, null));
        response.assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("success", is(true))
                .and()
                .body("user.email", CoreMatchers.equalTo(updateEmail.toLowerCase()));

    }

    @Test
    @DisplayName("Изменение имени авторизованного пользователя")
    @Description("Проверка статус-кода 200, сообщения c success - true и соответсвия name в user.name")
    public void userUpdateNameTest() {
        userClient.createUser(user);
        String accessToken = userClient.loginUser(user).extract().body().path("accessToken");
        String updateName = RandomUser.getRandomNameOrPassword();
        ValidatableResponse response = userClient.updateUser(accessToken, new UserData(null, updateName));
        response.assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("success", is(true))
                .and()
                .body("user.name", CoreMatchers.equalTo(updateName));
    }

    @Test
    @DisplayName("Изменение почты и имени авторизованного пользователя")
    @Description("Проверка статус-кода 200, сообщения c success - true, соответсвия email и name в user.email")
    public void userUpdateEmailAndNameTest() {
        userClient.createUser(user);
        String accessToken = userClient.loginUser(user).extract().body().path("accessToken");
        String updateEmail = RandomUser.getRandomEmail();
        String updateName = RandomUser.getRandomNameOrPassword();
        ValidatableResponse response = userClient.updateUser(accessToken, new UserData(updateEmail, updateName));
        response.assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("success", is(true))
                .body("user.email", CoreMatchers.equalTo(updateEmail.toLowerCase()))
                .body("user.name", CoreMatchers.equalTo(updateName));

    }

    @Test
    @DisplayName("Изменение почты неавторизованного пользователя")
    @Description("Проверка статус-кода 401, сообщения c success - false и сообщения: You should be authorised")
    public void userNotAuthorisationUpdateEmailTest() {
        String accessToken = userClient.createUser(user).extract().body().path("accessToken");
        String updateEmail = RandomUser.getRandomEmail();
        ValidatableResponse response = userClient.updateUser(accessToken, new UserData(updateEmail, null));
        response.assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("success", is(false))
                .and()
                .body("message", is(SHOULD_BE_AUTHORISED));

    }

    @Test
    @DisplayName("Изменение имени неавторизованного пользователя")
    @Description("Проверка статус-кода 401, сообщения c success - false и message - You should be authorised")
    public void userNotAuthorisationUpdateNameTest() {
        String accessToken = userClient.createUser(user).extract().body().path("accessToken");
        String updateName = RandomUser.getRandomNameOrPassword();
        ValidatableResponse response = userClient.updateUser(accessToken, new UserData(null, updateName));
        response.assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("success", is(false))
                .and()
                .body("message", is(SHOULD_BE_AUTHORISED));

    }

    @Test
    @DisplayName("Изменение почты и имени неавторизованного пользователя")
    @Description("Проверка статус-кода 401, сообщения c success - false и message - You should be authorised")
    public void userNotAuthorisationUpdateEmailAndNameTest() {
        String accessToken = userClient.createUser(user).extract().body().path("accessToken");
        String updateEmail = RandomUser.getRandomEmail();
        String updateName = RandomUser.getRandomNameOrPassword();
        ValidatableResponse response = userClient.updateUser(accessToken, new UserData(updateEmail, updateName));
        response.assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("success", is(false))
                .and()
                .body("message", is(SHOULD_BE_AUTHORISED));

    }
}
