package com.smart.covid.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.smart.covid.domain.CovidUpdates;
import com.smart.covid.domain.*; // for static metamodels
import com.smart.covid.repository.CovidUpdatesRepository;
import com.smart.covid.service.dto.CovidUpdatesCriteria;

/**
 * Service for executing complex queries for {@link CovidUpdates} entities in the database.
 * The main input is a {@link CovidUpdatesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CovidUpdates} or a {@link Page} of {@link CovidUpdates} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CovidUpdatesQueryService extends QueryService<CovidUpdates> {

    private final Logger log = LoggerFactory.getLogger(CovidUpdatesQueryService.class);

    private final CovidUpdatesRepository covidUpdatesRepository;

    public CovidUpdatesQueryService(CovidUpdatesRepository covidUpdatesRepository) {
        this.covidUpdatesRepository = covidUpdatesRepository;
    }

    /**
     * Return a {@link List} of {@link CovidUpdates} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CovidUpdates> findByCriteria(CovidUpdatesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CovidUpdates> specification = createSpecification(criteria);
        return covidUpdatesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CovidUpdates} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CovidUpdates> findByCriteria(CovidUpdatesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CovidUpdates> specification = createSpecification(criteria);
        return covidUpdatesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CovidUpdatesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CovidUpdates> specification = createSpecification(criteria);
        return covidUpdatesRepository.count(specification);
    }

    /**
     * Function to convert {@link CovidUpdatesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CovidUpdates> createSpecification(CovidUpdatesCriteria criteria) {
        Specification<CovidUpdates> specification = Specification.where(null);
		/*
		 * if (criteria != null) { if (criteria.getId() != null) { specification =
		 * specification.and(buildRangeSpecification(criteria.getId(),
		 * CovidUpdates_.id)); } if (criteria.getTitle() != null) { specification =
		 * specification.and(buildStringSpecification(criteria.getTitle(),
		 * CovidUpdates_.title)); } if (criteria.getContent() != null) { specification =
		 * specification.and(buildStringSpecification(criteria.getContent(),
		 * CovidUpdates_.content)); } if (criteria.getSource() != null) { specification
		 * = specification.and(buildStringSpecification(criteria.getSource(),
		 * CovidUpdates_.source)); } if (criteria.getDomain() != null) { specification =
		 * specification.and(buildStringSpecification(criteria.getDomain(),
		 * CovidUpdates_.domain)); } if (criteria.getImage() != null) { specification =
		 * specification.and(buildStringSpecification(criteria.getImage(),
		 * CovidUpdates_.image)); } if (criteria.getPublishedAt() != null) {
		 * specification =
		 * specification.and(buildStringSpecification(criteria.getPublishedAt(),
		 * CovidUpdates_.publishedAt)); } }
		 */
        return specification;
    }
}
