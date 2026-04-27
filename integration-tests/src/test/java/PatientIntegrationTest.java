import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.notNullValue;

public class PatientIntegrationTest {
    @BeforeAll
    static void setUp(){
        RestAssured.baseURI="http://localhost:5001";
    }

    @Test
    public void shouldReturnPatientWithValidToken(){
        String loginPayload= """
                {
                "email":"testuser@test.com",
                "password":"password123"
                }
                """;

        String token=RestAssured.given()
                .contentType("application/json")
                .body(loginPayload)
                .when().post("/api/auth/login")
                .then().statusCode(200)
                .extract().jsonPath().get("token");



        RestAssured.given().header("Authorization","Bearer "+token)
                .get("/api/patients").then().statusCode(200)
                .body("patients",notNullValue());
    }
}
