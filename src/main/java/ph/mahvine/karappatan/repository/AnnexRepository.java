package ph.mahvine.karappatan.repository;

import ph.mahvine.karappatan.domain.Annex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Annex entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnnexRepository extends JpaRepository<Annex, Long> {

    @Query(value = "select distinct krptn_annex from Annex krptn_annex left join fetch krptn_annex.nextQuestions",
        countQuery = "select count(distinct krptn_annex) from Annex krptn_annex")
    Page<Annex> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct krptn_annex from Annex krptn_annex left join fetch krptn_annex.nextQuestions")
    List<Annex> findAllWithEagerRelationships();

    @Query("select krptn_annex from Annex krptn_annex left join fetch krptn_annex.nextQuestions where krptn_annex.id =:id")
    Optional<Annex> findOneWithEagerRelationships(@Param("id") Long id);

    Optional<Annex> findOneByIdentifier(String identifier);

}
