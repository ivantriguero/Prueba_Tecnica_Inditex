package es.inditex.coreplatform.domain.exception;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(Long productId, Long brandId, String date) {
        super(String.format("No price found for productId=%d, brandId=%d on date=%s", productId, brandId, date));
    }
}
