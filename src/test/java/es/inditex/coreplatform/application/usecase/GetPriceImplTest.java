package es.inditex.coreplatform.application.usecase;

import es.inditex.coreplatform.domain.exception.PriceNotFoundException;
import es.inditex.coreplatform.domain.model.Price;
import es.inditex.coreplatform.domain.port.out.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetPriceImplTest {

    private PriceRepository priceRepository;
    private GetPriceImpl getPriceImpl;

    @BeforeEach
    void setUp() {
        priceRepository = mock(PriceRepository.class);
        getPriceImpl = new GetPriceImpl(priceRepository);
    }

    @Test
    void shouldReturnPriceWhenRepositoryFindsMatch() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price expectedPrice = Price.builder()
                .productId(productId)
                .brandId(brandId)
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .priceList(2L)
                .priority(1)
                .priceValue(new BigDecimal("25.45"))
                .currency("EUR")
                .build();

        when(priceRepository.findPrice(productId, brandId, date)).thenReturn(expectedPrice);

        // When
        Price result = getPriceImpl.getApplicablePrice(date, productId, brandId);

        // Then
        assertNotNull(result);
        assertEquals(expectedPrice.getProductId(), result.getProductId());
        assertEquals(expectedPrice.getBrandId(), result.getBrandId());
        assertEquals(expectedPrice.getStartDate(), result.getStartDate());
        assertEquals(expectedPrice.getEndDate(), result.getEndDate());
        assertEquals(expectedPrice.getPriceList(), result.getPriceList());
        assertEquals(expectedPrice.getPriority(), result.getPriority());
        assertEquals(expectedPrice.getPriceValue(), result.getPriceValue());
        assertEquals(expectedPrice.getCurrency(), result.getCurrency());
    }

    @Test
    void shouldThrowExceptionWhenNoApplicablePriceFound() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 99999L;
        Long brandId = 2L;

        when(priceRepository.findPrice(productId, brandId, date))
                .thenThrow(new PriceNotFoundException(productId, brandId, date));

        // When & Then
        assertThrows(PriceNotFoundException.class,
                () -> getPriceImpl.getApplicablePrice(date, productId, brandId));
    }
}
