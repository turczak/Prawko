package pl.turlap.prawko.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "answers")
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

    @Column(name = "isCorrect", columnDefinition = "BOOLEAN")
    private Boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
    private List<AnswerTranslation> translations;

}
