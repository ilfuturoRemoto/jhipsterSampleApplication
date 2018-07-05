package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Studente;
import io.github.jhipster.application.repository.StudenteRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StudenteResource REST controller.
 *
 * @see StudenteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class StudenteResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_COGNOME = "AAAAAAAAAA";
    private static final String UPDATED_COGNOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ETA = 1;
    private static final Integer UPDATED_ETA = 2;

    @Autowired
    private StudenteRepository studenteRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStudenteMockMvc;

    private Studente studente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudenteResource studenteResource = new StudenteResource(studenteRepository);
        this.restStudenteMockMvc = MockMvcBuilders.standaloneSetup(studenteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Studente createEntity(EntityManager em) {
        Studente studente = new Studente()
            .nome(DEFAULT_NOME)
            .cognome(DEFAULT_COGNOME)
            .eta(DEFAULT_ETA);
        return studente;
    }

    @Before
    public void initTest() {
        studente = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudente() throws Exception {
        int databaseSizeBeforeCreate = studenteRepository.findAll().size();

        // Create the Studente
        restStudenteMockMvc.perform(post("/api/studentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studente)))
            .andExpect(status().isCreated());

        // Validate the Studente in the database
        List<Studente> studenteList = studenteRepository.findAll();
        assertThat(studenteList).hasSize(databaseSizeBeforeCreate + 1);
        Studente testStudente = studenteList.get(studenteList.size() - 1);
        assertThat(testStudente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testStudente.getCognome()).isEqualTo(DEFAULT_COGNOME);
        assertThat(testStudente.getEta()).isEqualTo(DEFAULT_ETA);
    }

    @Test
    @Transactional
    public void createStudenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studenteRepository.findAll().size();

        // Create the Studente with an existing ID
        studente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudenteMockMvc.perform(post("/api/studentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studente)))
            .andExpect(status().isBadRequest());

        // Validate the Studente in the database
        List<Studente> studenteList = studenteRepository.findAll();
        assertThat(studenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStudentes() throws Exception {
        // Initialize the database
        studenteRepository.saveAndFlush(studente);

        // Get all the studenteList
        restStudenteMockMvc.perform(get("/api/studentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cognome").value(hasItem(DEFAULT_COGNOME.toString())))
            .andExpect(jsonPath("$.[*].eta").value(hasItem(DEFAULT_ETA)));
    }
    

    @Test
    @Transactional
    public void getStudente() throws Exception {
        // Initialize the database
        studenteRepository.saveAndFlush(studente);

        // Get the studente
        restStudenteMockMvc.perform(get("/api/studentes/{id}", studente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studente.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.cognome").value(DEFAULT_COGNOME.toString()))
            .andExpect(jsonPath("$.eta").value(DEFAULT_ETA));
    }
    @Test
    @Transactional
    public void getNonExistingStudente() throws Exception {
        // Get the studente
        restStudenteMockMvc.perform(get("/api/studentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudente() throws Exception {
        // Initialize the database
        studenteRepository.saveAndFlush(studente);

        int databaseSizeBeforeUpdate = studenteRepository.findAll().size();

        // Update the studente
        Studente updatedStudente = studenteRepository.findById(studente.getId()).get();
        // Disconnect from session so that the updates on updatedStudente are not directly saved in db
        em.detach(updatedStudente);
        updatedStudente
            .nome(UPDATED_NOME)
            .cognome(UPDATED_COGNOME)
            .eta(UPDATED_ETA);

        restStudenteMockMvc.perform(put("/api/studentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudente)))
            .andExpect(status().isOk());

        // Validate the Studente in the database
        List<Studente> studenteList = studenteRepository.findAll();
        assertThat(studenteList).hasSize(databaseSizeBeforeUpdate);
        Studente testStudente = studenteList.get(studenteList.size() - 1);
        assertThat(testStudente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testStudente.getCognome()).isEqualTo(UPDATED_COGNOME);
        assertThat(testStudente.getEta()).isEqualTo(UPDATED_ETA);
    }

    @Test
    @Transactional
    public void updateNonExistingStudente() throws Exception {
        int databaseSizeBeforeUpdate = studenteRepository.findAll().size();

        // Create the Studente

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStudenteMockMvc.perform(put("/api/studentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studente)))
            .andExpect(status().isBadRequest());

        // Validate the Studente in the database
        List<Studente> studenteList = studenteRepository.findAll();
        assertThat(studenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudente() throws Exception {
        // Initialize the database
        studenteRepository.saveAndFlush(studente);

        int databaseSizeBeforeDelete = studenteRepository.findAll().size();

        // Get the studente
        restStudenteMockMvc.perform(delete("/api/studentes/{id}", studente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Studente> studenteList = studenteRepository.findAll();
        assertThat(studenteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Studente.class);
        Studente studente1 = new Studente();
        studente1.setId(1L);
        Studente studente2 = new Studente();
        studente2.setId(studente1.getId());
        assertThat(studente1).isEqualTo(studente2);
        studente2.setId(2L);
        assertThat(studente1).isNotEqualTo(studente2);
        studente1.setId(null);
        assertThat(studente1).isNotEqualTo(studente2);
    }
}
