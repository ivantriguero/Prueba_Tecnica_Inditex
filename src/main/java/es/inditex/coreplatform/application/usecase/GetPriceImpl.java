package es.inditex.coreplatform.application.usecase;

import es.inditex.coreplatform.domain.exception.PriceNotFoundException;
import es.inditex.coreplatform.domain.model.Price;
import es.inditex.coreplatform.domain.port.out.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

@RequiredArgsConstructor
@Component
public class GetPriceImpl implements GetPrice {

    private final PriceRepository priceRepository;

    @Override
    public Price getApplicablePrice(LocalDateTime date, Long productId, Long brandId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return priceRepository.findPrices(productId, brandId, date).stream()
                .max(Comparator.comparingInt(Price::getPriority))
                .orElseThrow(() -> new PriceNotFoundException(productId, brandId, date.format(formatter)));
    }
}