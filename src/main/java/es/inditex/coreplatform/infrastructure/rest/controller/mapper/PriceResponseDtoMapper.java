package es.inditex.coreplatform.infrastructure.rest.controller.mapper;

import es.inditex.coreplatform.adapter.infrastructure.rest.openapi.dto.PriceResponseDTO;
import es.inditex.coreplatform.domain.model.Price;

import java.time.ZoneOffset;

public class PriceResponseDtoMapper {

    private PriceResponseDtoMapper() {
        // Evita instanciación
    }

    public static PriceResponseDTO toPriceResponseDTO(Price price) {
        return new PriceResponseDTO(
                price.getProductId(),
                price.getBrandId(),
                price.getPriceList().intValue(),
                price.getStartDate().atOffset(ZoneOffset.UTC),
                price.getEndDate().atOffset(ZoneOffset.UTC),
                price.getPriceValue().doubleValue(),
                price.getCurrency()
        );
    }

}
