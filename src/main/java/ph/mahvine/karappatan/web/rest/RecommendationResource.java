package ph.mahvine.karappatan.web.rest;
import ph.mahvine.karappatan.domain.Recommendation;
import ph.mahvine.karappatan.repository.RecommendationRepository;
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
 * REST controller for managing Recommendation.
 */
@RestController
@RequestMapping("/api")
public class RecommendationResource {

    private final Logger log = LoggerFactory.getLogger(RecommendationResource.class);

    private static final String ENTITY_NAME = "recommendation";

    private final RecommendationRepository recommendationRepository;

    public RecommendationResource(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    /**
     * POST  /recommendations : Create a new recommendation.
     *
     * @param recommendation the recommendation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recommendation, or with status 400 (Bad Request) if the recommendation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recommendations")
    public ResponseEntity<Recommendation> createRecommendation(@Valid @RequestBody Recommendation recommendation) throws URISyntaxException {
        log.debug("REST request to save Recommendation : {}", recommendation);
        if (recommendation.getId() != null) {
            throw new BadRequestAlertException("A new recommendation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Recommendation result = recommendationRepository.save(recommendation);
        return ResponseEntity.created(new URI("/api/recommendations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recommendations : Updates an existing recommendation.
     *
     * @param recommendation the recommendation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recommendation,
     * or with status 400 (Bad Request) if the recommendation is not valid,
     * or with status 500 (Internal Server Error) if the recommendation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recommendations")
    public ResponseEntity<Recommendation> updateRecommendation(@Valid @RequestBody Recommendation recommendation) throws URISyntaxException {
        log.debug("REST request to update Recommendation : {}", recommendation);
        if (recommendation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Recommendation result = recommendationRepository.save(recommendation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recommendation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recommendations : get all the recommendations.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of recommendations in body
     */
    @GetMapping("/recommendations")
    public ResponseEntity<List<Recommendation>> getAllRecommendations(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Recommendations");
        Page<Recommendation> page;
        if (eagerload) {
            page = recommendationRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = recommendationRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/recommendations?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /recommendations/:id : get the "id" recommendation.
     *
     * @param id the id of the recommendation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recommendation, or with status 404 (Not Found)
     */
    @GetMapping("/recommendations/{id}")
    public ResponseEntity<Recommendation> getRecommendation(@PathVariable Long id) {
        log.debug("REST request to get Recommendation : {}", id);
        Optional<Recommendation> recommendation = recommendationRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(recommendation);
    }

    /**
     * DELETE  /recommendations/:id : delete the "id" recommendation.
     *
     * @param id the id of the recommendation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recommendations/{id}")
    public ResponseEntity<Void> deleteRecommendation(@PathVariable Long id) {
        log.debug("REST request to delete Recommendation : {}", id);
        recommendationRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
