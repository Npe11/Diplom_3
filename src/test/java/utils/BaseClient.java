package utils;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import utils.Endpoints;

public class BaseClient {
    public static RequestSpecification spec = RestAssured.given()
            .baseUri(Endpoints.BASE_URL)
            .header("Content-Type", "application/json");
}