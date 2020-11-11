package com.smart.covid.service.impl;

import com.smart.covid.service.CovidUpdatesService;
import com.smart.covid.domain.CovidUpdates;
import com.smart.covid.repository.CovidUpdatesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CovidUpdates}.
 */
@Service
@Transactional
public class CovidUpdatesServiceImpl implements CovidUpdatesService {

    private final Logger log = LoggerFactory.getLogger(CovidUpdatesServiceImpl.class);

    private final CovidUpdatesRepository covidUpdatesRepository;

    public CovidUpdatesServiceImpl(CovidUpdatesRepository covidUpdatesRepository) {
        this.covidUpdatesRepository = covidUpdatesRepository;
    }

    @Override
    public CovidUpdates save(CovidUpdates covidUpdates) {
        log.debug("Request to save CovidUpdates : {}", covidUpdates);
        return covidUpdatesRepository.save(covidUpdates);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CovidUpdates> findAll(Pageable pageable) {
        log.debug("Request to get all CovidUpdates");
        return covidUpdatesRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CovidUpdates> findOne(Long id) {
        log.debug("Request to get CovidUpdates : {}", id);
        return covidUpdatesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CovidUpdates : {}", id);
        covidUpdatesRepository.deleteById(id);
    }
}
