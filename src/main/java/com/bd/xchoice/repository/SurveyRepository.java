package com.bd.xchoice.repository;

import com.bd.xchoice.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for CRUD Survey.
 */
public interface SurveyRepository extends JpaRepository<Survey, Integer> {
}
