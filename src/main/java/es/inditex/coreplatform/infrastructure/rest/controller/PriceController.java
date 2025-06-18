package es.inditex.coreplatform.infrastructure.rest.controller;


import es.inditex.coreplatform.adapter.infrastructure.rest.openapi.controller.DefaultApi;
import es.inditex.coreplatform.adapter.infrastructure.rest.openapi.dto.PriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class PriceController implements DefaultApi {
    public ResponseEntity<PriceResponse> getApplicablePrice(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String date,
            Long productId,
            Long brandId
    ) {
        return null;
    }
}
