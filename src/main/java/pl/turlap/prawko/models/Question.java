package pl.turlap.prawko.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "questions")
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    // POLISH

    @Column(name = "content_PL")
    private String content_PL;

    @Column(name = "answer_A_PL")
    private String answer_A_PL;

    @Column(name = "answer_B_PL")
    private String answer_B_PL;

    @Column(name = "answer_C_PL")
    private String answer_C_PL;

    // ENGLISH

    @Column(name = "content_EN")
    private String content_EN;

    @Column(name = "answer_A_EN")
    private String answer_A_EN;

    @Column(name = "answer_B_EN")
    private String answer_B_EN;

    @Column(name = "answer_C_EN")
    private String answer_C_EN;

    // DEUTCH

    @Column(name = "content_DE")
    private String content_DE;

    @Column(name = "answer_A_DE")
    private String answer_A_DE;

    @Column(name = "answer_B_DE")
    private String answer_B_DE;

    @Column(name = "answer_C_DE")
    private String answer_C_DE;

    @Column(name = "correct_answer")
    private String correct;

    @Column(name = "media_url")
    private String media;

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private Integer value;

    @ManyToMany(mappedBy = "questions")
    private List<Test> tests = new ArrayList<>();

}

