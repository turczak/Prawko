package pl.turlap.prawko.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "Answer_translations")
@Data
public class AnswerTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

}