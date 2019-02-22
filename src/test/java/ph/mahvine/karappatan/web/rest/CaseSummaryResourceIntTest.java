package ph.mahvine.karappatan.web.rest;

import ph.mahvine.karappatan.KarappatanApp;

import ph.mahvine.karappatan.domain.CaseSummary;
import ph.mahvine.karappatan.domain.User;
import ph.mahvine.karappatan.repository.CaseSummaryRepository;
import ph.mahvine.karappatan.service.CaseSummaryService;
import ph.mahvine.karappatan.service.dto.CaseSummaryDTO;
import ph.mahvine.karappatan.service.mapper.CaseSummaryMapper;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


import static ph.mahvine.karappatan.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CaseSummaryResource REST controller.
 *
 * @see CaseSummaryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KarappatanApp.class)
public class CaseSummaryResourceIntTest {

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CaseSummaryRepository caseSummaryRepository;

    @Mock
    private CaseSummaryRepository caseSummaryRepositoryMock;

    @Autowired
    private CaseSummaryMapper caseSummaryMapper;

    @Mock
    private CaseSummaryService caseSummaryServiceMock;

    @Autowired
    private CaseSummaryService caseSummaryService;

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

    private MockMvc restCaseSummaryMockMvc;

    private CaseSummary caseSummary;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CaseSummaryResource caseSummaryResource = new CaseSummaryResource(caseSummaryService);
        this.restCaseSummaryMockMvc = MockMvcBuilders.standaloneSetup(caseSummaryResource)
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
    public static CaseSummary createEntity(EntityManager em) {
        CaseSummary caseSummary = new CaseSummary()
            .dateCreated(DEFAULT_DATE_CREATED);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        caseSummary.setUser(user);
        return caseSummary;
    }

    @Before
    public void initTest() {
        caseSummary = createEntity(em);
    }

