package ru.yandex.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.data.UserData;

import static io.restassured.RestAssured.given;

public class UserClient extends RestClient {
    private static final String CREATE_USER = "api/auth/register";
    private static final String LOGIN_USER = "api/auth/login";
    private static final String DELETE_USER = "/api/auth/user";
    private static final String UPDATE_USER = "/api/auth/user";

    @Step("Отправка POST-запроса для создания пользователя по api/auth/register")
    public ValidatableResponse createUser(UserData user){
        return given()
                .spec(requestSpecification())
                .and()
                .body(user)
                .when()
                .post(CREATE_USER)
                .then();
    }
    @Step("Отправка POST-запроса для авторизации пользователя по api/auth/login")
    public ValidatableResponse loginUser(UserData user){
        return given()
                .spec(requestSpecification())
                .and()
                .body(user)
                .when()
                .post(LOGIN_USER)
                .then();
    }

    @Step("Отправка DELETE-запроса для удаления пользователя по /api/auth/user")
    public ValidatableResponse deleteUser(String accessToken){
        return given()
                .spec(requestSpecification())
                .and()
                .header("Authorization", accessToken)
                .when()
                .delete(DELETE_USER)
                .then();
    }

    @Step("Отправка PATCH-запроса для обновления информации о пользователе по /api/auth/user")
    public ValidatableResponse updateUser(String accessToken, UserData user){
        return given()
                .spec(requestSpecification())
                .and()
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(UPDATE_USER)
                .then();
    }
}
