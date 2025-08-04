package es.inditex.coreplatform.infrastructure.jpa.repository;

import es.inditex.coreplatform.domain.exception.PriceNotFoundException;
import es.inditex.coreplatform.domain.model.Price;
import es.inditex.coreplatform.domain.port.out.PriceRepository;
import es.inditex.coreplatform.infrastructure.jpa.entity.PriceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceRepositoryAdapterTest {

    private JpaPriceRepository jpaRepository;
    private PriceRepository priceRepository;

    @BeforeEach
    void setUp() {
        jpaRepository = mock(JpaPriceRepository.class);
        priceRepository = new PriceRepositoryAdapter(jpaRepository);
    }

    @Test
    void returnMappedPriceWhenEntityFound() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

        PriceEntity entity = PriceEntity.builder()
                .id(1L)
                .brandId(brandId)
                .productId(productId)
                .priceList(2L)
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .build();

        when(jpaRepository.findTopByProductIdAndBrandIdAndDateOrderByPriorityDesc(productId, brandId, applicationDate))
                .thenReturn(Optional.of(entity));

        // When
        Price price = priceRepository.findPrice(productId, brandId, applicationDate);

        // Then
        assertEquals(productId, price.getProductId());
        assertEquals("EUR", price.getCurrency());
        assertEquals(new BigDecimal("25.45"), price.getPrice());
    }

    @Test
    void throwExceptionWhenNoPriceFound() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

        when(jpaRepository.findTopByProductIdAndBrandIdAndDateOrderByPriorityDesc(productId, brandId, applicationDate))
                .thenReturn(Optional.empty());

        // When / Then
        assertThrows(PriceNotFoundException.class, () -> {
            priceRepository.findPrice(productId, brandId, applicationDate);
        });
    }
}
