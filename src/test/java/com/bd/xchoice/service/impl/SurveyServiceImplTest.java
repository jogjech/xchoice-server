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
import com.bd.xchoice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for SurveyServiceImpl.
 */
class SurveyServiceImplTest {

    private static final Integer SURVEY_ID_1 = 29;
    private static final Integer SURVEY_ID_2 = 34;
    private static final Integer ANOTHER_SURVEY_ID = 31;
    private static final Integer USER_ID = 17;
    private static final Integer RESPONSES_1 = 14;
    private static final Integer RESPONSES_2 = 0;

    private static final String TITLE_1 = "Title 1";
    private static final String TITLE_2 = "Title 2";
    private static final String SLUG_1 = "slug1";
    private static final String SLUG_2 = "slug2";
    private static final String EMAIL = "email";

    @Mock
    private SurveyRepository surveyRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ResponseRepository responseRepository;
    @Mock
    private UserService userService;
    @Mock
    private Survey surveyInput;
    @Mock
    private Survey surveyResponse;
    @Mock
    private User userInput;
    @Mock
    private User userResponse;
    @Mock
    private User publisher;
    @Mock
    private Survey survey1;
    @Mock
    private Survey survey2;
    @Mock
    private Question question1;
    @Mock
    private Question question2;
    @Mock
    private Choice choice1;
    @Mock
    private Choice choice2;
    @Mock
    private Choice choice3;
    @Mock
    private Choice choice4;
    @Mock
    private Response response1;
    @Mock
    private Response response2;

