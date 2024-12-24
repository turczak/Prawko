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

    // POLISH

    @Column(name = "content_PL", columnDefinition = "TEXT")
    private String content_PL;

    @Column(name = "answer_A_PL", columnDefinition = "TEXT")
    private String answer_A_PL;

    @Column(name = "answer_B_PL", columnDefinition = "TEXT")
    private String answer_B_PL;

    @Column(name = "answer_C_PL", columnDefinition = "TEXT")
    private String answer_C_PL;

    // ENGLISH

    @Column(name = "content_EN", columnDefinition = "TEXT")
    private String content_EN;

    @Column(name = "answer_A_EN", columnDefinition = "TEXT")
    private String answer_A_EN;

    @Column(name = "answer_B_EN", columnDefinition = "TEXT")
    private String answer_B_EN;

    @Column(name = "answer_C_EN", columnDefinition = "TEXT")
    private String answer_C_EN;

    // DEUTCH

    @Column(name = "content_DE", columnDefinition = "TEXT")
    private String content_DE;

    @Column(name = "answer_A_DE", columnDefinition = "TEXT")
    private String answer_A_DE;

    @Column(name = "answer_B_DE", columnDefinition = "TEXT")
    private String answer_B_DE;

    @Column(name = "answer_C_DE", columnDefinition = "TEXT")
    private String answer_C_DE;

    // OTHER IMPORTANT THINGS

    @Column(name = "correct_answer", columnDefinition = "VARCHAR(1)")
    private String correct;

    @Column(name = "media_url", columnDefinition = "VARCHAR(50)")
    private String media;

    @Column(name = "type", columnDefinition = "VARCHAR(25)")
    private String type;

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

