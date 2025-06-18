package es.inditex.coreplatform.infrastructure.rest.controller.mapper;

import es.inditex.coreplatform.adapter.infrastructure.rest.openapi.dto.PriceResponseDTO;
import es.inditex.coreplatform.domain.model.Price;

import java.time.ZoneOffset;

import static org.apache.commons.lang3.time.CalendarUtils.toOffsetDateTime;

public class PriceResponseDtoMapper {

    public static PriceResponseDTO toPriceResponseDTO(Price price) {
        return new PriceResponseDTO(
                price.getProductId(),
                price.getBrandId(),
                price.getPriceList().intValue(),
                price.getStartDate().atOffset(ZoneOffset.UTC),
                price.getEndDate().atOffset(ZoneOffset.UTC),
                price.getPrice().doubleValue(),
                price.getCurrency()
        );
    }

}
