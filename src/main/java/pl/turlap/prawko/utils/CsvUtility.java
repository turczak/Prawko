package pl.turlap.prawko.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;
import pl.turlap.prawko.models.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvUtility {
    public static String TYPE = "text/csv";
    static String[] HEADERs = {
            "Nazwa pytania",
            "Numer pytania",
            "Pytanie",
            "Odpowiedź A",
            "Odpowiedź B",
            "Odpowiedź C",
            "Pytanie ENG",
            "Odpowiedź ENG A",
            "Odpowiedź ENG B",
            "Odpowiedź ENG C",
            "Pytanie DE",
            "Odpowiedź DE A",
            "Odpowiedź DE B",
            "Odpowiedź DE C",
            "Poprawna odp",
            "Media",
            "Zakres struktury",
            "Liczba pyunktów",
            "Kategorie",
            "Nazwa bloku",
            "Źródło pytania",
            "O co chcemy zapytać",
            "Jaki ma związek z bezpieczeństwem",
            "Status",
            "Podmiot",
            "Nazwa media tłumaczenie migowe (PJM) treść pyt",
            "Nazwa media tłumaczenie migowe (PJM) odp A",
            "Nazwa media tłumaczenie migowe (PJM) odp B",
            "Nazwa media tłumaczenie migowe (PJM) odp C",
            "Nazwa media tłumaczenie migowe (SJM) treść pyt",
            "Nazwa media tłumaczenie migowe (SJM) treść odp A",
            "Nazwa media tłumaczenie migowe (SJM) treść odp B",
            "Nazwa media tłumaczenie migowe (SJM) treść odp C",

    };

    public static boolean hasCsvFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<Question> csvToQuestionList(InputStream is) {
        try (BufferedReader bReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(bReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<Question> questions = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                Question question = new Question();
                question.setId(Long.valueOf(csvRecord.get("Numer pytania")));
                question.setName(csvRecord.get("Nazwa pytania"));
                question.setContent_PL(csvRecord.get("Pytanie"));
                question.setAnswer_A_PL(csvRecord.get("Odpowiedź A"));
                question.setAnswer_B_PL(csvRecord.get("Odpowiedź B"));
                question.setAnswer_C_PL(csvRecord.get("Odpowiedź C"));
                question.setContent_EN(csvRecord.get("Pytanie ENG"));
                question.setAnswer_A_EN(csvRecord.get("Odpowiedź ENG A"));
                question.setAnswer_B_EN(csvRecord.get("Odpowiedź ENG B"));
                question.setAnswer_C_EN(csvRecord.get("Odpowiedź ENG C"));
                question.setContent_DE(csvRecord.get("Pytanie DE"));
                question.setAnswer_A_DE(csvRecord.get("Odpowiedź DE A"));
                question.setAnswer_B_DE(csvRecord.get("Odpowiedź DE B"));
                question.setAnswer_C_DE(csvRecord.get("Odpowiedź DE C"));
                question.setCorrect(csvRecord.get("Poprawna odp"));
                question.setMedia(csvRecord.get("Media"));
                question.setType(csvRecord.get("Zakres struktury"));
                question.setValue(Integer.parseInt(csvRecord.get("Liczba punktów")));


                questions.add(question);
            }
            return questions;
        } catch (IOException e) {
            throw new RuntimeException("CSV data is failed to parse: " + e.getMessage());
        }
    }
}
