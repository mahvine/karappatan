package ph.mahvine.karappatan.web.rest;
import ph.mahvine.karappatan.service.CaseSummaryService;
import ph.mahvine.karappatan.web.rest.errors.BadRequestAlertException;
import ph.mahvine.karappatan.web.rest.util.HeaderUtil;
import ph.mahvine.karappatan.web.rest.util.PaginationUtil;
import ph.mahvine.karappatan.service.dto.CaseSummaryDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CaseSummary.
 */
@RestController
@RequestMapping("/api")
public class CaseSummaryResource {

    private final Logger log = LoggerFactory.getLogger(CaseSummaryResource.class);

    private static final String ENTITY_NAME = "caseSummary";

    private final CaseSummaryService caseSummaryService;

    public CaseSummaryResource(CaseSummaryService caseSummaryService) {
        this.caseSummaryService = caseSummaryService;
    }

    /**
     * POST  /case-summaries : Create a new caseSummary.
     *
     * @param caseSummaryDTO the caseSummaryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new caseSummaryDTO, or with status 400 (Bad Request) if the caseSummary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/case-summaries")
    public ResponseEntity<CaseSummaryDTO> createCaseSummary(@Valid @RequestBody CaseSummaryDTO caseSummaryDTO) throws URISyntaxException {
        log.debug("REST request to save CaseSummary : {}", caseSummaryDTO);
        if (caseSummaryDTO.getId() != null) {
            throw new BadRequestAlertException("A new caseSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CaseSummaryDTO result = caseSummaryService.save(caseSummaryDTO);
        return ResponseEntity.created(new URI("/api/case-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /case-summaries : Updates an existing caseSummary.
     *
     * @param caseSummaryDTO the caseSummaryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated caseSummaryDTO,
     * or with status 400 (Bad Request) if the caseSummaryDTO is not valid,
     * or with status 500 (Internal Server Error) if the caseSummaryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/case-summaries")
    public ResponseEntity<CaseSummaryDTO> updateCaseSummary(@Valid @RequestBody CaseSummaryDTO caseSummaryDTO) throws URISyntaxException {
        log.debug("REST request to update CaseSummary : {}", caseSummaryDTO);
        if (caseSummaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CaseSummaryDTO result = caseSummaryService.save(caseSummaryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, caseSummaryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /case-summaries : get all the caseSummaries.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of caseSummaries in body
     */
    @GetMapping("/case-summaries")
    public ResponseEntity<List<CaseSummaryDTO>> getAllCaseSummaries(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of CaseSummaries");
        Page<CaseSummaryDTO> page;
        if (eagerload) {
            page = caseSummaryService.findAllWithEagerRelationships(pageable);
        } else {
            page = caseSummaryService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/case-summaries?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /case-summaries/:id : get the "id" caseSummary.
     *
     * @param id the id of the caseSummaryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the caseSummaryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/case-summaries/{id}")
    public ResponseEntity<CaseSummaryDTO> getCaseSummary(@PathVariable Long id) {
        log.debug("REST request to get CaseSummary : {}", id);
        Optional<CaseSummaryDTO> caseSummaryDTO = caseSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(caseSummaryDTO);
    }

    /**
     * DELETE  /case-summaries/:id : delete the "id" caseSummary.
     *
     * @param id the id of the caseSummaryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/case-summaries/{id}")
    public ResponseEntity<Void> deleteCaseSummary(@PathVariable Long id) {
        log.debug("REST request to delete CaseSummary : {}", id);
        caseSummaryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
