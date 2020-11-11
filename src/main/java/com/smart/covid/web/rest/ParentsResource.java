package com.smart.covid.web.rest;

import com.smart.covid.domain.Parents;
import com.smart.covid.repository.ParentsRepository;
import com.smart.covid.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.smart.covid.domain.Parents}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ParentsResource {

    private final Logger log = LoggerFactory.getLogger(ParentsResource.class);

    private static final String ENTITY_NAME = "parents";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParentsRepository parentsRepository;

    public ParentsResource(ParentsRepository parentsRepository) {
        this.parentsRepository = parentsRepository;
    }

    /**
     * {@code POST  /parents} : Create a new parents.
     *
     * @param parents the parents to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parents, or with status {@code 400 (Bad Request)} if the parents has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parents")
    public ResponseEntity<Parents> createParents(@RequestBody Parents parents) throws URISyntaxException {
        log.debug("REST request to save Parents : {}", parents);
        if (parents.getId() != null) {
            throw new BadRequestAlertException("A new parents cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Parents result = parentsRepository.save(parents);
        return ResponseEntity.created(new URI("/api/parents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parents} : Updates an existing parents.
     *
     * @param parents the parents to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parents,
     * or with status {@code 400 (Bad Request)} if the parents is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parents couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parents")
    public ResponseEntity<Parents> updateParents(@RequestBody Parents parents) throws URISyntaxException {
        log.debug("REST request to update Parents : {}", parents);
        if (parents.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Parents result = parentsRepository.save(parents);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, parents.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /parents} : get all the parents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parents in body.
     */
    @GetMapping("/parents")
    public ResponseEntity<List<Parents>> getAllParents(Pageable pageable) {
        log.debug("REST request to get a page of Parents");
        Page<Parents> page = parentsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /parents/:id} : get the "id" parents.
     *
     * @param id the id of the parents to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parents, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parents/{id}")
    public ResponseEntity<Parents> getParents(@PathVariable Long id) {
        log.debug("REST request to get Parents : {}", id);
        Optional<Parents> parents = parentsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(parents);
    }

    /**
     * {@code DELETE  /parents/:id} : delete the "id" parents.
     *
     * @param id the id of the parents to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parents/{id}")
    public ResponseEntity<Void> deleteParents(@PathVariable Long id) {
        log.debug("REST request to delete Parents : {}", id);
        parentsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
