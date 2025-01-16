package pl.turlap.prawko.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "answers")
@Data
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label")
    private Character label;

    @Column(name = "content")
    private String content;

    @Column(name = "isCorrect")
    private Boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @OneToMany(mappedBy = "answer")
    private List<AnswerTranslation> translations;

}
