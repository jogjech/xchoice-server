package com.bd.xchoice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Optional;

/**
 * Data model for Survey.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String title;

    @Enumerated(EnumType.STRING)
    private SurveyStatus status;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Question> questions;

    @JsonIgnore
    @ManyToOne
    private User publisher;

    public void attachReferenceToChild() {
        if (questions != null) {
            questions.forEach(question -> {
                question.attachReferenceToChild();
                question.setSurvey(this);
            });
        }
    }

    public int getTotalResponses() {
        if (questions == null) {
            return 0;
        }
        final int totalResponses = questions.stream()
                .filter(question -> question.getChoices() != null)
                .flatMap(question -> question.getChoices().stream())
                .map(choice -> Optional.ofNullable(choice.getResponses()).map(List::size).orElse(0))
                .reduce(0, Integer::sum);
        return totalResponses / questions.size(); // TODO: This is a hack with the assumption that all questions need to be taken.
    }
}
