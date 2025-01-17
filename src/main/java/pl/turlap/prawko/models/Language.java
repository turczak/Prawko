package pl.turlap.prawko.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "languages")
@Data
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(25)")
    private String name;

    @Column(name = "code", columnDefinition = "VARCHAR(2)")
    private String code;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "language")
    private List<QuestionTranslation> questionTranslations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "language")
    private List<AnswerTranslation> answerTranslations;

}
