package ru.yandex.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.data.Ingredients;
import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
    private static final String CREATE_ORDER = "api/orders";

    private static final String GET_ORDER = "api/orders";

    @Step("Отправка POST-запроса для создания заказа по api/orders")
    public ValidatableResponse createOrder(Ingredients ingredient, String accessToken) {
        return given()
                .spec(requestSpecification())
                .and()
                .header("Authorization", accessToken)
                .body(ingredient)
                .when()
                .post(CREATE_ORDER)
                .then();
    }

    @Step("Отправка GET-запроса для получения заказов пользователя по api/orders")
    public ValidatableResponse getOrderUser(String accessToken) {
        return given()
                .spec(requestSpecification())
                .and()
                .header("Authorization", accessToken)
                .when()
                .get(GET_ORDER)
                .then();
    }
}
