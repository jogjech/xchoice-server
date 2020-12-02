package com.bd.xchoice.controller.impl;

import com.bd.xchoice.model.Survey;
import com.bd.xchoice.model.User;
import com.bd.xchoice.repository.UserRepository;
import com.bd.xchoice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Test class for UserControllerImpl.
 */
class UserControllerImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
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
    }
}
