package pl.turlap.prawko.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "answer_translation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    public AnswerTranslation(Answer answer, Language language) {
        this.answer = answer;
        this.language = language;
    }
}