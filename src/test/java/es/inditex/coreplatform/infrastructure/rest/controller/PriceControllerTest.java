package es.inditex.coreplatform.infrastructure.rest.controller;

import es.inditex.coreplatform.application.usecase.GetPrice;
import es.inditex.coreplatform.domain.exception.PriceNotFoundException;
import es.inditex.coreplatform.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PriceController.class)
@AutoConfigureMockMvc
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private GetPrice getPrice;

    private final String BASE_URL = "/api/prices";

    @BeforeEach
    void setUp() {
        getPrice = mock(GetPrice.class);
    }

    @Test
    void returnApplicablePrice() throws Exception {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price price = Price.builder()
                .productId(productId)
                .brandId(brandId)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priceList(1L)
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();

        given(getPrice.getApplicablePrice(any(), anyLong(), anyLong()))
                .willReturn(price);

        mockMvc.perform(get(BASE_URL)
                        .param("date", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    void return404WhenNoPriceFound() throws Exception {
        given(getPrice.getApplicablePrice(any(), anyLong(), anyLong()))
                .willThrow(new PriceNotFoundException(35455L, 1L, "2020-06-14"));

        mockMvc.perform(get(BASE_URL)
                        .param("date", "2020-06-14T00:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void return400ForInvalidDateFormat() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("date", "invalid-date")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void return400ForMissingParams() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("date", "2020-06-14T00:00:00")
                // Falta productId y brandId
        ).andExpect(status().isBadRequest());
    }
}
