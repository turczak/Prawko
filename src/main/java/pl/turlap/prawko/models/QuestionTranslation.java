package pl.turlap.prawko.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "question_translations")
@Data
public class QuestionTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

}
