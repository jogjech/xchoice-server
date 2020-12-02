package com.bd.xchoice.controller.impl;

import com.bd.xchoice.service.SurveyService;
import com.bd.xchoice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Test class for SurveyControllerImpl.
 */
class SurveyControllerImplTest {

    @Mock
    private UserService userService;
    @Mock
    private SurveyService surveyService;

    @InjectMocks
    private SurveyControllerImpl surveyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}
