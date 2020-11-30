package com.bd.xchoice.repository;

import com.bd.xchoice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for CRUD User.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
