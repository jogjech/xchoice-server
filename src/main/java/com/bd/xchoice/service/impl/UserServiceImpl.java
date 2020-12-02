package com.bd.xchoice.service.impl;

import com.bd.xchoice.model.User;
import com.bd.xchoice.repository.UserRepository;
import com.bd.xchoice.service.UserService;
import lombok.NonNull;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User putUser(@NonNull String email) {
        final List<User> userList = userRepository.findByEmail(email);
        if (userList.isEmpty()) {
            return userRepository.save(User.builder().email(email).build());
        }
        Validate.isTrue(userList.size() == 1, "Found more than 1 user");
        return userList.get(0);
    }

    @Override
    public User getUser(@NonNull final String email) {
        final List<User> userList = userRepository.findByEmail(email);
        Validate.isTrue(userList.size() == 1);

        return userList.get(0);
    }

    @Override
    public User getUser(final int id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
