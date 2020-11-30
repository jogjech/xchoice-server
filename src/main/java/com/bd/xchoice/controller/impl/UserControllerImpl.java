package com.bd.xchoice.controller.impl;

import com.bd.xchoice.controller.UserController;
import com.bd.xchoice.model.User;
import com.bd.xchoice.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

/**
 * Implementation of UserController.
 */
@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(@NonNull final User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(@NonNull final Integer id) {
        final User result = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        result.getSurveys().forEach(survey -> survey.setPublisherId(result.getId()));
        return result;
    }
}
