package ph.mahvine.karappatan.web.rest;
import ph.mahvine.karappatan.domain.Annex;
import ph.mahvine.karappatan.repository.AnnexRepository;
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

    private final AnnexRepository annexRepository;

    public AnnexResource(AnnexRepository annexRepository) {
        this.annexRepository = annexRepository;
    }

    /**
     * POST  /annexes : Create a new annex.
     *
     * @param annex the annex to create
     * @return the ResponseEntity with status 201 (Created) and with body the new annex, or with status 400 (Bad Request) if the annex has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/annexes")
    public ResponseEntity<Annex> createAnnex(@Valid @RequestBody Annex annex) throws URISyntaxException {
        log.debug("REST request to save Annex : {}", annex);
        if (annex.getId() != null) {
            throw new BadRequestAlertException("A new annex cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Annex result = annexRepository.save(annex);
        return ResponseEntity.created(new URI("/api/annexes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /annexes : Updates an existing annex.
     *
     * @param annex the annex to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated annex,
     * or with status 400 (Bad Request) if the annex is not valid,
     * or with status 500 (Internal Server Error) if the annex couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/annexes")
    public ResponseEntity<Annex> updateAnnex(@Valid @RequestBody Annex annex) throws URISyntaxException {
        log.debug("REST request to update Annex : {}", annex);
        if (annex.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Annex result = annexRepository.save(annex);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, annex.getId().toString()))
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
    public ResponseEntity<List<Annex>> getAllAnnexes(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Annexes");
        Page<Annex> page;
        if (eagerload) {
            page = annexRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = annexRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/annexes?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /annexes/:id : get the "id" annex.
     *
     * @param id the id of the annex to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the annex, or with status 404 (Not Found)
     */
    @GetMapping("/annexes/{id}")
    public ResponseEntity<Annex> getAnnex(@PathVariable Long id) {
        log.debug("REST request to get Annex : {}", id);
        Optional<Annex> annex = annexRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(annex);
    }

    /**
     * DELETE  /annexes/:id : delete the "id" annex.
     *
     * @param id the id of the annex to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/annexes/{id}")
    public ResponseEntity<Void> deleteAnnex(@PathVariable Long id) {
        log.debug("REST request to delete Annex : {}", id);
        annexRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
