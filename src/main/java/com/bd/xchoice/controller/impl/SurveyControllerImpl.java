package com.bd.xchoice.controller.impl;

import com.bd.xchoice.controller.SurveyController;
import com.bd.xchoice.model.Survey;
import com.bd.xchoice.model.SurveyMetadata;
import com.bd.xchoice.model.SurveyResponse;
import com.bd.xchoice.model.SurveyStatus;
import com.bd.xchoice.model.User;
import com.bd.xchoice.security.JwtUtil;
import com.bd.xchoice.service.SurveyService;
import com.bd.xchoice.service.UserService;
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
    private UserService userService;

    @Autowired
    private SurveyService surveyService;

    @Override
    public Survey createSurvey(@NonNull final Survey survey) {
        final User publisher = userService.getUser(JwtUtil.getUserEmail());
        return surveyService.createSurvey(survey, publisher);
    }

    @Override
    public Survey getSurvey(@NonNull final Integer id) {
        return surveyService.getSurvey(id, JwtUtil.getUserEmail());
    }

    @Override
    public List<SurveyMetadata> findSurveys() {
        final User user = userService.getUser(JwtUtil.getUserEmail());
        return surveyService.findSurveys(user);
    }

    @Override
    public String postSurveyResponse(@NonNull final Integer id, @NonNull final List<Integer> selections) {
        return surveyService.postSurveyResponse(id, selections);
    }

    @Override
    public SurveyResponse findSurveyResponse(@NonNull final String slug) {
        return surveyService.findSurveyResponse(slug);
    }

    @Override
    public void updateSurveyStatus(@NonNull Integer id, @NonNull final SurveyStatus status) {
        final Survey survey = surveyService.getSurvey(id, JwtUtil.getUserEmail());
        surveyService.updateSurveyStatus(id, survey.getStatus(), status);
    }

    @Override
    public void deleteSurvey() {

    }
}
