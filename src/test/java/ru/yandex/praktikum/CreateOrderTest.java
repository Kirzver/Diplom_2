package ru.yandex.praktikum;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.yandex.praktikum.config.BeforeAndAfterBaseClass;

import static org.hamcrest.CoreMatchers.is;
import static ru.yandex.praktikum.config.ConstantData.BAD_HASH_INGREDIENT;
import static ru.yandex.praktikum.config.ConstantsMessage.MUST_BE_PROVIDED;

public class CreateOrderTest extends BeforeAndAfterBaseClass {

    @Test
    @DisplayName("Создание заказа с авторизацией и ингрeдиентами")
    @Description("Проверка статус-кода 200 и сообщения c success - true")
    public void createOrderWithAuthorisationTest(){
        userClient.createUser(user);
        String accessToken = userClient.loginUser(user).extract().body().path("accessToken");
        ValidatableResponse response = orderClient.createOrder(ingredients, accessToken);
        response.assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("success", is(true));
    }
    @Test
    @DisplayName("Создание заказа с авторизацией без ингредиентов")
    @Description("Проверка статус-кода 400 и сообщения: Ingredient ids must be provided")
    public void createOrderWithAuthorisationAnwWithoutIngredientsTest(){
        userClient.createUser(user);
        String accessToken = userClient.loginUser(user).extract().body().path("accessToken");
        ingredients.clearIngredients();
        ValidatableResponse response = orderClient.createOrder(ingredients, accessToken);
        response.assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("success", is(false))
                .and()
                .body("message", is(MUST_BE_PROVIDED));
    }

    @Test
    @DisplayName("Создание заказа с авторизацией и неверным ингредиентом")
    @Description("Проверка статус-кода 500")
    public void createOrderWithAuthorisationAnwWrongIngredientsTest(){
        userClient.createUser(user);
        String accessToken = userClient.loginUser(user).extract().body().path("accessToken");
        ingredients.clearIngredients();
        ingredients.addIngredients(BAD_HASH_INGREDIENT);
        ValidatableResponse response = orderClient.createOrder(ingredients, accessToken);
        response.assertThat()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);

    }
    @Test
    @DisplayName("Создание заказа без авторизации и с ингрeдиентами")
    @Description("Проверка статус-кода 200 и сообщения c success - true")
    public void createOrderWithoutAuthorisationTest(){
        userClient.createUser(user);
        ValidatableResponse response = orderClient.createOrder(ingredients, "");
        response.assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Создание заказа без авторизации и без ингредиентов")
    @Description("Проверка статус-кода 400 и сообщения: Ingredient ids must be provided")
    public void createOrderWithoutAuthorisationAnwWithoutIngredientsTest(){
        userClient.createUser(user);
        ingredients.clearIngredients();
        ValidatableResponse response = orderClient.createOrder(ingredients, "");
        response.assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("success", is(false))
                .and()
                .body("message", is(MUST_BE_PROVIDED));
    }

    @Test
    @DisplayName("Создание заказа без авторизации и неверным ингредиентом")
    @Description("Проверка статус-кода 500")
    public void createOrderWithoutAuthorisationAnwWrongIngredientsTest(){
        userClient.createUser(user);
        ingredients.clearIngredients();
        ingredients.addIngredients(BAD_HASH_INGREDIENT);
        ValidatableResponse response = orderClient.createOrder(ingredients, "");
        response.assertThat()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);

    }
}
