package ph.mahvine.karappatan.repository;

import ph.mahvine.karappatan.domain.Annex;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Annex entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnnexRepository extends JpaRepository<Annex, Long> {

}
