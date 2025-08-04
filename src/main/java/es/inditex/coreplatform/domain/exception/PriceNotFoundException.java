package es.inditex.coreplatform.domain.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(Long productId, Long brandId, LocalDateTime date) {
        super(String.format("Precio no encontrado para productId=%d, brandId=%d on date=%s",
                productId, brandId, date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
    }
}

