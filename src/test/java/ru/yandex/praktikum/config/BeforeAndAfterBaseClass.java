package ru.yandex.praktikum.config;


import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import ru.yandex.praktikum.client.OrderClient;
import ru.yandex.praktikum.client.UserClient;
import ru.yandex.praktikum.data.Ingredients;
import ru.yandex.praktikum.data.RandomUser;
import ru.yandex.praktikum.data.UserData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.yandex.praktikum.config.ConstantData.*;


public class BeforeAndAfterBaseClass {

    public UserClient userClient;
    public UserData user;
    public OrderClient orderClient;
    public Ingredients ingredients;



    @Before
    public void setup() {
        userClient = new UserClient();
        orderClient = new OrderClient();
        user = RandomUser.getRandomUser();
        ingredients = new Ingredients( new ArrayList<String>(Arrays.asList(BUN, MAIN, SAUCE)));

    }

    @After
    public void cleanUp(){
        ValidatableResponse response = userClient.loginUser(user);
        if(response.extract().statusCode() == 200)
            userClient.deleteUser(response.extract().path("accessToken"));

    }


}
