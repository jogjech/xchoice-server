package com.bd.xchoice.controller;

import com.bd.xchoice.model.Survey;
import com.bd.xchoice.model.SurveyMetadata;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
     * Get a survey by survey id.
     *
     * @param id The id of the survey
     * @return the Survey object
     */
    @GetMapping("/surveys/{id}")
    Survey getSurvey(@PathVariable String id);

    /**
     * Find surveys published by user.
     *
     * @param userId The id of the survey
     * @return the Survey object
     */
    @GetMapping("/surveys")
    List<SurveyMetadata> findSurveys(@RequestParam String userId);

    /**
     * Post responses for a given survey.
     *
     * @param id The survey id
     * @param selections The list of integers. For an integer n of index i, it means selecting (n+1)th choice of (i+i)th question.
     */
    @PostMapping("/surveys/{id}/response")
    void postResponse(@PathVariable String id, @RequestBody List<Integer> selections);
}
