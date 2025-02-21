package pl.turlap.prawko.model;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionCSVRepresentation {

    @CsvBindByName(column = "Nazwa pytania")
    private String name;

    @CsvBindByName(column = "Numer pytania")
    private Integer id;

    @CsvBindByName(column = "Pytanie")
    private String content_PL;

    @CsvBindByName(column = "Odpowiedź A")
    private String answer_A_PL;

    @CsvBindByName(column = "Odpowiedź B")
    private String answer_B_PL;

    @CsvBindByName(column = "Odpowiedź C")
    private String answer_C_PL;

    @CsvBindByName(column = "Pytanie ENG")
    private String content_EN;

    @CsvBindByName(column = "Odpowiedź ENG A")
    private String answer_A_EN;

    @CsvBindByName(column = "Odpowiedź ENG B")
    private String answer_B_EN;

    @CsvBindByName(column = "Odpowiedź ENG C")
    private String answer_C_EN;

    @CsvBindByName(column = "Pytanie DE")
    private String content_DE;

    @CsvBindByName(column = "Odpowiedź DE A")
    private String answer_A_DE;

    @CsvBindByName(column = "Odpowiedź DE B")
    private String answer_B_DE;

    @CsvBindByName(column = "Odpowiedź DE C")
    private String answer_C_DE;

    @CsvBindByName(column = "Poprawna odp")
    private Character correctAnswer;

    @CsvBindByName(column = "Media")
    private String mediaName;

    @CsvBindByName(column = "Zakres struktury")
    private String type;

    @CsvBindByName(column = "Liczba punktów")
    private Integer value;

    @CsvBindByName(column = "Kategorie")
    private String categories;

}
