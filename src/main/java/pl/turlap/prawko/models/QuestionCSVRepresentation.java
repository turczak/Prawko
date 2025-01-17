package pl.turlap.prawko.models;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionCSVRepresentation {

    @CsvBindByName(column = "Nazwa pytania")
    private String nazwa_pytania;

    @CsvBindByName(column = "Numer pytania")
    private Integer numer_pytania;

    @CsvBindByName(column = "Pytanie")
    private String tresc_PL;

    @CsvBindByName(column = "Odpowiedź A")
    private String odp_A_PL;

    @CsvBindByName(column = "Odpowiedź B")
    private String odp_B_PL;

    @CsvBindByName(column = "Odpowiedź C")
    private String odp_C_PL;

    @CsvBindByName(column = "Pytanie ENG")
    private String tresc_EN;

    @CsvBindByName(column = "Odpowiedź ENG A")
    private String odp_A_EN;

    @CsvBindByName(column = "Odpowiedź ENG B")
    private String odp_B_EN;

    @CsvBindByName(column = "Odpowiedź ENG C")
    private String odp_C_EN;

    @CsvBindByName(column = "Pytanie DE")
    private String tresc_DE;

    @CsvBindByName(column = "Odpowiedź DE A")
    private String odp_A_DE;

    @CsvBindByName(column = "Odpowiedź DE B")
    private String odp_B_DE;

    @CsvBindByName(column = "Odpowiedź DE C")
    private String odp_C_DE;

    @CsvBindByName(column = "Poprawna odp")
    private Character poprawna_odp;

    @CsvBindByName(column = "Media")
    private String media;

    @CsvBindByName(column = "Nazwa")
    private String nazwa;

    @CsvBindByName(column = "Zakres struktury")
    private String typ_pytania;

    @CsvBindByName(column = "Liczba punktów")
    private Integer wartosc;

    @CsvBindByName(column = "Kategorie")
    private String kategorie;

}
