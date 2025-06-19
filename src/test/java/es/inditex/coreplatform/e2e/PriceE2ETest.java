package es.inditex.coreplatform.e2e;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PriceE2ETest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void returnPriceWhenFound() {
        given()
                .accept(ContentType.JSON)
                .queryParam("date", "2020-06-14T10:00:00Z")
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .when()
                .get("/api/prices")
                .then()
                .statusCode(200)
                .body("productId", equalTo(35455))
                .body("brandId", equalTo(1))
                .body("price", notNullValue());
    }

    @Test
    void returnHighestPriorityPriceWhenMultipleFound() {
        given()
                .accept(ContentType.JSON)
                .queryParam("date", "2020-06-14T16:00:00Z")
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .when()
                .get("/api/prices")
                .then()
                .statusCode(200)
                .body("priceList", equalTo(2));
    }

    @Test
    void return404WhenNoPriceFound() {
        given()
                .accept(ContentType.JSON)
                .queryParam("date", "2030-01-01T00:00:00Z")
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .when()
                .get("/api/prices")
                .then()
                .statusCode(404)
                .body("message", containsString("no encontrado"));
    }

    @Test
    void return400WhenInvalidDateFormat() {
        given()
                .accept(ContentType.JSON)
                .queryParam("date", "invalid-date")
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .when()
                .get("/api/prices")
                .then()
                .statusCode(400)
                .body("message", containsString("debe ser de tipo"));
    }

    @Test
    void return400WhenMissingParameter() {
        given()
                .accept(ContentType.JSON)
                .queryParam("date", "2020-06-14T10:00:00Z")
                .queryParam("productId", 35455)
                .when()
                .get("/api/prices")
                .then()
                .statusCode(400)
                .body("message", containsString("Falta el parámetro obligatorio"));
    }
}
