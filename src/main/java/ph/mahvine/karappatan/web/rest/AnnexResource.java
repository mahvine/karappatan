package ph.mahvine.karappatan.web.rest;
import ph.mahvine.karappatan.service.AnnexService;
import ph.mahvine.karappatan.web.rest.errors.BadRequestAlertException;
import ph.mahvine.karappatan.web.rest.util.HeaderUtil;
import ph.mahvine.karappatan.web.rest.util.PaginationUtil;
import ph.mahvine.karappatan.service.dto.AnnexDTO;
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
 * REST controller for managing Annex.
 */
@RestController
@RequestMapping("/api")
public class AnnexResource {

    private final Logger log = LoggerFactory.getLogger(AnnexResource.class);

    private static final String ENTITY_NAME = "annex";

    private final AnnexService annexService;

    public AnnexResource(AnnexService annexService) {
        this.annexService = annexService;
    }

    /**
     * POST  /annexes : Create a new annex.
     *
     * @param annexDTO the annexDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new annexDTO, or with status 400 (Bad Request) if the annex has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/annexes")
    public ResponseEntity<AnnexDTO> createAnnex(@Valid @RequestBody AnnexDTO annexDTO) throws URISyntaxException {
        log.debug("REST request to save Annex : {}", annexDTO);
        if (annexDTO.getId() != null) {
            throw new BadRequestAlertException("A new annex cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnnexDTO result = annexService.save(annexDTO);
        return ResponseEntity.created(new URI("/api/annexes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /annexes : Updates an existing annex.
     *
     * @param annexDTO the annexDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated annexDTO,
     * or with status 400 (Bad Request) if the annexDTO is not valid,
     * or with status 500 (Internal Server Error) if the annexDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/annexes")
    public ResponseEntity<AnnexDTO> updateAnnex(@Valid @RequestBody AnnexDTO annexDTO) throws URISyntaxException {
        log.debug("REST request to update Annex : {}", annexDTO);
        if (annexDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnnexDTO result = annexService.save(annexDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, annexDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /annexes : get all the annexes.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of annexes in body
     */
    @GetMapping("/annexes")
    public ResponseEntity<List<AnnexDTO>> getAllAnnexes(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Annexes");
        Page<AnnexDTO> page;
        if (eagerload) {
            page = annexService.findAllWithEagerRelationships(pageable);
        } else {
            page = annexService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/annexes?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /annexes/:id : get the "id" annex.
     *
     * @param id the id of the annexDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the annexDTO, or with status 404 (Not Found)
     */
    @GetMapping("/annexes/{id}")
    public ResponseEntity<AnnexDTO> getAnnex(@PathVariable Long id) {
        log.debug("REST request to get Annex : {}", id);
        Optional<AnnexDTO> annexDTO = annexService.findOne(id);
        return ResponseUtil.wrapOrNotFound(annexDTO);
    }

    /**
     * DELETE  /annexes/:id : delete the "id" annex.
     *
     * @param id the id of the annexDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/annexes/{id}")
    public ResponseEntity<Void> deleteAnnex(@PathVariable Long id) {
        log.debug("REST request to delete Annex : {}", id);
        annexService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
