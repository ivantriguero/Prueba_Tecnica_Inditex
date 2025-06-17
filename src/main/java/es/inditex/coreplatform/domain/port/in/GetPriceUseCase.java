package es.inditex.coreplatform.domain.port.in;

import es.inditex.coreplatform.domain.model.Price;

import java.time.LocalDateTime;

public interface GetPriceUseCase {
    Price getApplicablePrice(LocalDateTime date, Long productId, Long brandId);
}
