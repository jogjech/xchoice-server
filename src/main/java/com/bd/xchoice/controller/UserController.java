package com.bd.xchoice.controller;

import com.bd.xchoice.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Interface for User controller.
 */
public interface UserController {

    /**
     * Create a user by providing email.
     *
     * @return the posted User object
     */
    @PostMapping("/users")
    User putUser();

    /**
     * Get the user by user id.
     * Note: this API is only for testing purpose.
     *
     * @param id the user id
     * @return the User object
     */
    @GetMapping("/users/{id}")
    User getUser(@PathVariable Integer id);
}
