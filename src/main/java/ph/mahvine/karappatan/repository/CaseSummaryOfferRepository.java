package ph.mahvine.karappatan.repository;

import ph.mahvine.karappatan.domain.CaseSummaryOffer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CaseSummaryOffer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaseSummaryOfferRepository extends JpaRepository<CaseSummaryOffer, Long> {

    @Query("select case_summary_offer from CaseSummaryOffer case_summary_offer where case_summary_offer.lawyer.login = ?#{principal.username}")
    List<CaseSummaryOffer> findByLawyerIsCurrentUser();

}
