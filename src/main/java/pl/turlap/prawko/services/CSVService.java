package pl.turlap.prawko.services;

import org.springframework.web.multipart.MultipartFile;
import pl.turlap.prawko.models.Question;

import java.util.List;

public interface CSVService {

    List<Question> mapCSVtoQuestions(MultipartFile file);

}
