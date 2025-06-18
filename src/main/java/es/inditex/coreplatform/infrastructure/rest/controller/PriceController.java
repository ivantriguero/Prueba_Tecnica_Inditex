package es.inditex.coreplatform.infrastructure.rest.controller;


import es.inditex.coreplatform.adapter.infrastructure.rest.openapi.controller.PriceApi;
import es.inditex.coreplatform.adapter.infrastructure.rest.openapi.dto.PriceResponseDTO;
import es.inditex.coreplatform.application.usecase.GetPrice;
import es.inditex.coreplatform.domain.model.Price;
import es.inditex.coreplatform.infrastructure.rest.controller.mapper.PriceResponseDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;


@RestController
@RequiredArgsConstructor
public class PriceController implements PriceApi {

    private final GetPrice getPrice;

    @Override
    public ResponseEntity<PriceResponseDTO> getApplicablePrice(
            OffsetDateTime date,
            Long productId,
            Long brandId
    ) {

        Price price = getPrice.getApplicablePrice(date.toLocalDateTime(), productId, brandId);
        PriceResponseDTO dto = PriceResponseDtoMapper.toPriceResponseDTO(price);
        return ResponseEntity.ok(dto);
    }
}
