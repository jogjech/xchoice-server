package com.bd.xchoice.controller.impl;

import com.bd.xchoice.controller.UserController;
import com.bd.xchoice.model.User;
import com.bd.xchoice.repository.UserRepository;
import com.bd.xchoice.security.JwtUtil;
import com.bd.xchoice.service.UserService;
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
    private UserService userService;

    @Override
    public User putUser() {
        return userService.putUser(JwtUtil.getUserEmail());
    }

    @Override
    public User getUser(@NonNull final Integer id) {
        return userService.getUser(id);
    }
}
