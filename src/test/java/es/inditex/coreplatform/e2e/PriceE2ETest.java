package es.inditex.coreplatform.e2e;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PriceE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getUrl(String date, Long productId, Long brandId) {
        return UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/prices")
                .queryParam("date", date)
                .queryParam("productId", productId)
                .queryParam("brandId", brandId)
                .toUriString();
    }

    @Test
    void returnPriceWhenFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(getUrl("2020-06-14T10:00:00Z", 35455L, 1L), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("35455");
    }

    @Test
    void returnHighestPriorityPriceWhenMultipleFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(getUrl("2020-06-14T16:00:00Z", 35455L, 1L), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"priceList\":2");
    }

    @Test
    void return404WhenNoPriceFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(getUrl("2030-01-01T00:00:00Z", 35455L, 1L), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("no encontrado");
    }

    @Test
    void return400WhenInvalidDateFormat() {
        String url = getUrl("invalid-date", 35455L, 1L);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("debe ser de tipo");
    }

    @Test
    void return400WhenMissingParameter() {
        String url = "http://localhost:" + port + "/api/prices?date=2020-06-14T10:00:00Z&productId=35455";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Falta el parámetro obligatorio");
    }
}
