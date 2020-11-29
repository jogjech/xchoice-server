package com.bd.xchoice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SurveyController {

    /**
     * Get a survey by survey id.
     *
     * @param id The id of the survey
     * @return the Survey object
     */
    @GetMapping("/surveys/{id}")
    String getSurvey(@PathVariable String id) {
        return "1";
    }
}
