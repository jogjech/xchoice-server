package com.bd.xchoice.repository;

import com.bd.xchoice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface for CRUD User.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    public List<User> findByEmail(final String email);
}
