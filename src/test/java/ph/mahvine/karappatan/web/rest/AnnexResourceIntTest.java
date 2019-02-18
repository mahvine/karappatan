package ph.mahvine.karappatan.web.rest;

import ph.mahvine.karappatan.KarappatanApp;

import ph.mahvine.karappatan.domain.Annex;
import ph.mahvine.karappatan.repository.AnnexRepository;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static ph.mahvine.karappatan.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AnnexResource REST controller.
 *
 * @see AnnexResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KarappatanApp.class)
public class AnnexResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private AnnexRepository annexRepository;

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

    private MockMvc restAnnexMockMvc;

    private Annex annex;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnnexResource annexResource = new AnnexResource(annexRepository);
        this.restAnnexMockMvc = MockMvcBuilders.standaloneSetup(annexResource)
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
    public static Annex createEntity(EntityManager em) {
        Annex annex = new Annex()
            .content(DEFAULT_CONTENT);
        return annex;
    }

    @Before
    public void initTest() {
        annex = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnnex() throws Exception {
        int databaseSizeBeforeCreate = annexRepository.findAll().size();

        // Create the Annex
        restAnnexMockMvc.perform(post("/api/annexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annex)))
            .andExpect(status().isCreated());

        // Validate the Annex in the database
        List<Annex> annexList = annexRepository.findAll();
        assertThat(annexList).hasSize(databaseSizeBeforeCreate + 1);
        Annex testAnnex = annexList.get(annexList.size() - 1);
        assertThat(testAnnex.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createAnnexWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = annexRepository.findAll().size();

        // Create the Annex with an existing ID
        annex.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnexMockMvc.perform(post("/api/annexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annex)))
            .andExpect(status().isBadRequest());

        // Validate the Annex in the database
        List<Annex> annexList = annexRepository.findAll();
        assertThat(annexList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAnnexes() throws Exception {
        // Initialize the database
        annexRepository.saveAndFlush(annex);

        // Get all the annexList
        restAnnexMockMvc.perform(get("/api/annexes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(annex.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }
    
    @Test
    @Transactional
    public void getAnnex() throws Exception {
        // Initialize the database
        annexRepository.saveAndFlush(annex);

        // Get the annex
        restAnnexMockMvc.perform(get("/api/annexes/{id}", annex.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(annex.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnnex() throws Exception {
        // Get the annex
        restAnnexMockMvc.perform(get("/api/annexes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnnex() throws Exception {
        // Initialize the database
        annexRepository.saveAndFlush(annex);

        int databaseSizeBeforeUpdate = annexRepository.findAll().size();

        // Update the annex
        Annex updatedAnnex = annexRepository.findById(annex.getId()).get();
        // Disconnect from session so that the updates on updatedAnnex are not directly saved in db
        em.detach(updatedAnnex);
        updatedAnnex
            .content(UPDATED_CONTENT);

        restAnnexMockMvc.perform(put("/api/annexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnnex)))
            .andExpect(status().isOk());

        // Validate the Annex in the database
        List<Annex> annexList = annexRepository.findAll();
        assertThat(annexList).hasSize(databaseSizeBeforeUpdate);
        Annex testAnnex = annexList.get(annexList.size() - 1);
        assertThat(testAnnex.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingAnnex() throws Exception {
        int databaseSizeBeforeUpdate = annexRepository.findAll().size();

        // Create the Annex

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnexMockMvc.perform(put("/api/annexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annex)))
            .andExpect(status().isBadRequest());

        // Validate the Annex in the database
        List<Annex> annexList = annexRepository.findAll();
        assertThat(annexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnnex() throws Exception {
        // Initialize the database
        annexRepository.saveAndFlush(annex);

        int databaseSizeBeforeDelete = annexRepository.findAll().size();

        // Delete the annex
        restAnnexMockMvc.perform(delete("/api/annexes/{id}", annex.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Annex> annexList = annexRepository.findAll();
        assertThat(annexList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Annex.class);
        Annex annex1 = new Annex();
        annex1.setId(1L);
        Annex annex2 = new Annex();
        annex2.setId(annex1.getId());
        assertThat(annex1).isEqualTo(annex2);
        annex2.setId(2L);
        assertThat(annex1).isNotEqualTo(annex2);
        annex1.setId(null);
        assertThat(annex1).isNotEqualTo(annex2);
    }
}
