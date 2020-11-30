package com.bd.xchoice.controller.impl;

import com.bd.xchoice.controller.SurveyController;
import com.bd.xchoice.model.Survey;
import com.bd.xchoice.model.SurveyMetadata;
import com.bd.xchoice.model.SurveyResponse;
import com.bd.xchoice.model.User;
import com.bd.xchoice.repository.SurveyRepository;
import com.bd.xchoice.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Implementation of SurveyController.
 */
@RestController
public class SurveyControllerImpl implements SurveyController {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public Survey createSurvey(@NonNull final Survey survey) {
        final User publisher = userRepository.findById(survey.getPublisherId())
                .orElseThrow(() -> new NoSuchElementException("Cannot find publish with user id " + survey.getPublisher()));
        survey.attachReferenceToChild();
        survey.setPublisher(publisher);
        final Survey result = surveyRepository.save(survey);
        if (publisher.getSurveys() == null) {
            publisher.setSurveys(new ArrayList<>());
        }
        publisher.getSurveys().add(result);
        userRepository.save(publisher);
        return result;
    }

    @Override
    public Survey getSurvey(@NonNull final Integer id) {
        return surveyRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<SurveyMetadata> findSurveys(@NonNull final Integer userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Cannot find user with id " + userId));

        return user.getSurveys().stream()
                .map(survey -> SurveyMetadata.builder()
                        .published(true)
                        .title(survey.getTitle())
                        .responses(survey.getTotalResponses())
                        .surveyId(survey.getId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public String postSurveyResponse(final String id, final List<Integer> selections) {
        return null;
    }

    @Override
    public SurveyResponse findSurveyResponse(final String slug) {
        return null;
    }
}
