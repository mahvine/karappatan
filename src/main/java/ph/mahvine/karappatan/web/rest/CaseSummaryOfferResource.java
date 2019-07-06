package ph.mahvine.karappatan.web.rest;
import ph.mahvine.karappatan.domain.CaseSummaryOffer;
import ph.mahvine.karappatan.repository.CaseSummaryOfferRepository;
import ph.mahvine.karappatan.web.rest.errors.BadRequestAlertException;
import ph.mahvine.karappatan.web.rest.util.HeaderUtil;
import ph.mahvine.karappatan.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CaseSummaryOffer.
 */
@RestController
@RequestMapping("/api")
public class CaseSummaryOfferResource {

    private final Logger log = LoggerFactory.getLogger(CaseSummaryOfferResource.class);

    private static final String ENTITY_NAME = "caseSummaryOffer";

    private final CaseSummaryOfferRepository caseSummaryOfferRepository;

    public CaseSummaryOfferResource(CaseSummaryOfferRepository caseSummaryOfferRepository) {
        this.caseSummaryOfferRepository = caseSummaryOfferRepository;
    }

    /**
     * POST  /case-summary-offers : Create a new caseSummaryOffer.
     *
     * @param caseSummaryOffer the caseSummaryOffer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new caseSummaryOffer, or with status 400 (Bad Request) if the caseSummaryOffer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/case-summary-offers")
    public ResponseEntity<CaseSummaryOffer> createCaseSummaryOffer(@RequestBody CaseSummaryOffer caseSummaryOffer) throws URISyntaxException {
        log.debug("REST request to save CaseSummaryOffer : {}", caseSummaryOffer);
        if (caseSummaryOffer.getId() != null) {
            throw new BadRequestAlertException("A new caseSummaryOffer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CaseSummaryOffer result = caseSummaryOfferRepository.save(caseSummaryOffer);
        return ResponseEntity.created(new URI("/api/case-summary-offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /case-summary-offers : Updates an existing caseSummaryOffer.
     *
     * @param caseSummaryOffer the caseSummaryOffer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated caseSummaryOffer,
     * or with status 400 (Bad Request) if the caseSummaryOffer is not valid,
     * or with status 500 (Internal Server Error) if the caseSummaryOffer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/case-summary-offers")
    public ResponseEntity<CaseSummaryOffer> updateCaseSummaryOffer(@RequestBody CaseSummaryOffer caseSummaryOffer) throws URISyntaxException {
        log.debug("REST request to update CaseSummaryOffer : {}", caseSummaryOffer);
        if (caseSummaryOffer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CaseSummaryOffer result = caseSummaryOfferRepository.save(caseSummaryOffer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, caseSummaryOffer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /case-summary-offers : get all the caseSummaryOffers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of caseSummaryOffers in body
     */
    @GetMapping("/case-summary-offers")
    public ResponseEntity<List<CaseSummaryOffer>> getAllCaseSummaryOffers(Pageable pageable) {
        log.debug("REST request to get a page of CaseSummaryOffers");
        Page<CaseSummaryOffer> page = caseSummaryOfferRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/case-summary-offers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /case-summary-offers/:id : get the "id" caseSummaryOffer.
     *
     * @param id the id of the caseSummaryOffer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the caseSummaryOffer, or with status 404 (Not Found)
     */
    @GetMapping("/case-summary-offers/{id}")
    public ResponseEntity<CaseSummaryOffer> getCaseSummaryOffer(@PathVariable Long id) {
        log.debug("REST request to get CaseSummaryOffer : {}", id);
        Optional<CaseSummaryOffer> caseSummaryOffer = caseSummaryOfferRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(caseSummaryOffer);
    }

    /**
     * DELETE  /case-summary-offers/:id : delete the "id" caseSummaryOffer.
     *
     * @param id the id of the caseSummaryOffer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/case-summary-offers/{id}")
    public ResponseEntity<Void> deleteCaseSummaryOffer(@PathVariable Long id) {
        log.debug("REST request to delete CaseSummaryOffer : {}", id);
        caseSummaryOfferRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
