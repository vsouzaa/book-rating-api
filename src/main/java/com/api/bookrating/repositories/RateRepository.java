package com.api.bookrating.repositories;

import com.api.bookrating.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RateRepository extends JpaRepository <Rate, Long> {}
