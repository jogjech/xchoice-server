package com.bd.xchoice.service;

import com.bd.xchoice.model.Survey;
import com.bd.xchoice.model.SurveyMetadata;
import com.bd.xchoice.model.SurveyResponse;
import com.bd.xchoice.model.User;

import java.util.List;

public interface SurveyService {

    Survey createSurvey(Survey survey, User user);

    Survey getSurvey(int id);

    List<SurveyMetadata> findSurveys(User user);

    String postSurveyResponse(int id, List<Integer> selections);

    SurveyResponse findSurveyResponse(String slug);
}
