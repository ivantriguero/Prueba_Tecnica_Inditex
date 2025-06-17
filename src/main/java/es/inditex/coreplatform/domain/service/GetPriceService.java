package es.inditex.coreplatform.domain.service;

import es.inditex.coreplatform.domain.exception.PriceNotFoundException;
import es.inditex.coreplatform.domain.model.Price;
import es.inditex.coreplatform.domain.port.in.GetPriceUseCase;
import es.inditex.coreplatform.domain.port.out.PriceRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

@RequiredArgsConstructor
public class GetPriceService implements GetPriceUseCase {

    private final PriceRepository priceRepository;

    @Override
    public Price getApplicablePrice(LocalDateTime date, Long productId, Long brandId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return priceRepository.findPrices(productId, brandId, date).stream()
                .max(Comparator.comparingInt(Price::getPriority))
                .orElseThrow(() -> new PriceNotFoundException(productId, brandId, date.format(formatter)));
    }
}