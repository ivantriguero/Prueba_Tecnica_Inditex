package es.inditex.coreplatform.e2e;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PriceE2EPruebas {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    private void callAndAssert(String date, float expectedPrice, int expectedPriceList) {
        given()
                .accept(ContentType.JSON)
                .queryParam("date", date)
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
                .when()
                .get("/api/prices")
                .then()
                .statusCode(200)
                .body("price", equalTo(expectedPrice))
                .body("priceList", equalTo(expectedPriceList));
    }

    @Test
    void test1() {
        callAndAssert("2020-06-14T10:00:00Z", 35.50F, 1);
    }

    @Test
    void test2() {
        callAndAssert("2020-06-14T16:00:00Z", 25.45F, 2);
    }

    @Test
    void test3() {
        callAndAssert("2020-06-14T21:00:00Z", 35.50F, 1);
    }

    @Test
    void test4() {
        callAndAssert("2020-06-15T10:00:00Z", 30.50F, 3);
    }

    @Test
    void test5() {
        callAndAssert("2020-06-16T21:00:00Z", 38.95F, 4);
    }
}
