package es.inditex.coreplatform.infrastructure.jpa.repository;

import es.inditex.coreplatform.infrastructure.jpa.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {
    @Query("SELECT p FROM PriceEntity p WHERE p.productId = :productId AND p.brandId = :brandId " +
            "AND :applicationDate BETWEEN p.startDate AND p.endDate")
    List<PriceEntity> findByProductIdAndBrandIdAndDate(Long productId, Long brandId, LocalDateTime applicationDate);

}
