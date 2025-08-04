package es.inditex.coreplatform.domain.port.out;

import es.inditex.coreplatform.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository {
    Price findPrice(Long productId, Long brandId, LocalDateTime date);
}
