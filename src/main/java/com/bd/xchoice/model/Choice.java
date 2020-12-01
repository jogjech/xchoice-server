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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Data model for Choice.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String text;

    @JsonIgnore
    @ManyToOne
    private Question question;

    @OneToMany(mappedBy = "choice", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Response> responses;

    public void attachReferenceToChild() {
        if (responses != null) {
            responses.forEach(response -> response.setChoice(this));
        }
    }
}
