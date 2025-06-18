package es.inditex.coreplatform.infrastructure.jpa.repository;

import es.inditex.coreplatform.domain.model.Price;
import es.inditex.coreplatform.domain.port.out.PriceRepository;
import es.inditex.coreplatform.infrastructure.jpa.mapper.PriceEntityMapper;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepository {

    private final JpaPriceRepository jpaPriceRepository;

    @Override
    public List<Price> findPrices(Long productId, Long brandId, LocalDateTime date) {
        return jpaPriceRepository
                .findByProductIdAndBrandIdAndDate(productId, brandId, date)
                .stream()
                .map(PriceEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}
