package com.bd.xchoice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data model for SurveyMetadata. Note: this is not a JPA entity, but a client facing response entity.
 * Will consider separating out internal and external data models.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyResponse {
    private int surveyId;
    private List<Integer> selections;
}
