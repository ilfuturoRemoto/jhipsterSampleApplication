package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Studente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Studente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudenteRepository extends JpaRepository<Studente, Long> {

}
