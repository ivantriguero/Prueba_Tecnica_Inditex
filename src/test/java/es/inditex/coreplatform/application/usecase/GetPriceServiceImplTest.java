package es.inditex.coreplatform.application.usecase;

import es.inditex.coreplatform.domain.exception.PriceNotFoundException;
import es.inditex.coreplatform.domain.model.Price;
import es.inditex.coreplatform.domain.port.out.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetPriceServiceImplTest {

    private PriceRepository priceRepository;
    private GetPriceServiceImpl getPriceServiceImpl;

    @BeforeEach
    void setUp() {
        priceRepository = mock(PriceRepository.class);
        getPriceServiceImpl = new GetPriceServiceImpl(priceRepository);
    }

    @Test
    void returnPriceWithHighestPriority() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price lowPriority = Price.builder()
                .productId(productId)
                .brandId(brandId)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priceList(1L)
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();

        Price highPriority = Price.builder()
                .productId(productId)
                .brandId(brandId)
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .priceList(2L)
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .build();

        when(priceRepository.findPrices(productId, brandId, date))
                .thenReturn(List.of(lowPriority, highPriority));

        // When
        Price result = getPriceServiceImpl.getApplicablePrice(date, productId, brandId);

        // Then
        assertNotNull(result);
        assertEquals(highPriority.getPriceList(), result.getPriceList());
        assertEquals(highPriority.getPriority(), result.getPriority());
    }

    @Test
    void throwPriceNotFoundException() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 99999L;
        Long brandId = 2L;

        when(priceRepository.findPrices(productId, brandId, date)).thenReturn(List.of());

        // When & Then
        assertThrows(PriceNotFoundException.class,
                () -> getPriceServiceImpl.getApplicablePrice(date, productId, brandId));
    }
}
