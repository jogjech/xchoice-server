package com.bd.xchoice.controller.impl;

import com.bd.xchoice.controller.SurveyController;
import com.bd.xchoice.model.Choice;
import com.bd.xchoice.model.Question;
import com.bd.xchoice.model.Response;
import com.bd.xchoice.model.Survey;
import com.bd.xchoice.model.SurveyMetadata;
import com.bd.xchoice.model.SurveyResponse;
import com.bd.xchoice.model.User;
import com.bd.xchoice.repository.ResponseRepository;
import com.bd.xchoice.repository.SurveyRepository;
import com.bd.xchoice.repository.UserRepository;
import com.bd.xchoice.security.JwtUtil;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
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

    @Autowired
    private ResponseRepository responseRepository;

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
        System.out.println(JwtUtil.getUserEmail());
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
    public String postSurveyResponse(@NonNull final Integer id, @NonNull final List<Integer> selections) {
        final Survey survey = surveyRepository.findById(id).orElseThrow(NoSuchElementException::new);
        final String slug = UUID.randomUUID().toString();

        for (int i = 0; i < selections.size(); i++) {
            final Question question = survey.getQuestions().get(i);
            final Choice selectedChoice = question.getChoices().get(selections.get(i));
            final Response response = Response.builder()
                    .choice(selectedChoice)
                    .slug(slug)
                    .build();
            responseRepository.save(response);
            selectedChoice.getResponses().add(response);
        }
        return slug;
    }

    @Override
    public SurveyResponse findSurveyResponse(@NonNull final String slug) {
        final List<Response> responses = responseRepository.findBySlug(slug);
        if (responses.isEmpty()) throw new NoSuchElementException("Cannot find any response for slug " + slug);

        final List<Choice> selectedChoices = responses.stream()
                .map(Response::getChoice)
                .collect(Collectors.toList());

        final List<Question> questions = selectedChoices.stream()
                .map(Choice::getQuestion)
                .collect(Collectors.toList());

        final List<Integer> selections = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            final Question question = questions.get(i);
            final Choice selectedChoice = selectedChoices.get(i);
            int selectedIndex = question.getChoices().indexOf(selectedChoice);
            selections.add(selectedIndex);
        }
        return SurveyResponse.builder()
                .selections(selections)
                .surveyId(questions.get(0).getSurvey().getId())
                .build();
    }
}
