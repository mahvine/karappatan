package ph.mahvine.karappatan.web.rest;
import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ph.mahvine.karappatan.domain.User;
import ph.mahvine.karappatan.service.KarappatanService;
import ph.mahvine.karappatan.service.UserService;
import ph.mahvine.karappatan.service.dto.CaseSummaryDTO;
import ph.mahvine.karappatan.service.dto.CreateCaseSummaryDTO;
import ph.mahvine.karappatan.web.rest.errors.BadRequestAlertException;
import ph.mahvine.karappatan.web.rest.util.HeaderUtil;

/**
 * REST controller for managing CaseSummary.
 */
@RestController
@RequestMapping("/api")
public class KarappatanResource {

    private final Logger log = LoggerFactory.getLogger(KarappatanResource.class);

    private static final String ENTITY_NAME = "caseSummary";

    
    private final KarappatanService karappatanService;
    
    private final UserService userService;

    public KarappatanResource(KarappatanService karappatanService, UserService userService) {
        this.karappatanService = karappatanService;
        this.userService = userService;
    }

    /**
     * POST  /caseSummaries : Create a new caseSummary.
     *
     * @param caseSummaryDTO the caseSummaryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new caseSummaryDTO, or with status 400 (Bad Request) if the caseSummary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/caseSummaries")
    public ResponseEntity<CaseSummaryDTO> createCaseSummary(@Valid @RequestBody CreateCaseSummaryDTO caseSummaryDTO) throws URISyntaxException {
        log.debug("REST request to save CaseSummary : {}", caseSummaryDTO);
        User user = userService.getUserWithAuthorities().get();
        CaseSummaryDTO result = karappatanService.createCaseSummary(caseSummaryDTO, user);
        return ResponseEntity.created(new URI("/api/caseSummaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    


    /**
     * POST  /caseSummaries/{id}/accept : Accept a caseSummary.
     *
     * @param caseSummaryDTO the caseSummaryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new caseSummaryDTO, or with status 400 (Bad Request) if the caseSummary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/caseSummaries/{id}/accept")
    public CaseSummaryDTO acceptCaseSummary(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to accept CaseSummary : {}", id);
        User user = userService.getUserWithAuthorities().get();
        CaseSummaryDTO result = karappatanService.acceptCaseSummary(id, user);
        return result;
    }
    

}
