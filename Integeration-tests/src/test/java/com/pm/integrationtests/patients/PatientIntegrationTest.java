package com.pm.integrationtests.patients;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import com.pm.integrationtests.base.BaseIntegrationTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PatientIntegrationTest extends BaseIntegrationTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://localhost:4004";
        // Token is already initialized in BaseIntegrationTest
        System.out.println("✅ Token from BaseIntegrationTest: " + authToken);
    }

    @Test
    void shouldReturnPatientsWithValidToken() {
        given()
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get("/api/patients")
                .then()
                .statusCode(200)
                .body("patients", notNullValue());
    }

    @Test
    void shouldRejectAccessWithoutToken() {
        given()
                .when()
                .get("/api/patients")
                .then()
                .statusCode(401);
    }

    @Test
    void shouldRejectAccessWithInvalidToken() {
        given()
                .header("Authorization", "Bearer invalid.token.here")
                .when()
                .get("/api/patients")
                .then()
                .statusCode(401);
    }
}
