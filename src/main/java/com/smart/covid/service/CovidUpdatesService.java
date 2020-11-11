package com.smart.covid.service;

import com.smart.covid.domain.CovidUpdates;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CovidUpdates}.
 */
public interface CovidUpdatesService {

    /**
     * Save a covidUpdates.
     *
     * @param covidUpdates the entity to save.
     * @return the persisted entity.
     */
    CovidUpdates save(CovidUpdates covidUpdates);

    /**
     * Get all the covidUpdates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CovidUpdates> findAll(Pageable pageable);


    /**
     * Get the "id" covidUpdates.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CovidUpdates> findOne(Long id);

    /**
     * Delete the "id" covidUpdates.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
