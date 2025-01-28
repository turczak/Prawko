package pl.turlap.prawko.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "language")
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

    @Column(name = "icon", columnDefinition = "VARCHAR(2)")
    private String icon;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "language")
    private List<QuestionTranslation> questionTranslations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "language")
    private List<AnswerTranslation> answerTranslations;

    @OneToMany(mappedBy = "language")
    private List<User> users;
}
