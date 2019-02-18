package ph.mahvine.karappatan.repository;

import ph.mahvine.karappatan.domain.Recommendation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Recommendation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

}
