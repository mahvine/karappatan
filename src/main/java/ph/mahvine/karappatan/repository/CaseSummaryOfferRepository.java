package ph.mahvine.karappatan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ph.mahvine.karappatan.domain.CaseSummaryOffer;

/**
 * Spring Data  repository for the CaseSummaryOffer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaseSummaryOfferRepository extends JpaRepository<CaseSummaryOffer, Long> {

    @Query("select case_summary_offer from CaseSummaryOffer case_summary_offer where case_summary_offer.lawyer.login = ?#{principal.username}")
    List<CaseSummaryOffer> findByLawyerIsCurrentUser();
    

    List<CaseSummaryOffer> findByCaseSummaryId(Long caseSummaryId);

}
