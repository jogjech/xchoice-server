package com.bd.xchoice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data model for SurveyMetadata. Note: this is not a JPA entity, but a client facing response entity.
 * Will consider separating out internal and external data models.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyMetadata {

    private int surveyId;

    private String title;

    private int responses;

    private boolean published;

    private SurveyStatus status;
}
