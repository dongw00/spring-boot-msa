package se.magnus.microservices.core.recommendation.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RecommendationRepository extends CrudRepository<RecommendationEntity, String> {

    List<RecommendationEntity> findByProductId(int productId);

    Optional<RecommendationEntity> findByRecommendationId(int recommendationId);
}
