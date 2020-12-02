package com.bd.xchoice.service.impl;

import com.bd.xchoice.model.Survey;
import com.bd.xchoice.model.User;
import com.bd.xchoice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for UserServiceImpl.
 */
class UserServiceImplTest {

    private static final Integer USER_ID = 17;
    private static final Integer ANOTHER_USER_ID = 33;
    private static final String EMAIL = "email";

    @Mock
    private UserRepository userRepository;
    @Mock
    private User userResponse;
    @Mock
    private Survey survey1;
    @Mock
    private Survey survey2;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(userRepository.save(any())).thenReturn(userResponse);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(userResponse));
        when(userResponse.getSurveys()).thenReturn(Arrays.asList(survey1, survey2));
        when(userResponse.getId()).thenReturn(USER_ID);
        when(userRepository.findByEmail(EMAIL)).thenReturn(Collections.singletonList(userResponse));
    }

    @Test
    void putUser_firstTime() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(new ArrayList<>());

        final User result = userService.putUser(EMAIL);

        assertEquals(userResponse, result);
        verify(userRepository).save(any());
    }

    @Test
    void putUser_SecondTime() {
        final User result = userService.putUser(EMAIL);

        assertEquals(userResponse, result);
        verify(userRepository, never()).save(any());
    }

    @Test
    void putUser_nullEmail() {
        assertThrows(NullPointerException.class, () -> userService.putUser(null));
    }

    @Test
    void getUserByEmail_happyPath() {
        final User result = userService.getUser(EMAIL);

        assertEquals(userResponse, result);
    }

    @Test
    void getUserByEmail_moreThanOneUser() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Arrays.asList(mock(User.class), mock(User.class)));

        assertThrows(IllegalArgumentException.class, () -> userService.getUser(EMAIL));
    }

    @Test
    void getUserByEmail_nullEmail() {
        assertThrows(NullPointerException.class, () -> userService.getUser(null));
    }

    @Test
    void getUserById_happyPath() {
        final User response = userService.getUser(USER_ID);

        assertEquals(userResponse, response);
    }

    @Test
    void getUserById_nullUserId() {
        assertThrows(NullPointerException.class, () -> userService.getUser(null));
    }

    @Test
    void getUserById_userNotExist() {
        assertThrows(NoSuchElementException.class, () -> userService.getUser(ANOTHER_USER_ID));
    }
}
