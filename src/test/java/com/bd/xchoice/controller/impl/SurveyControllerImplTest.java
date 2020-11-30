package com.bd.xchoice.controller.impl;

import com.bd.xchoice.model.Survey;
import com.bd.xchoice.repository.SurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Test class for SurveyControllerImpl.
 */
class SurveyControllerImplTest {

    @Mock
    private SurveyRepository surveyRepository;
    @Mock
    private Survey survey;
    @Mock
    private Survey surveyResponse;

    @InjectMocks
    private SurveyControllerImpl surveyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(surveyRepository.save(any(Survey.class))).thenReturn(surveyResponse);
    }

    @Test
    void createSurvey_happyPath() {
        final Survey response = surveyController.createSurvey(survey);

        assertEquals(surveyResponse, response);
    }

    @Test
    void createSurvey_nullSurveyInput() {
        assertThrows(NullPointerException.class, ()-> surveyController.createSurvey(null));
    }
}
