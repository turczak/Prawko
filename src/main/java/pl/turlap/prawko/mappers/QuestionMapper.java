package pl.turlap.prawko.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.turlap.prawko.dto.AnswerDto;
import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.models.Answer;
import pl.turlap.prawko.models.AnswerTranslation;
import pl.turlap.prawko.models.Language;
import pl.turlap.prawko.models.Question;
import pl.turlap.prawko.models.QuestionTranslation;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class QuestionMapper {

    private final CategoryMapper categoryMapper = new CategoryMapper();

    public QuestionDto mapToQuestionDto(Question question, Language language) {

        List<AnswerDto> translatedAnswers = getAnswerDtos(question, language);

        QuestionTranslation translatedQuestionContent = question.getTranslations().stream()
                .filter(translation -> translation.getLanguage().equals(language))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No translation found for question."));

        return new QuestionDto()
                .withName(question.getName())
                .withTranslation(translatedQuestionContent.getContent())
                .withAnswers(translatedAnswers)
                .withType(question.getType())
                .withValue(question.getValue())
                .withMedia(question.getMedia())
                .withCategories(question.getCategories().stream().map(categoryMapper::toCategoryDto).toList());
    }

    private List<AnswerDto> getAnswerDtos(Question question, Language language) {
        List<AnswerDto> translatedAnswers = new ArrayList<>();
        for (Answer answer : question.getAnswers()) {
            AnswerTranslation answerTranslation = answer.getTranslations().stream()
                    .filter(translation -> translation.getLanguage().equals(language))
                    .findFirst()
                    .orElse(null);
            if (answerTranslation != null) {
                translatedAnswers.add(new AnswerDto()
                        .withId(answer.getId())
                        .withLabel(answer.getLabel())
                        .withContent(answerTranslation.getContent()));
            } else {
                translatedAnswers.add(new AnswerDto().withId(answer.getId()).withLabel(answer.getLabel()));
            }
        }
        return translatedAnswers;
    }

}