package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Utente;
import io.github.jhipster.application.repository.UtenteRepository;
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
 * Test class for the UtenteResource REST controller.
 *
 * @see UtenteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class UtenteResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private UtenteRepository utenteRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUtenteMockMvc;

    private Utente utente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UtenteResource utenteResource = new UtenteResource(utenteRepository);
        this.restUtenteMockMvc = MockMvcBuilders.standaloneSetup(utenteResource)
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
    public static Utente createEntity(EntityManager em) {
        Utente utente = new Utente()
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD);
        return utente;
    }

    @Before
    public void initTest() {
        utente = createEntity(em);
    }

    @Test
    @Transactional
    public void createUtente() throws Exception {
        int databaseSizeBeforeCreate = utenteRepository.findAll().size();

        // Create the Utente
        restUtenteMockMvc.perform(post("/api/utentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utente)))
            .andExpect(status().isCreated());

        // Validate the Utente in the database
        List<Utente> utenteList = utenteRepository.findAll();
        assertThat(utenteList).hasSize(databaseSizeBeforeCreate + 1);
        Utente testUtente = utenteList.get(utenteList.size() - 1);
        assertThat(testUtente.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUtente.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void createUtenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = utenteRepository.findAll().size();

        // Create the Utente with an existing ID
        utente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUtenteMockMvc.perform(post("/api/utentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utente)))
            .andExpect(status().isBadRequest());

        // Validate the Utente in the database
        List<Utente> utenteList = utenteRepository.findAll();
        assertThat(utenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUtentes() throws Exception {
        // Initialize the database
        utenteRepository.saveAndFlush(utente);

        // Get all the utenteList
        restUtenteMockMvc.perform(get("/api/utentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(utente.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }
    

    @Test
    @Transactional
    public void getUtente() throws Exception {
        // Initialize the database
        utenteRepository.saveAndFlush(utente);

        // Get the utente
        restUtenteMockMvc.perform(get("/api/utentes/{id}", utente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(utente.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingUtente() throws Exception {
        // Get the utente
        restUtenteMockMvc.perform(get("/api/utentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUtente() throws Exception {
        // Initialize the database
        utenteRepository.saveAndFlush(utente);

        int databaseSizeBeforeUpdate = utenteRepository.findAll().size();

        // Update the utente
        Utente updatedUtente = utenteRepository.findById(utente.getId()).get();
        // Disconnect from session so that the updates on updatedUtente are not directly saved in db
        em.detach(updatedUtente);
        updatedUtente
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD);

        restUtenteMockMvc.perform(put("/api/utentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUtente)))
            .andExpect(status().isOk());

        // Validate the Utente in the database
        List<Utente> utenteList = utenteRepository.findAll();
        assertThat(utenteList).hasSize(databaseSizeBeforeUpdate);
        Utente testUtente = utenteList.get(utenteList.size() - 1);
        assertThat(testUtente.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUtente.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingUtente() throws Exception {
        int databaseSizeBeforeUpdate = utenteRepository.findAll().size();

        // Create the Utente

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUtenteMockMvc.perform(put("/api/utentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(utente)))
            .andExpect(status().isBadRequest());

        // Validate the Utente in the database
        List<Utente> utenteList = utenteRepository.findAll();
        assertThat(utenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUtente() throws Exception {
        // Initialize the database
        utenteRepository.saveAndFlush(utente);

        int databaseSizeBeforeDelete = utenteRepository.findAll().size();

        // Get the utente
        restUtenteMockMvc.perform(delete("/api/utentes/{id}", utente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Utente> utenteList = utenteRepository.findAll();
        assertThat(utenteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Utente.class);
        Utente utente1 = new Utente();
        utente1.setId(1L);
        Utente utente2 = new Utente();
        utente2.setId(utente1.getId());
        assertThat(utente1).isEqualTo(utente2);
        utente2.setId(2L);
        assertThat(utente1).isNotEqualTo(utente2);
        utente1.setId(null);
        assertThat(utente1).isNotEqualTo(utente2);
    }
}
