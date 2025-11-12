package com.pm.integrationtests.base;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.BeforeAll;

public class BaseIntegrationTest {

    protected static String authToken;

    @BeforeAll
    static void globalSetUp() {
        RestAssured.baseURI = "http://localhost:4004"; // or read from properties/env

        // Login once and reuse token
        String loginPayload = """
                {
                    "email": "testuser@test.com",
                    "password": "password123"
                }
                """;

        authToken = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getString("token");
    }

    protected String getAuthToken() {
        return authToken;
    }
}
