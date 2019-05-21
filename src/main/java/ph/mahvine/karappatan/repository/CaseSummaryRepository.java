package ph.mahvine.karappatan.repository;

import ph.mahvine.karappatan.domain.CaseSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the CaseSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaseSummaryRepository extends JpaRepository<CaseSummary, Long> {

    @Query("select case_summary from CaseSummary case_summary left join fetch case_summary.answers where case_summary.user.login = ?#{principal.username} ")
    List<CaseSummary> findByUserIsCurrentUser();

    @Query(value = "select distinct case_summary from CaseSummary case_summary left join fetch case_summary.answers",
        countQuery = "select count(distinct case_summary) from CaseSummary case_summary")
    Page<CaseSummary> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct case_summary from CaseSummary case_summary left join fetch case_summary.answers")
    List<CaseSummary> findAllWithEagerRelationships();

    @Query("select case_summary from CaseSummary case_summary left join fetch case_summary.answers where case_summary.id =:id")
    Optional<CaseSummary> findOneWithEagerRelationships(@Param("id") Long id);

}
