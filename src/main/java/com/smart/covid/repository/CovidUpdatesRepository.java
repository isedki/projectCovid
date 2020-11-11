package com.smart.covid.repository;

import com.smart.covid.domain.CovidUpdates;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CovidUpdates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CovidUpdatesRepository extends JpaRepository<CovidUpdates, Long>, JpaSpecificationExecutor<CovidUpdates> {
}
