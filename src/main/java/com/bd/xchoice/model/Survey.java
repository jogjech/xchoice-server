package com.bd.xchoice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;

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

    @OneToMany(mappedBy = "survey", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Question> questions;

    @JsonIgnore
    @ManyToOne
    private User publisher;

    @Transient
    private int publisherId;

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
        return questions.stream()
                .filter(question -> question.getChoices() == null)
                .flatMap(question -> question.getChoices().stream())
                .map(choice -> choice.getResponses().size())
                .reduce(0, Integer::sum);
    }
}
