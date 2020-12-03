package com.bd.xchoice.controller;

import com.bd.xchoice.model.Survey;
import com.bd.xchoice.model.SurveyMetadata;
import com.bd.xchoice.model.SurveyResponse;
import com.bd.xchoice.model.SurveyStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Interface for Survey controller.
 */
public interface SurveyController {

    /**
     * Create a survey by providing data such as questions and choices.
     *
     * @param survey the Survey object
     * @return the posted Survey object
     */
    @PostMapping("/surveys")
    Survey createSurvey(@RequestBody Survey survey);

    /**
     * Public API.
     * Get a survey by survey id.
     *
     * @param id The id of the survey
     * @return the Survey object
     */
    @GetMapping("/surveys/{id}")
    Survey getSurvey(@PathVariable Integer id);

    /**
     * Find surveys published by user.
     *
     * @return the Survey object
     */
    @GetMapping("/surveys")
    List<SurveyMetadata> findSurveys();

    /**
     * Public API.
     * Post responses for a given survey.
     *
     * @param id The survey id
     * @param selections The list of integers. For an integer n of index i, it means selecting (n+1)th choice of (i+i)th question.
     * @return Slug of the response.
     */
    @PostMapping("/surveys/{id}/responses")
    String postSurveyResponse(@PathVariable Integer id, @RequestBody List<Integer> selections);

    /**
     * Public API.
     * Get responses using slug id.
     *
     * @param slug The response slug
     * @return SurveyResponse which contains survey id and selections.
     */
    @GetMapping("/surveys/responses")
    SurveyResponse findSurveyResponse(@RequestParam String slug);

    /**
     * Update survey status to target status.
     * The logic will perform status check to make sure this transition is valid.
     *
     * @param status target status
     */
    @PostMapping("/surveys/{id}")
    void updateSurveyStatus(@PathVariable Integer id, @RequestBody SurveyStatus status);

    /**
     * Delete survey.
     * It will perform a soft delete (by changing the status to Deleted).
     *
     */
    @DeleteMapping("/surveys/{id}")
    void deleteSurvey();
}
