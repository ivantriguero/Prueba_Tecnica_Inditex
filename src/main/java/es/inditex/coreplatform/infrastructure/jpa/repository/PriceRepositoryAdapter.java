package es.inditex.coreplatform.infrastructure.jpa.repository;

import es.inditex.coreplatform.domain.exception.PriceNotFoundException;
import es.inditex.coreplatform.domain.model.Price;
import es.inditex.coreplatform.domain.port.out.PriceRepository;
import es.inditex.coreplatform.infrastructure.jpa.mapper.PriceEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class PriceRepositoryAdapter implements PriceRepository {

    private final JpaPriceRepository jpaPriceRepository;

    @Override
    public Price findPrice(Long productId, Long brandId, LocalDateTime date) {
        return jpaPriceRepository
                .findTopByProductIdAndBrandIdAndDateOrderByPriorityDesc(productId, brandId, date)
                .map(PriceEntityMapper::toDomain)
                .orElseThrow(() -> new PriceNotFoundException(productId, brandId, date));
    }

}