    @InjectMocks
    private SurveyServiceImpl surveyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(surveyRepository.save(surveyInput)).thenReturn(surveyResponse);
        when(surveyRepository.findById(SURVEY_ID_1)).thenReturn(Optional.of(surveyResponse));
        when(userRepository.save(userInput)).thenReturn(userResponse);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(userResponse));
        when(userResponse.getId()).thenReturn(USER_ID);
        final List<Survey> surveyList = new ArrayList<>();
        surveyList.add(survey1);
        surveyList.add(survey2);
        when(userResponse.getSurveys()).thenReturn(surveyList);
        when(survey1.getTitle()).thenReturn(TITLE_1);
        when(survey2.getTitle()).thenReturn(TITLE_2);
        when(survey1.getTotalResponses()).thenReturn(RESPONSES_1);
        when(survey2.getTotalResponses()).thenReturn(RESPONSES_2);
        when(survey1.getId()).thenReturn(SURVEY_ID_1);
        when(survey2.getId()).thenReturn(SURVEY_ID_2);
        final List<Question> questionList = new ArrayList<>();
        questionList.add(question1);
        questionList.add(question2);
        when(surveyResponse.getQuestions()).thenReturn(questionList);
        when(surveyResponse.getStatus()).thenReturn(SurveyStatus.PUBLISHED);
        when(surveyResponse.getPublisher()).thenReturn(publisher);
        when(publisher.getEmail()).thenReturn(EMAIL);
        final List<Choice> choiceList1 = new ArrayList<>();
        choiceList1.add(choice1);
        choiceList1.add(choice2);
        final List<Choice> choiceList2 = new ArrayList<>();
        choiceList2.add(choice3);
        choiceList2.add(choice4);
        when(question1.getChoices()).thenReturn(choiceList1);
        when(question2.getChoices()).thenReturn(choiceList2);
        final List<Response> responseList = new ArrayList<>();
        responseList.add(response1);
        responseList.add(response2);
        when(responseRepository.findBySlug(SLUG_1)).thenReturn(responseList);
        when(responseRepository.findBySlug(SLUG_2)).thenReturn(new ArrayList<>());
        when(response1.getChoice()).thenReturn(choice1);
        when(response2.getChoice()).thenReturn(choice4);
        when(choice1.getQuestion()).thenReturn(question1);
        when(choice4.getQuestion()).thenReturn(question2);
        when(question1.getSurvey()).thenReturn(survey1);
        when(question2.getSurvey()).thenReturn(survey1);
    }

    @Test
    void createSurvey_happyPath() {
        final Survey response = surveyService.createSurvey(surveyInput, userResponse);

        assertEquals(surveyResponse, response);
        verify(surveyRepository).save(surveyInput);
        verify(userRepository).save(userResponse);
    }

    @Test
    void createSurvey_nullInputs() {
        assertThrows(NullPointerException.class, () -> surveyService.createSurvey(null, userResponse));
        assertThrows(NullPointerException.class, () -> surveyService.createSurvey(surveyInput, null));
    }

    @Test
    void getSurvey_externalUserGetPublishedSurvey() {
        final Survey response = surveyService.getSurvey(SURVEY_ID_1, null);

        assertEquals(surveyResponse, response);
    }

    @Test
    void getSurvey_externalUserGetUnpublishedSurvey() {
        when(surveyResponse.getStatus()).thenReturn(SurveyStatus.UNPUBLISHED);

        assertThrows(NoSuchElementException.class, () -> surveyService.getSurvey(SURVEY_ID_1, null));
    }

    @Test
    void getSurvey_externalUserGetDeletedSurvey() {
        when(surveyResponse.getStatus()).thenReturn(SurveyStatus.DELETED);

        assertThrows(NoSuchElementException.class, () -> surveyService.getSurvey(SURVEY_ID_1, null));
    }

    @Test
    void getSurvey_externalUserGetDraftSurvey() {
        when(surveyResponse.getStatus()).thenReturn(SurveyStatus.DRAFT);

        assertThrows(NoSuchElementException.class, () -> surveyService.getSurvey(SURVEY_ID_1, null));
    }

    @Test
    void getSurvey_publisherGetPublishedSurvey() {
        final Survey response = surveyService.getSurvey(SURVEY_ID_1, EMAIL);

        assertEquals(surveyResponse, response);
    }

    @Test
    void getSurvey_publisherGetUnpublishedSurvey() {
        when(surveyResponse.getStatus()).thenReturn(SurveyStatus.UNPUBLISHED);

        final Survey response = surveyService.getSurvey(SURVEY_ID_1, EMAIL);

        assertEquals(surveyResponse, response);
    }

    @Test
    void getSurvey_publisherGetDeletedSurvey() {
        when(surveyResponse.getStatus()).thenReturn(SurveyStatus.DELETED);

        assertThrows(NoSuchElementException.class, () -> surveyService.getSurvey(SURVEY_ID_1, EMAIL));
    }

    @Test
    void getSurvey_publisherGetDraftSurvey() {
        when(surveyResponse.getStatus()).thenReturn(SurveyStatus.DRAFT);

        final Survey response = surveyService.getSurvey(SURVEY_ID_1, EMAIL);

        assertEquals(surveyResponse, response);
    }

    @Test
    void getSurvey_surveyNotExist() {
        assertThrows(NoSuchElementException.class, () -> surveyService.getSurvey(ANOTHER_SURVEY_ID, EMAIL));
        assertThrows(NoSuchElementException.class, () -> surveyService.getSurvey(ANOTHER_SURVEY_ID, null));
    }

    @Test
    void findSurveys_happyPath() {
        final List<SurveyMetadata> surveyMetadataList = surveyService.findSurveys(userResponse);

        assertEquals(2, surveyMetadataList.size());
        assertEquals(TITLE_1, surveyMetadataList.get(0).getTitle());
        assertEquals(TITLE_2, surveyMetadataList.get(1).getTitle());
        assertEquals(SURVEY_ID_1, surveyMetadataList.get(0).getSurveyId());
        assertEquals(SURVEY_ID_2, surveyMetadataList.get(1).getSurveyId());
        assertEquals(RESPONSES_1, surveyMetadataList.get(0).getResponses());
        assertEquals(RESPONSES_2, surveyMetadataList.get(1).getResponses());
        assertTrue(surveyMetadataList.get(0).isPublished());
        assertTrue(surveyMetadataList.get(1).isPublished());
    }

    @Test
    void findSurveys_nullUserId() {
        assertThrows(NullPointerException.class, () -> surveyService.findSurveys(null));
    }

    @Test
    void postSurveyResponse_happyPath() {
        final String slug = surveyService.postSurveyResponse(SURVEY_ID_1, Arrays.asList(0, 1));

        assertNotNull(slug);
        verify(responseRepository, times(2)).save(any());
    }

    @Test
    void postSurveyResponse_nullInputs() {
        assertThrows(NullPointerException.class, () -> surveyService.postSurveyResponse(SURVEY_ID_1, null));
    }

    @Test
    void postSurveyResponse_surveyNotExist() {
        assertThrows(NoSuchElementException.class, () -> surveyService.postSurveyResponse(SURVEY_ID_2, Arrays.asList(0, 1)));
    }

    @Test
    void findSurveyResponse_happyPath() {
        final SurveyResponse surveyResponse = surveyService.findSurveyResponse(SLUG_1);

        assertEquals(SURVEY_ID_1, surveyResponse.getSurveyId());
        assertEquals(Arrays.asList(0, 1), surveyResponse.getSelections());
    }

    @Test
    void findSurveyResponse_nullSlug() {
        assertThrows(NullPointerException.class, () -> surveyService.findSurveyResponse(null));
    }

    @Test
    void findSurveyResponse_responseNotExist() {
        assertThrows(NoSuchElementException.class, () -> surveyService.findSurveyResponse(SLUG_2));
    }
}
