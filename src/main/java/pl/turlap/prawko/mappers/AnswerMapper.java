package pl.turlap.prawko.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.turlap.prawko.dto.AnswerDto;
import pl.turlap.prawko.models.Answer;
import pl.turlap.prawko.models.AnswerTranslation;
import pl.turlap.prawko.models.Language;

@Component
@AllArgsConstructor
public class AnswerMapper {

    public AnswerDto toDto(Answer answer, Language language) {
        String translatedContent = "";
        for (AnswerTranslation translation : answer.getTranslations()) {
            if (translation.getLanguage() == language) {
                translatedContent = translation.getContent();
            }
        }
        return new AnswerDto()
                .withId(answer.getId())
                .withContent(translatedContent)
                .withLabel(answer.getLabel());
    }

}