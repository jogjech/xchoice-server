package com.bd.xchoice.controller.impl;

import com.bd.xchoice.model.Survey;
import com.bd.xchoice.model.SurveyMetadata;
import com.bd.xchoice.model.User;
import com.bd.xchoice.repository.SurveyRepository;
import com.bd.xchoice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for SurveyControllerImpl.
 */
class SurveyControllerImplTest {

    private static final Integer SURVEY_ID_1 = 29;
    private static final Integer SURVEY_ID_2 = 34;
    private static final Integer ANOTHER_SURVEY_ID = 31;
    private static final Integer USER_ID = 17;
    private static final Integer ANOTHER_USER_ID = 33;
    private static final Integer RESPONSES_1 = 14;
    private static final Integer RESPONSES_2 = 0;

    private static final String TITLE_1 = "Title 1";
    private static final String TITLE_2 = "Title 2";

    @Mock
    private SurveyRepository surveyRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Survey surveyInput;
    @Mock
    private Survey surveyResponse;
    @Mock
    private User userInput;
    @Mock
    private User userResponse;
    @Mock
    private Survey survey1;
    @Mock
    private Survey survey2;

    @InjectMocks
    private SurveyControllerImpl surveyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(surveyRepository.save(surveyInput)).thenReturn(surveyResponse);
        when(surveyRepository.findById(SURVEY_ID_1)).thenReturn(Optional.of(surveyResponse));
        when(surveyInput.getPublisherId()).thenReturn(USER_ID);
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
    }

    @Test
    void createSurvey_happyPath() {
        final Survey response = surveyController.createSurvey(surveyInput);

        assertEquals(surveyResponse, response);
        verify(surveyRepository).save(surveyInput);
        verify(userRepository).save(userResponse);
    }

    @Test
    void createSurvey_nullSurveyInput() {
        assertThrows(NullPointerException.class, () -> surveyController.createSurvey(null));
    }

    @Test
    void createSurvey_publisherNotExist() {
        when(surveyInput.getPublisherId()).thenReturn(ANOTHER_USER_ID);

        assertThrows(NoSuchElementException.class, () -> surveyController.createSurvey(surveyInput));
    }

    @Test
    void getSurvey_happyPath() {
        final Survey response = surveyController.getSurvey(SURVEY_ID_1);

        assertEquals(surveyResponse, response);
    }

    @Test
    void getSurvey_nullSurveyId() {
        assertThrows(NullPointerException.class, () -> surveyController.getSurvey(null));
    }

    @Test
    void getSurvey_userNotExist() {
        assertThrows(NoSuchElementException.class, () -> surveyController.getSurvey(ANOTHER_SURVEY_ID));
    }

    @Test
    void findSurveys_happyPath() {
        final List<SurveyMetadata> surveyMetadataList = surveyController.findSurveys(USER_ID);

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

    }

    @Test
    void findSurveys_userNotExist() {

    }
}