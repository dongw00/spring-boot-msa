package se.magnus.microservices.core.recommendation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import se.magnus.microservices.core.recommendation.persistence.RecommendationEntity;
import se.magnus.microservices.core.recommendation.persistence.RecommendationRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
public class PersistenceTests {

    @Autowired
    private RecommendationRepository repository;

    private RecommendationEntity savedEntity;

    @BeforeEach
    public void setup() {
        repository.deleteAll().block();

        RecommendationEntity entity = new RecommendationEntity(1, 2, "a", 3, "c");
        savedEntity = repository.save(entity).block();

        assertEqualsRecommendation(entity, savedEntity);
    }

    @DisplayName("생성 테스트")
    @Test
    public void create() {
        RecommendationEntity newEntity = new RecommendationEntity(1, 3, "a", 3, "c");
        repository.save(newEntity).block();

        RecommendationEntity foundEntity = repository.findById(newEntity.getId()).block();
        assertEqualsRecommendation(newEntity, foundEntity);

        assertEquals(2, repository.count().block());
    }

    @DisplayName("수정 테스트")
    @Test
    public void update() {
        savedEntity.setAuthor("a2");
        repository.save(savedEntity).block();

        RecommendationEntity foundEntity = repository.findById(savedEntity.getId()).block();
        assertEquals(1, (long) foundEntity.getVersion());
        assertEquals("a2", foundEntity.getAuthor());
    }

    @DisplayName("삭제 테스트")
    @Test
    public void delete() {
        repository.delete(savedEntity).block();
        assertFalse(repository.existsById(savedEntity.getId()).block());
    }

    @DisplayName("SELECT 테스트")
    @Test
    public void getByRecommendationId() {
        List<RecommendationEntity> entity = repository.findByProductId(savedEntity.getProductId())
                .collectList().block();

        assertEquals(1, entity.size());
        assertEqualsRecommendation(savedEntity, entity.get(0));
    }

    @DisplayName("낙관적락 테스트")
    @Test
    public void optimisticLockError() {
        RecommendationEntity entity1 = repository.findById(savedEntity.getId()).block();
        RecommendationEntity entity2 = repository.findById(savedEntity.getId()).block();

        entity1.setAuthor("a1");
        repository.save(entity1).block();

        try {
            entity2.setAuthor("a2");
            repository.save(entity2).block();

            fail("Expected an optimisticLockingFailureException");
        } catch (OptimisticLockingFailureException e) {

        }

        RecommendationEntity updatedEntity = repository.findById(savedEntity.getId()).block();
        assertEquals(1, (int) updatedEntity.getVersion());
        assertEquals("a1", updatedEntity.getAuthor());
    }

    private void assertEqualsRecommendation(RecommendationEntity expectedEntity, RecommendationEntity actualEntity) {
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getVersion(), actualEntity.getVersion());
        assertEquals(expectedEntity.getProductId(), actualEntity.getProductId());
        assertEquals(expectedEntity.getRecommendationId(), actualEntity.getRecommendationId());
        assertEquals(expectedEntity.getAuthor(), actualEntity.getAuthor());
        assertEquals(expectedEntity.getRating(), actualEntity.getRating());
        assertEquals(expectedEntity.getContent(), actualEntity.getContent());
    }
}
