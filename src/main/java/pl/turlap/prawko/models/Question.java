package pl.turlap.prawko.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.processing.SQL;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "questions")
@Data
public class Question {

    @Id
    @Column(name = "id", columnDefinition = "INT")
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(10)")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private List<QuestionTranslation> translations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private List<Answer> answers;

    @Column(name = "media_url", columnDefinition = "VARCHAR(50)")
    private String media;

    @Column(name = "type", columnDefinition = "VARCHAR(25)")
    private QuestionType type;

    @Column(name = "value", columnDefinition = "INT")
    private Integer value;

    @ManyToMany
    @JoinTable(
            name = "question_categories",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(mappedBy = "questions")
    private List<Test> tests = new ArrayList<>();

}