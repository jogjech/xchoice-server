package com.bd.xchoice.repository;

import com.bd.xchoice.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface for CRUD Response.
 */
public interface ResponseRepository extends JpaRepository<Response, Integer> {
}
