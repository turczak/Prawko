package pl.turlap.prawko.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "questions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "translations")
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

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Column(name = "value", columnDefinition = "INT")
    private Integer value;

    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "questions_categories",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @ManyToMany(mappedBy = "questions")
    private List<Test> tests;

}