package com.bd.xchoice.controller.impl;

import com.bd.xchoice.controller.SurveyController;
import com.bd.xchoice.model.Survey;
import com.bd.xchoice.model.SurveyMetadata;
import com.bd.xchoice.repository.SurveyRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implementation of SurveyController.
 */
@RestController
public class SurveyControllerImpl implements SurveyController {

    @Autowired
    private SurveyRepository surveyRepository;

    @Override
    public Survey createSurvey(@NonNull final Survey survey) {
        return surveyRepository.save(survey);
    }

    @Override
    public Survey getSurvey(final String id) {
        return null;
    }

    @Override
    public List<SurveyMetadata> findSurveys(final String userId) {
        return null;
    }

    @Override
    public void postResponse(final String id, final List<Integer> selections) {
    }
}
