package es.inditex.coreplatform.e2e;

import es.inditex.coreplatform.adapter.infrastructure.rest.openapi.dto.PriceResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceE2EPruebas {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url(String date) {
        return UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/prices")
                .queryParam("date", date)
                .queryParam("productId", 35455L)
                .queryParam("brandId", 1L)
                .toUriString();
    }

    @Test
    void test1() {
        ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(
                url("2020-06-14T10:00:00Z"), PriceResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody().getPrice()).isEqualTo(35.50);
        assertThat(response.getBody().getPriceList()).isEqualTo(1);
    }

    @Test
    void test2() {
        ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(
                url("2020-06-14T16:00:00Z"), PriceResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody().getPrice()).isEqualTo(25.45);
        assertThat(response.getBody().getPriceList()).isEqualTo(2);
    }

    @Test
    void test3() {
        ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(
                url("2020-06-14T21:00:00Z"), PriceResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody().getPrice()).isEqualTo(35.50);
        assertThat(response.getBody().getPriceList()).isEqualTo(1);
    }

    @Test
    void test4() {
        ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(
                url("2020-06-15T10:00:00Z"), PriceResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody().getPrice()).isEqualTo(30.50);
        assertThat(response.getBody().getPriceList()).isEqualTo(3);
    }

    @Test
    void test5() {
        ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(
                url("2020-06-16T21:00:00Z"), PriceResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody().getPrice()).isEqualTo(38.95);
        assertThat(response.getBody().getPriceList()).isEqualTo(4);
    }
}
