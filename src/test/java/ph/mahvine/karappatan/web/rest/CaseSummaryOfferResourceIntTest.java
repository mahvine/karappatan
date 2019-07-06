package ph.mahvine.karappatan.web.rest;

import ph.mahvine.karappatan.KarappatanApp;

import ph.mahvine.karappatan.domain.CaseSummaryOffer;
import ph.mahvine.karappatan.repository.CaseSummaryOfferRepository;
import ph.mahvine.karappatan.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static ph.mahvine.karappatan.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ph.mahvine.karappatan.domain.enumeration.OfferStatus;
/**
 * Test class for the CaseSummaryOfferResource REST controller.
 *
 * @see CaseSummaryOfferResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KarappatanApp.class)
public class CaseSummaryOfferResourceIntTest {

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final OfferStatus DEFAULT_STATUS = OfferStatus.OPEN;
    private static final OfferStatus UPDATED_STATUS = OfferStatus.DENIED;

    @Autowired
    private CaseSummaryOfferRepository caseSummaryOfferRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCaseSummaryOfferMockMvc;

    private CaseSummaryOffer caseSummaryOffer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CaseSummaryOfferResource caseSummaryOfferResource = new CaseSummaryOfferResource(caseSummaryOfferRepository);
        this.restCaseSummaryOfferMockMvc = MockMvcBuilders.standaloneSetup(caseSummaryOfferResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CaseSummaryOffer createEntity(EntityManager em) {
        CaseSummaryOffer caseSummaryOffer = new CaseSummaryOffer()
            .dateCreated(DEFAULT_DATE_CREATED)
            .status(DEFAULT_STATUS);
        return caseSummaryOffer;
    }

    @Before
    public void initTest() {
        caseSummaryOffer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCaseSummaryOffer() throws Exception {
        int databaseSizeBeforeCreate = caseSummaryOfferRepository.findAll().size();

        // Create the CaseSummaryOffer
        restCaseSummaryOfferMockMvc.perform(post("/api/case-summary-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caseSummaryOffer)))
            .andExpect(status().isCreated());

        // Validate the CaseSummaryOffer in the database
        List<CaseSummaryOffer> caseSummaryOfferList = caseSummaryOfferRepository.findAll();
        assertThat(caseSummaryOfferList).hasSize(databaseSizeBeforeCreate + 1);
        CaseSummaryOffer testCaseSummaryOffer = caseSummaryOfferList.get(caseSummaryOfferList.size() - 1);
        assertThat(testCaseSummaryOffer.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCaseSummaryOffer.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCaseSummaryOfferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = caseSummaryOfferRepository.findAll().size();

        // Create the CaseSummaryOffer with an existing ID
        caseSummaryOffer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaseSummaryOfferMockMvc.perform(post("/api/case-summary-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caseSummaryOffer)))
            .andExpect(status().isBadRequest());

        // Validate the CaseSummaryOffer in the database
        List<CaseSummaryOffer> caseSummaryOfferList = caseSummaryOfferRepository.findAll();
        assertThat(caseSummaryOfferList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCaseSummaryOffers() throws Exception {
        // Initialize the database
        caseSummaryOfferRepository.saveAndFlush(caseSummaryOffer);

        // Get all the caseSummaryOfferList
        restCaseSummaryOfferMockMvc.perform(get("/api/case-summary-offers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caseSummaryOffer.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getCaseSummaryOffer() throws Exception {
        // Initialize the database
        caseSummaryOfferRepository.saveAndFlush(caseSummaryOffer);

        // Get the caseSummaryOffer
        restCaseSummaryOfferMockMvc.perform(get("/api/case-summary-offers/{id}", caseSummaryOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(caseSummaryOffer.getId().intValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCaseSummaryOffer() throws Exception {
        // Get the caseSummaryOffer
        restCaseSummaryOfferMockMvc.perform(get("/api/case-summary-offers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaseSummaryOffer() throws Exception {
        // Initialize the database
        caseSummaryOfferRepository.saveAndFlush(caseSummaryOffer);

        int databaseSizeBeforeUpdate = caseSummaryOfferRepository.findAll().size();

        // Update the caseSummaryOffer
        CaseSummaryOffer updatedCaseSummaryOffer = caseSummaryOfferRepository.findById(caseSummaryOffer.getId()).get();
        // Disconnect from session so that the updates on updatedCaseSummaryOffer are not directly saved in db
        em.detach(updatedCaseSummaryOffer);
        updatedCaseSummaryOffer
            .dateCreated(UPDATED_DATE_CREATED)
            .status(UPDATED_STATUS);

        restCaseSummaryOfferMockMvc.perform(put("/api/case-summary-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCaseSummaryOffer)))
            .andExpect(status().isOk());

        // Validate the CaseSummaryOffer in the database
        List<CaseSummaryOffer> caseSummaryOfferList = caseSummaryOfferRepository.findAll();
        assertThat(caseSummaryOfferList).hasSize(databaseSizeBeforeUpdate);
        CaseSummaryOffer testCaseSummaryOffer = caseSummaryOfferList.get(caseSummaryOfferList.size() - 1);
        assertThat(testCaseSummaryOffer.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCaseSummaryOffer.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingCaseSummaryOffer() throws Exception {
        int databaseSizeBeforeUpdate = caseSummaryOfferRepository.findAll().size();

        // Create the CaseSummaryOffer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaseSummaryOfferMockMvc.perform(put("/api/case-summary-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caseSummaryOffer)))
            .andExpect(status().isBadRequest());

        // Validate the CaseSummaryOffer in the database
        List<CaseSummaryOffer> caseSummaryOfferList = caseSummaryOfferRepository.findAll();
        assertThat(caseSummaryOfferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCaseSummaryOffer() throws Exception {
        // Initialize the database
        caseSummaryOfferRepository.saveAndFlush(caseSummaryOffer);

        int databaseSizeBeforeDelete = caseSummaryOfferRepository.findAll().size();

        // Delete the caseSummaryOffer
        restCaseSummaryOfferMockMvc.perform(delete("/api/case-summary-offers/{id}", caseSummaryOffer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CaseSummaryOffer> caseSummaryOfferList = caseSummaryOfferRepository.findAll();
        assertThat(caseSummaryOfferList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaseSummaryOffer.class);
        CaseSummaryOffer caseSummaryOffer1 = new CaseSummaryOffer();
        caseSummaryOffer1.setId(1L);
        CaseSummaryOffer caseSummaryOffer2 = new CaseSummaryOffer();
        caseSummaryOffer2.setId(caseSummaryOffer1.getId());
        assertThat(caseSummaryOffer1).isEqualTo(caseSummaryOffer2);
        caseSummaryOffer2.setId(2L);
        assertThat(caseSummaryOffer1).isNotEqualTo(caseSummaryOffer2);
        caseSummaryOffer1.setId(null);
        assertThat(caseSummaryOffer1).isNotEqualTo(caseSummaryOffer2);
    }
}
