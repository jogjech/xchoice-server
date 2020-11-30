package com.bd.xchoice.controller.impl;

import com.bd.xchoice.model.Survey;
import com.bd.xchoice.model.User;
import com.bd.xchoice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for UserControllerImpl.
 */
class UserControllerImplTest {

    private static final Integer USER_ID = 17;
    private static final Integer ANOTHER_USER_ID = 33;

    @Mock
    private UserRepository userRepository;
    @Mock
    private User userInput;
    @Mock
    private User userResponse;
    @Mock
    private Survey survey1;
    @Mock
    private Survey survey2;

    @InjectMocks
    private UserControllerImpl userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(userRepository.save(userInput)).thenReturn(userResponse);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(userResponse));
        when(userResponse.getSurveys()).thenReturn(Arrays.asList(survey1, survey2));
        when(userResponse.getId()).thenReturn(USER_ID);
    }

    @Test
    void createUser_happyPath() {
        final User response = userController.createUser(userInput);

        assertEquals(userResponse, response);
    }

    @Test
    void createUser_nullUserInput() {
        assertThrows(NullPointerException.class, () -> userController.createUser(null));
    }

    @Test
    void getUser_happyPath() {
        final User response = userController.getUser(USER_ID);

        assertEquals(userResponse, response);
        verify(survey1).setPublisherId(USER_ID);
        verify(survey2).setPublisherId(USER_ID);
    }

    @Test
    void getUser_nullUserId() {
        assertThrows(NullPointerException.class, () -> userController.getUser(null));
    }

    @Test
    void getUser_userNotExist() {
        assertThrows(NoSuchElementException.class, () -> userController.getUser(ANOTHER_USER_ID));
    }
}
