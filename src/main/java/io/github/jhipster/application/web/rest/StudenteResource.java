package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Studente;
import io.github.jhipster.application.repository.StudenteRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Studente.
 */
@RestController
@RequestMapping("/api")
public class StudenteResource {

    private final Logger log = LoggerFactory.getLogger(StudenteResource.class);

    private static final String ENTITY_NAME = "studente";

    private final StudenteRepository studenteRepository;

    public StudenteResource(StudenteRepository studenteRepository) {
        this.studenteRepository = studenteRepository;
    }

    /**
     * POST  /studentes : Create a new studente.
     *
     * @param studente the studente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studente, or with status 400 (Bad Request) if the studente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/studentes")
    @Timed
    public ResponseEntity<Studente> createStudente(@RequestBody Studente studente) throws URISyntaxException {
        log.debug("REST request to save Studente : {}", studente);
        if (studente.getId() != null) {
            throw new BadRequestAlertException("A new studente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Studente result = studenteRepository.save(studente);
        return ResponseEntity.created(new URI("/api/studentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /studentes : Updates an existing studente.
     *
     * @param studente the studente to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studente,
     * or with status 400 (Bad Request) if the studente is not valid,
     * or with status 500 (Internal Server Error) if the studente couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/studentes")
    @Timed
    public ResponseEntity<Studente> updateStudente(@RequestBody Studente studente) throws URISyntaxException {
        log.debug("REST request to update Studente : {}", studente);
        if (studente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Studente result = studenteRepository.save(studente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studente.getId().toString()))
            .body(result);
    }

    /**
     * GET  /studentes : get all the studentes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of studentes in body
     */
    @GetMapping("/studentes")
    @Timed
    public List<Studente> getAllStudentes() {
        log.debug("REST request to get all Studentes");
        return studenteRepository.findAll();
    }

    /**
     * GET  /studentes/:id : get the "id" studente.
     *
     * @param id the id of the studente to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studente, or with status 404 (Not Found)
     */
    @GetMapping("/studentes/{id}")
    @Timed
    public ResponseEntity<Studente> getStudente(@PathVariable Long id) {
        log.debug("REST request to get Studente : {}", id);
        Optional<Studente> studente = studenteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(studente);
    }

    /**
     * DELETE  /studentes/:id : delete the "id" studente.
     *
     * @param id the id of the studente to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/studentes/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudente(@PathVariable Long id) {
        log.debug("REST request to delete Studente : {}", id);

        studenteRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
