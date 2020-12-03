package com.bd.xchoice.repository;

import com.bd.xchoice.model.Survey;
import com.bd.xchoice.model.SurveyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * Interface for CRUD Survey.
 */
public interface SurveyRepository extends JpaRepository<Survey, Integer> {

    @Modifying
    @Transactional
    @Query("update Survey s set s.status = ?2 where s.id = ?1")
    int setStatusForSurvey(int id, SurveyStatus status);
}
