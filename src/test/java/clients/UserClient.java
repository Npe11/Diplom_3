package clients;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.CourierModel;
import static utils.BaseClient.spec;

/**
 * Клиент для работы с пользователями через API.
 */
public class UserClient {

    @Step("Создание пользователя: {courier}")
    public Response createUser(CourierModel courier) {
        return RestAssured.given()
                .spec(spec)
                .body(courier)
                .when()
                .post("/api/auth/register")
                .then().extract().response();
    }

    @Step("Логин пользователя: {courier}")
    public Response loginUser(CourierModel courier) {
        return RestAssured.given()
                .spec(spec)
                .body(courier)
                .when()
                .post("/api/auth/login")
                .then().extract().response();
    }

    @Step("Удаление пользователя с токеном: {token}")
    public Response deleteUser(String token) {
        return RestAssured.given()
                .spec(spec)
                .header("Authorization", token)
                .when()
                .delete("/api/auth/user")
                .then().extract().response();
    }
}