    @Test
    @Transactional
    public void createCaseSummary() throws Exception {
        int databaseSizeBeforeCreate = caseSummaryRepository.findAll().size();

        // Create the CaseSummary
        CaseSummaryDTO caseSummaryDTO = caseSummaryMapper.toDto(caseSummary);
        restCaseSummaryMockMvc.perform(post("/api/case-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caseSummaryDTO)))
            .andExpect(status().isCreated());

        // Validate the CaseSummary in the database
        List<CaseSummary> caseSummaryList = caseSummaryRepository.findAll();
        assertThat(caseSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        CaseSummary testCaseSummary = caseSummaryList.get(caseSummaryList.size() - 1);
        assertThat(testCaseSummary.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
    }

    @Test
    @Transactional
    public void createCaseSummaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = caseSummaryRepository.findAll().size();

        // Create the CaseSummary with an existing ID
        caseSummary.setId(1L);
        CaseSummaryDTO caseSummaryDTO = caseSummaryMapper.toDto(caseSummary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaseSummaryMockMvc.perform(post("/api/case-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caseSummaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CaseSummary in the database
        List<CaseSummary> caseSummaryList = caseSummaryRepository.findAll();
        assertThat(caseSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCaseSummaries() throws Exception {
        // Initialize the database
        caseSummaryRepository.saveAndFlush(caseSummary);

        // Get all the caseSummaryList
        restCaseSummaryMockMvc.perform(get("/api/case-summaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caseSummary.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCaseSummariesWithEagerRelationshipsIsEnabled() throws Exception {
        CaseSummaryResource caseSummaryResource = new CaseSummaryResource(caseSummaryServiceMock);
        when(caseSummaryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restCaseSummaryMockMvc = MockMvcBuilders.standaloneSetup(caseSummaryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCaseSummaryMockMvc.perform(get("/api/case-summaries?eagerload=true"))
        .andExpect(status().isOk());

        verify(caseSummaryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCaseSummariesWithEagerRelationshipsIsNotEnabled() throws Exception {
        CaseSummaryResource caseSummaryResource = new CaseSummaryResource(caseSummaryServiceMock);
            when(caseSummaryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restCaseSummaryMockMvc = MockMvcBuilders.standaloneSetup(caseSummaryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCaseSummaryMockMvc.perform(get("/api/case-summaries?eagerload=true"))
        .andExpect(status().isOk());

            verify(caseSummaryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCaseSummary() throws Exception {
        // Initialize the database
        caseSummaryRepository.saveAndFlush(caseSummary);

        // Get the caseSummary
        restCaseSummaryMockMvc.perform(get("/api/case-summaries/{id}", caseSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(caseSummary.getId().intValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCaseSummary() throws Exception {
        // Get the caseSummary
        restCaseSummaryMockMvc.perform(get("/api/case-summaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaseSummary() throws Exception {
        // Initialize the database
        caseSummaryRepository.saveAndFlush(caseSummary);

        int databaseSizeBeforeUpdate = caseSummaryRepository.findAll().size();

        // Update the caseSummary
        CaseSummary updatedCaseSummary = caseSummaryRepository.findById(caseSummary.getId()).get();
        // Disconnect from session so that the updates on updatedCaseSummary are not directly saved in db
        em.detach(updatedCaseSummary);
        updatedCaseSummary
            .dateCreated(UPDATED_DATE_CREATED);
        CaseSummaryDTO caseSummaryDTO = caseSummaryMapper.toDto(updatedCaseSummary);

        restCaseSummaryMockMvc.perform(put("/api/case-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caseSummaryDTO)))
            .andExpect(status().isOk());

        // Validate the CaseSummary in the database
        List<CaseSummary> caseSummaryList = caseSummaryRepository.findAll();
        assertThat(caseSummaryList).hasSize(databaseSizeBeforeUpdate);
        CaseSummary testCaseSummary = caseSummaryList.get(caseSummaryList.size() - 1);
        assertThat(testCaseSummary.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingCaseSummary() throws Exception {
        int databaseSizeBeforeUpdate = caseSummaryRepository.findAll().size();

        // Create the CaseSummary
        CaseSummaryDTO caseSummaryDTO = caseSummaryMapper.toDto(caseSummary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaseSummaryMockMvc.perform(put("/api/case-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(caseSummaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CaseSummary in the database
        List<CaseSummary> caseSummaryList = caseSummaryRepository.findAll();
        assertThat(caseSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCaseSummary() throws Exception {
        // Initialize the database
        caseSummaryRepository.saveAndFlush(caseSummary);

        int databaseSizeBeforeDelete = caseSummaryRepository.findAll().size();

        // Delete the caseSummary
        restCaseSummaryMockMvc.perform(delete("/api/case-summaries/{id}", caseSummary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CaseSummary> caseSummaryList = caseSummaryRepository.findAll();
        assertThat(caseSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaseSummary.class);
        CaseSummary caseSummary1 = new CaseSummary();
        caseSummary1.setId(1L);
        CaseSummary caseSummary2 = new CaseSummary();
        caseSummary2.setId(caseSummary1.getId());
        assertThat(caseSummary1).isEqualTo(caseSummary2);
        caseSummary2.setId(2L);
        assertThat(caseSummary1).isNotEqualTo(caseSummary2);
        caseSummary1.setId(null);
        assertThat(caseSummary1).isNotEqualTo(caseSummary2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaseSummaryDTO.class);
        CaseSummaryDTO caseSummaryDTO1 = new CaseSummaryDTO();
        caseSummaryDTO1.setId(1L);
        CaseSummaryDTO caseSummaryDTO2 = new CaseSummaryDTO();
        assertThat(caseSummaryDTO1).isNotEqualTo(caseSummaryDTO2);
        caseSummaryDTO2.setId(caseSummaryDTO1.getId());
        assertThat(caseSummaryDTO1).isEqualTo(caseSummaryDTO2);
        caseSummaryDTO2.setId(2L);
        assertThat(caseSummaryDTO1).isNotEqualTo(caseSummaryDTO2);
        caseSummaryDTO1.setId(null);
        assertThat(caseSummaryDTO1).isNotEqualTo(caseSummaryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(caseSummaryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(caseSummaryMapper.fromId(null)).isNull();
    }
}
