package com.bd.xchoice.service.impl;

import com.bd.xchoice.model.Choice;
import com.bd.xchoice.model.Question;
import com.bd.xchoice.model.Response;
import com.bd.xchoice.model.Survey;
import com.bd.xchoice.model.SurveyMetadata;
import com.bd.xchoice.model.SurveyResponse;
import com.bd.xchoice.model.SurveyStatus;
import com.bd.xchoice.model.User;
import com.bd.xchoice.repository.ResponseRepository;
import com.bd.xchoice.repository.SurveyRepository;
import com.bd.xchoice.repository.UserRepository;
import com.bd.xchoice.security.JwtUtil;
import com.bd.xchoice.service.SurveyService;
import com.bd.xchoice.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private UserService userService;

    @Override
    public Survey createSurvey(@NonNull final Survey survey, @NonNull final User user) {
        survey.attachReferenceToChild();
        survey.setPublisher(user);
        survey.setStatus(SurveyStatus.PUBLISHED);
        final Survey result = surveyRepository.save(survey);
        if (user.getSurveys() == null) {
            user.setSurveys(new ArrayList<>());
        }
        user.getSurveys().add(result);
        userRepository.save(user);
        return result;
    }

    @Override
    public Survey getSurvey(final int id, final String email) {
        final Survey survey = surveyRepository.findById(id).orElseThrow(NoSuchElementException::new);

        if (SurveyStatus.PUBLISHED.equals(survey.getStatus())) {
            return survey;
        }

        if (SurveyStatus.DELETED.equals(survey.getStatus())) {
            throw new NoSuchElementException("This survey has been deleted.");
        }

        if (email != null) {
            if (survey.getPublisher().getEmail().equalsIgnoreCase(email)) {
                return survey;
            }
        }
        throw new NoSuchElementException("Cannot find survey");
    }

    @Override
    public List<SurveyMetadata> findSurveys(@NonNull final User user) {
        return user.getSurveys().stream()
                .map(survey -> SurveyMetadata.builder()
                        .published(true)
                        .title(survey.getTitle())
                        .responses(survey.getTotalResponses())
                        .surveyId(survey.getId())
                        .status(survey.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public String postSurveyResponse(int id, @NonNull List<Integer> selections) {
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
    public SurveyResponse findSurveyResponse(@NonNull String slug) {
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

    @Override
    public void updateSurveyStatus(int id, @NonNull final SurveyStatus initialStatus, @NonNull final SurveyStatus targetStatus) {
        if (initialStatus.equals(targetStatus)) {
            return;
        }
        if (invalidStatusTransition(initialStatus, targetStatus)) {
            throw new IllegalArgumentException(String.format("Illegal status transition from %s to %s", initialStatus, targetStatus));
        }
        surveyRepository.setStatusForSurvey(id, targetStatus);
    }

    private boolean invalidStatusTransition(final SurveyStatus initialStatus, final SurveyStatus targetStatus) {
        return SurveyStatus.DRAFT.equals(targetStatus)
                || SurveyStatus.DELETED.equals(initialStatus)
                || (SurveyStatus.DRAFT.equals(initialStatus) && SurveyStatus.UNPUBLISHED.equals(targetStatus))
                || (SurveyStatus.PUBLISHED.equals(initialStatus) && SurveyStatus.DELETED.equals(targetStatus));
    }
}
