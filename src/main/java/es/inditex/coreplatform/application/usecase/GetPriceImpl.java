package es.inditex.coreplatform.application.usecase;

import es.inditex.coreplatform.domain.model.Price;
import es.inditex.coreplatform.domain.port.out.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class GetPriceImpl implements GetPrice {

    private final PriceRepository priceRepository;

    @Override
    public Price getApplicablePrice(LocalDateTime date, Long productId, Long brandId) {
        return priceRepository.findPrice(productId, brandId, date);
    }

}