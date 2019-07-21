package ph.mahvine.karappatan.web.rest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ph.mahvine.karappatan.domain.Answer;
import ph.mahvine.karappatan.domain.CaseSummaryOffer;
import ph.mahvine.karappatan.domain.User;
import ph.mahvine.karappatan.repository.CaseSummaryOfferRepository;
import ph.mahvine.karappatan.repository.CaseSummaryRepository;
import ph.mahvine.karappatan.service.KarappatanService;
import ph.mahvine.karappatan.service.UserService;
import ph.mahvine.karappatan.service.dto.CaseOfferOperationDTO;
import ph.mahvine.karappatan.service.dto.CaseSummaryDTO;
import ph.mahvine.karappatan.service.dto.CreateCaseSummaryDTO;
import ph.mahvine.karappatan.service.dto.CreateOfferDTO;
import ph.mahvine.karappatan.service.mapper.CaseSummaryMapper;
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
    
    private final CaseSummaryRepository caseSummaryRepository;
    
    private final CaseSummaryMapper mapper;
    
    private final CaseSummaryOfferRepository caseSummaryOfferRepository;

    public KarappatanResource(KarappatanService karappatanService, UserService userService, CaseSummaryRepository caseSummaryRepository, CaseSummaryMapper mapper, CaseSummaryOfferRepository caseSummaryOfferRepository) {
        this.karappatanService = karappatanService;
        this.userService = userService;
        this.caseSummaryRepository = caseSummaryRepository;
        this.mapper = mapper;
        this.caseSummaryOfferRepository = caseSummaryOfferRepository;
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
        if(user == null) {
        	throw new RuntimeException("User not logged in");
        }
        CaseSummaryDTO result = karappatanService.createCaseSummary(caseSummaryDTO, user);
        return ResponseEntity.created(new URI("/api/caseSummaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    @GetMapping("/caseSummaries")
    public List<CaseSummaryDTO> getMyCaseSummaries() throws URISyntaxException {
        return caseSummaryRepository.findByUserIsCurrentUser().stream().map( caseSummary -> {
        	CaseSummaryDTO caseSummaryDTO = new CaseSummaryDTO();
        	caseSummaryDTO.setId(caseSummary.getId());
        	caseSummaryDTO.setAnswerIds(caseSummary.getAnswers().stream().map(Answer::getId).collect(Collectors.toList()));
        	caseSummaryDTO.setDateCreated(caseSummary.getDateCreated());
        	return caseSummaryDTO;
        }).collect(Collectors.toList());
    }
    


    /**
     * POST  /caseSummaries/{id}/accept : Accept a caseSummary.
     *
     * @param caseSummaryDTO the caseSummaryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new caseSummaryDTO, or with status 400 (Bad Request) if the caseSummary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
//    @PostMapping("/caseSummaries/{id}/accept")
    public CaseSummaryDTO acceptCaseSummary(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to accept CaseSummary : {}", id);
        User user = userService.getUserWithAuthorities().get();
        CaseSummaryDTO result = karappatanService.acceptCaseSummary(id, user);
        return result;
    }
    

    @PostMapping("/caseSummaries/offers")
    public CaseSummaryOffer createCaseSummaryOffer(@Valid @RequestBody CreateOfferDTO caseSummaryOfferDTO) throws URISyntaxException {
        log.debug("REST request to offer CaseSummary : {}", caseSummaryOfferDTO);
        User user = userService.getUserWithAuthorities().get();
        return karappatanService.createOffer(caseSummaryOfferDTO.getCaseSummaryId(), user);
    }

    @GetMapping("/caseSummaries/{id}/offers")
    public List<CaseSummaryOffer> listCaseSummaryOffer(@PathVariable("id") Long caseSummaryId) throws URISyntaxException {
    	return caseSummaryOfferRepository.findByCaseSummaryId(caseSummaryId);
    }

    @PostMapping("/caseSummaries/offers/accept")
    public void acceptCaseSummaryOffer(@Valid @RequestBody CaseOfferOperationDTO caseSummaryOfferDTO) throws URISyntaxException {
        User user = userService.getUserWithAuthorities().get();
        karappatanService.acceptOffer(caseSummaryOfferDTO.caseSummaryOfferId, user);
    }

    @PostMapping("/caseSummaries/offers/decline")
    public void declineCaseSummaryOffer(@Valid @RequestBody CaseOfferOperationDTO caseSummaryOfferDTO) throws URISyntaxException {
        User user = userService.getUserWithAuthorities().get();
        karappatanService.denyOffer(caseSummaryOfferDTO.caseSummaryOfferId, user);
    }
    

}
