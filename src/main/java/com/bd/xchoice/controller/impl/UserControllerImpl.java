package com.bd.xchoice.controller.impl;

import com.bd.xchoice.controller.UserController;
import com.bd.xchoice.model.User;
import com.bd.xchoice.repository.UserRepository;
import com.bd.xchoice.security.JwtUtil;
import lombok.NonNull;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implementation of UserController.
 */
@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User putUser() {
        final String email = JwtUtil.getUserEmail();

        final List<User> userList = userRepository.findByEmail(email);
        if (userList.isEmpty()) {
            return userRepository.save(User.builder().email(email).build());
        }
        Validate.isTrue(userList.size() == 1, "Found more than 1 user");
        return userList.get(0);
    }

    @Override
    public User getUser(@NonNull final Integer id) {
        final User result = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        result.getSurveys().forEach(survey -> survey.setPublisherId(result.getId()));
        return result;
    }
}
