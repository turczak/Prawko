package pl.turlap.prawko.service;

import org.springframework.web.multipart.MultipartFile;
import pl.turlap.prawko.model.Question;

import java.util.List;

public interface CSVService {

    List<Question> mapCSVtoQuestions(MultipartFile file);

}
