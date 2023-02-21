package com.api.bookratings.repositories;

import com.api.bookratings.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RateRepository extends JpaRepository <Rate, Long> {}
