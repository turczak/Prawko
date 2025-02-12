package pl.turlap.prawko.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Entity(name = "answer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Long id;

    @Column(name = "label", columnDefinition = "CHAR")
    private Character label;

    @Column(name = "is_correct", columnDefinition = "BOOLEAN")
    private Boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
    private List<AnswerTranslation> translations;

    @ManyToMany(mappedBy = "userAnswers", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Test> tests;

    public Answer(Character label, Question question) {
        this.label = label;
        this.question = question;
        this.translations = Collections.emptyList();
        this.isCorrect = false;
    }
}
