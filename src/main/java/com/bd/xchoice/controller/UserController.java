package com.bd.xchoice.controller;

import com.bd.xchoice.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Interface for User controller.
 */
public interface UserController {

    /**
     * Create a user by providing data such as name and email.
     *
     * @param user the User object
     * @return the posted User object
     */
    @PostMapping("/users")
    User createUser(@RequestBody User user);

    /**
     * Get the user by user id.
     *
     * @param id the user id
     * @return the User object
     */
    @GetMapping("/users/{id}")
    User getUser(@PathVariable Integer id);
}
