package es.inditex.coreplatform.infrastructure.jpa.mapper;

import es.inditex.coreplatform.domain.model.Price;
import es.inditex.coreplatform.infrastructure.jpa.entity.PriceEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PriceMapperTest {

    @Test
    void mapEntityToDomain() {
        // Given
        PriceEntity entity = PriceEntity.builder()
                .id(1L)
                .brandId(1L)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priceList(1L)
                .productId(35455L)
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();

        // When
        Price result = PriceEntityMapper.toDomain(entity);

        // Then
        assertThat(result.getId()).isEqualTo(entity.getId());
        assertThat(result.getBrandId()).isEqualTo(entity.getBrandId());
        assertThat(result.getStartDate()).isEqualTo(entity.getStartDate());
        assertThat(result.getEndDate()).isEqualTo(entity.getEndDate());
        assertThat(result.getPriceList()).isEqualTo(entity.getPriceList());
        assertThat(result.getProductId()).isEqualTo(entity.getProductId());
        assertThat(result.getPriority()).isEqualTo(entity.getPriority());
        assertThat(result.getPrice()).isEqualByComparingTo(entity.getPrice());
        assertThat(result.getCurrency()).isEqualTo(entity.getCurrency());
    }

    @Test
    void mapDomainToEntity() {
        Price price = Price.builder()
                .id(1L)
                .brandId(1L)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priceList(1L)
                .productId(35455L)
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();

        PriceEntity result = PriceEntityMapper.toEntity(price);

        assertThat(result.getId()).isEqualTo(price.getId());
        assertThat(result.getBrandId()).isEqualTo(price.getBrandId());
        assertThat(result.getStartDate()).isEqualTo(price.getStartDate());
        assertThat(result.getEndDate()).isEqualTo(price.getEndDate());
        assertThat(result.getPriceList()).isEqualTo(price.getPriceList());
        assertThat(result.getProductId()).isEqualTo(price.getProductId());
        assertThat(result.getPriority()).isEqualTo(price.getPriority());
        assertThat(result.getPrice()).isEqualByComparingTo(price.getPrice());
        assertThat(result.getCurrency()).isEqualTo(price.getCurrency());
    }
}
