package es.inditex.coreplatform.application.usecase;

import es.inditex.coreplatform.domain.model.Price;

import java.time.LocalDateTime;

public interface GetPrice {
    Price getApplicablePrice(LocalDateTime date, Long productId, Long brandId);
}
