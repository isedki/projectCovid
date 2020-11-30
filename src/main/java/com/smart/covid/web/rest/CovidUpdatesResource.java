package com.smart.covid.web.rest;

import com.smart.covid.domain.CovidUpdates;
import com.smart.covid.domain.NewsApiItem;
import com.smart.covid.domain.NewsG;
import com.smart.covid.service.CovidUpdatesService;
import com.smart.covid.web.rest.errors.BadRequestAlertException;
import com.smart.covid.service.dto.CovidUpdatesCriteria;
import com.smart.covid.service.CovidUpdatesQueryService;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.smart.covid.domain.CovidUpdates}.
 */
@RestController
@RequestMapping("/api")
public class CovidUpdatesResource {

    private final Logger log = LoggerFactory.getLogger(CovidUpdatesResource.class);

    private static final String ENTITY_NAME = "covidUpdates";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CovidUpdatesService covidUpdatesService;

    private final CovidUpdatesQueryService covidUpdatesQueryService;

    public CovidUpdatesResource(CovidUpdatesService covidUpdatesService, CovidUpdatesQueryService covidUpdatesQueryService) {
        this.covidUpdatesService = covidUpdatesService;
        this.covidUpdatesQueryService = covidUpdatesQueryService;
    }

    /**
     * {@code POST  /covid-updates} : Create a new covidUpdates.
     *
     * @param covidUpdates the covidUpdates to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new covidUpdates, or with status {@code 400 (Bad Request)} if the covidUpdates has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/covid-updates")
    public ResponseEntity<List<CovidUpdates>> createCovidUpdates(@RequestBody CovidUpdates covidUpdates) throws URISyntaxException {
        log.debug("REST request to save CovidUpdates : {}", covidUpdates);
        if (covidUpdates.getId() != null) {
            throw new BadRequestAlertException("A new covidUpdates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
          = "http://newsapi.org/v2/top-headlines?q=covidANDquebec&country=ca&language=en&sortBy=publishedAt&apiKey=360d2f7f354f412f915d7b606e9f1b63";
        
        NewsG response
          = restTemplate.getForObject(fooResourceUrl  , NewsG.class);
        
        System.out.println(response);
        
        
        List<NewsApiItem> items= (List<NewsApiItem>)response.getArticles();
        Iterator iter = items.iterator();
        while(iter.hasNext()) {
        CovidUpdates c1= new CovidUpdates();
        NewsApiItem it =(NewsApiItem) iter.next();
        c1.setTitle(it.getTitle());
        c1.setContent(it.getDescription());
        c1.setSource(it.getSource().getName());
        c1.setDomain(it.getAuthor());
        c1.setPublishedAt(it.getPublishedAt());
        c1.setImage(it.getUrlToImage());
        covidUpdatesService.save(c1);
        }
       
        Page<CovidUpdates> page = covidUpdatesQueryService.findByCriteria(null, null);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code PUT  /covid-updates} : Updates an existing covidUpdates.
     *
     * @param covidUpdates the covidUpdates to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated covidUpdates,
     * or with status {@code 400 (Bad Request)} if the covidUpdates is not valid,
     * or with status {@code 500 (Internal Server Error)} if the covidUpdates couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/covid-updates")
    public ResponseEntity<CovidUpdates> updateCovidUpdates(@RequestBody CovidUpdates covidUpdates) throws URISyntaxException {
        log.debug("REST request to update CovidUpdates : {}", covidUpdates);
        if (covidUpdates.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CovidUpdates result = covidUpdatesService.save(covidUpdates);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, covidUpdates.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /covid-updates} : get all the covidUpdates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of covidUpdates in body.
     */
    @GetMapping("/covid-updates2")
    public ResponseEntity<List<CovidUpdates>> getAllCovidUpdates2(CovidUpdatesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CovidUpdates by criteria: {}", criteria);
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
          = "http://newsapi.org/v2/top-headlines?q=covidANDquebec&country=ca&language=en&sortBy=publishedAt&apiKey=360d2f7f354f412f915d7b606e9f1b63";
        
        NewsG response
          = restTemplate.getForObject(fooResourceUrl  , NewsG.class);
        
        System.out.println(response);
        
        
        List<NewsApiItem> items= (List<NewsApiItem>)response.getArticles();
        Iterator iter = items.iterator();
        while(iter.hasNext()) {
        CovidUpdates c1= new CovidUpdates();
        NewsApiItem it =(NewsApiItem) iter.next();
        c1.setTitle(it.getTitle());
        c1.setContent(it.getDescription());
        c1.setSource(it.getSource().getName());
        c1.setDomain(it.getAuthor());
        c1.setPublishedAt(it.getPublishedAt());
        c1.setImage(it.getUrlToImage());
        covidUpdatesService.save(c1);
        }
        Page<CovidUpdates> page = covidUpdatesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    /**
     * {@code GET  /covid-updates} : get all the covidUpdates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of covidUpdates in body.
     */
    @GetMapping("/covid-updates")
    public ResponseEntity<List<CovidUpdates>> getAllCovidUpdates(CovidUpdatesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CovidUpdates by criteria: {}", criteria);
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
          = "http://newsapi.org/v2/top-headlines?q=covidANDQuebecANDschool&country=ca&language=en&sortBy=publishedAt&apiKey=360d2f7f354f412f915d7b606e9f1b63";
        
        NewsG response
          = restTemplate.getForObject(fooResourceUrl  , NewsG.class);
        
        System.out.println(response);
        
        
        List<NewsApiItem> items= (List<NewsApiItem>)response.getArticles();
        Iterator iter = items.iterator();
        while(iter.hasNext()) {
        CovidUpdates c1= new CovidUpdates();
        NewsApiItem it =(NewsApiItem) iter.next();
        c1.setTitle(it.getTitle());
        c1.setContent(it.getContent());
        c1.setSource(it.getSource().getName());
        c1.setDomain(it.getAuthor());
        c1.setPublishedAt(it.getPublishedAt());
        c1.setImage(it.getUrlToImage());
        covidUpdatesService.save(c1);
        }
        Page<CovidUpdates> page = covidUpdatesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /covid-updates/count} : count all the covidUpdates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/covid-updates/count")
    public ResponseEntity<Long> countCovidUpdates(CovidUpdatesCriteria criteria) {
        log.debug("REST request to count CovidUpdates by criteria: {}", criteria);
        return ResponseEntity.ok().body(covidUpdatesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /covid-updates/:id} : get the "id" covidUpdates.
     *
     * @param id the id of the covidUpdates to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the covidUpdates, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/covid-updates/{id}")
    public ResponseEntity<CovidUpdates> getCovidUpdates(@PathVariable Long id) {
        log.debug("REST request to get CovidUpdates : {}", id);
        Optional<CovidUpdates> covidUpdates = covidUpdatesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(covidUpdates);
    }

    /**
     * {@code DELETE  /covid-updates/:id} : delete the "id" covidUpdates.
     *
     * @param id the id of the covidUpdates to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/covid-updates/{id}")
    public ResponseEntity<Void> deleteCovidUpdates(@PathVariable Long id) {
        log.debug("REST request to delete CovidUpdates : {}", id);
        covidUpdatesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
