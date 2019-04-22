package ph.mahvine.karappatan.web.rest;

import ph.mahvine.karappatan.KarappatanApp;

import ph.mahvine.karappatan.domain.Recommendation;
import ph.mahvine.karappatan.repository.RecommendationRepository;
import ph.mahvine.karappatan.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static ph.mahvine.karappatan.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RecommendationResource REST controller.
 *
 * @see RecommendationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KarappatanApp.class)
public class RecommendationResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Mock
    private RecommendationRepository recommendationRepositoryMock;

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

    private MockMvc restRecommendationMockMvc;

    private Recommendation recommendation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecommendationResource recommendationResource = new RecommendationResource(recommendationRepository);
        this.restRecommendationMockMvc = MockMvcBuilders.standaloneSetup(recommendationResource)
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
    public static Recommendation createEntity(EntityManager em) {
        Recommendation recommendation = new Recommendation()
            .content(DEFAULT_CONTENT)
            .identifier(DEFAULT_IDENTIFIER);
        return recommendation;
    }

    @Before
    public void initTest() {
        recommendation = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecommendation() throws Exception {
        int databaseSizeBeforeCreate = recommendationRepository.findAll().size();

        // Create the Recommendation
        restRecommendationMockMvc.perform(post("/api/recommendations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recommendation)))
            .andExpect(status().isCreated());

        // Validate the Recommendation in the database
        List<Recommendation> recommendationList = recommendationRepository.findAll();
        assertThat(recommendationList).hasSize(databaseSizeBeforeCreate + 1);
        Recommendation testRecommendation = recommendationList.get(recommendationList.size() - 1);
        assertThat(testRecommendation.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testRecommendation.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void createRecommendationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recommendationRepository.findAll().size();

        // Create the Recommendation with an existing ID
        recommendation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecommendationMockMvc.perform(post("/api/recommendations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recommendation)))
            .andExpect(status().isBadRequest());

        // Validate the Recommendation in the database
        List<Recommendation> recommendationList = recommendationRepository.findAll();
        assertThat(recommendationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = recommendationRepository.findAll().size();
        // set the field null
        recommendation.setIdentifier(null);

        // Create the Recommendation, which fails.

        restRecommendationMockMvc.perform(post("/api/recommendations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recommendation)))
            .andExpect(status().isBadRequest());

        List<Recommendation> recommendationList = recommendationRepository.findAll();
        assertThat(recommendationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecommendations() throws Exception {
        // Initialize the database
        recommendationRepository.saveAndFlush(recommendation);

        // Get all the recommendationList
        restRecommendationMockMvc.perform(get("/api/recommendations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recommendation.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllRecommendationsWithEagerRelationshipsIsEnabled() throws Exception {
        RecommendationResource recommendationResource = new RecommendationResource(recommendationRepositoryMock);
        when(recommendationRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRecommendationMockMvc = MockMvcBuilders.standaloneSetup(recommendationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRecommendationMockMvc.perform(get("/api/recommendations?eagerload=true"))
        .andExpect(status().isOk());

        verify(recommendationRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllRecommendationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        RecommendationResource recommendationResource = new RecommendationResource(recommendationRepositoryMock);
            when(recommendationRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRecommendationMockMvc = MockMvcBuilders.standaloneSetup(recommendationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRecommendationMockMvc.perform(get("/api/recommendations?eagerload=true"))
        .andExpect(status().isOk());

            verify(recommendationRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRecommendation() throws Exception {
        // Initialize the database
        recommendationRepository.saveAndFlush(recommendation);

        // Get the recommendation
        restRecommendationMockMvc.perform(get("/api/recommendations/{id}", recommendation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recommendation.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRecommendation() throws Exception {
        // Get the recommendation
        restRecommendationMockMvc.perform(get("/api/recommendations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecommendation() throws Exception {
        // Initialize the database
        recommendationRepository.saveAndFlush(recommendation);

        int databaseSizeBeforeUpdate = recommendationRepository.findAll().size();

        // Update the recommendation
        Recommendation updatedRecommendation = recommendationRepository.findById(recommendation.getId()).get();
        // Disconnect from session so that the updates on updatedRecommendation are not directly saved in db
        em.detach(updatedRecommendation);
        updatedRecommendation
            .content(UPDATED_CONTENT)
            .identifier(UPDATED_IDENTIFIER);

        restRecommendationMockMvc.perform(put("/api/recommendations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecommendation)))
            .andExpect(status().isOk());

        // Validate the Recommendation in the database
        List<Recommendation> recommendationList = recommendationRepository.findAll();
        assertThat(recommendationList).hasSize(databaseSizeBeforeUpdate);
        Recommendation testRecommendation = recommendationList.get(recommendationList.size() - 1);
        assertThat(testRecommendation.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testRecommendation.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingRecommendation() throws Exception {
        int databaseSizeBeforeUpdate = recommendationRepository.findAll().size();

        // Create the Recommendation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecommendationMockMvc.perform(put("/api/recommendations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recommendation)))
            .andExpect(status().isBadRequest());

        // Validate the Recommendation in the database
        List<Recommendation> recommendationList = recommendationRepository.findAll();
        assertThat(recommendationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecommendation() throws Exception {
        // Initialize the database
        recommendationRepository.saveAndFlush(recommendation);

        int databaseSizeBeforeDelete = recommendationRepository.findAll().size();

        // Delete the recommendation
        restRecommendationMockMvc.perform(delete("/api/recommendations/{id}", recommendation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Recommendation> recommendationList = recommendationRepository.findAll();
        assertThat(recommendationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recommendation.class);
        Recommendation recommendation1 = new Recommendation();
        recommendation1.setId(1L);
        Recommendation recommendation2 = new Recommendation();
        recommendation2.setId(recommendation1.getId());
        assertThat(recommendation1).isEqualTo(recommendation2);
        recommendation2.setId(2L);
        assertThat(recommendation1).isNotEqualTo(recommendation2);
        recommendation1.setId(null);
        assertThat(recommendation1).isNotEqualTo(recommendation2);
    }
}
