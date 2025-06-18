package es.inditex.coreplatform.infrastructure.jpa.mapper;

import es.inditex.coreplatform.domain.model.Price;
import es.inditex.coreplatform.infrastructure.jpa.entity.PriceEntity;

public class PriceEntityMapper {

    public static Price toDomain(PriceEntity entity) {
        return Price.builder()
                .id(entity.getId())
                .brandId(entity.getBrandId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .priceList(entity.getPriceList())
                .productId(entity.getProductId())
                .priority(entity.getPriority())
                .price(entity.getPrice())
                .currency(entity.getCurrency())
                .build();
    }

    public static PriceEntity toEntity(Price domain) {
        return PriceEntity.builder()
                .id(domain.getId())
                .brandId(domain.getBrandId())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .priceList(domain.getPriceList())
                .productId(domain.getProductId())
                .priority(domain.getPriority())
                .price(domain.getPrice())
                .currency(domain.getCurrency())
                .build();
    }

}
