package es.inditex.coreplatform.infrastructure.jpa.repository;

import es.inditex.coreplatform.infrastructure.jpa.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {
    @Query(value = "SELECT * FROM prices p " +
            "WHERE p.product_id = :productId " +
            "AND p.brand_id = :brandId " +
            "AND :applicationDate BETWEEN p.start_date AND p.end_date " +
            "ORDER BY p.priority DESC LIMIT 1", nativeQuery = true)
    Optional<PriceEntity> findTopByProductIdAndBrandIdAndDateOrderByPriorityDesc(
            Long productId, Long brandId, LocalDateTime applicationDate);


}
