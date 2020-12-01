package com.bd.xchoice.repository;

import com.bd.xchoice.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for CRUD Choice. Mainly used for saving responses.
 */
public interface ChoiceRepository extends JpaRepository<Choice, Integer> {
}
