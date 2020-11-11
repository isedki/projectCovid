package com.smart.covid.repository;

import com.smart.covid.domain.Parents;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Parents entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParentsRepository extends JpaRepository<Parents, Long> {
}
