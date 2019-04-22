package ph.mahvine.karappatan.repository;

import ph.mahvine.karappatan.domain.Recommendation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Recommendation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    @Query(value = "select distinct krptn_recommendation from Recommendation krptn_recommendation left join fetch krptn_recommendation.nextQuestions",
        countQuery = "select count(distinct krptn_recommendation) from Recommendation krptn_recommendation")
    Page<Recommendation> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct krptn_recommendation from Recommendation krptn_recommendation left join fetch krptn_recommendation.nextQuestions")
    List<Recommendation> findAllWithEagerRelationships();

    @Query("select krptn_recommendation from Recommendation krptn_recommendation left join fetch krptn_recommendation.nextQuestions where krptn_recommendation.id =:id")
    Optional<Recommendation> findOneWithEagerRelationships(@Param("id") Long id);

    Optional<Recommendation> findOneByIdentifier(String identifier);

}
