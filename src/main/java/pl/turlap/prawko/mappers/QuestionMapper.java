package pl.turlap.prawko.mappers;

import org.springframework.stereotype.Component;
import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.models.Language;
import pl.turlap.prawko.models.Question;

@Component
public class QuestionMapper {

    public QuestionDto mapToQuestionDto(Question question, Language language) {

        String content, answer_A, answer_B, answer_C;

        switch (language) {
            case PL -> {
                content = question.getContent_PL();
                answer_A = question.getAnswer_A_PL();
                answer_B = question.getAnswer_B_PL();
                answer_C = question.getAnswer_C_PL();
            }
            case EN -> {
                content = question.getContent_EN();
                answer_A = question.getAnswer_A_EN();
                answer_B = question.getAnswer_B_EN();
                answer_C = question.getAnswer_C_EN();
            }
            case DE -> {
                content = question.getContent_DE();
                answer_A = question.getAnswer_A_DE();
                answer_B = question.getAnswer_B_DE();
                answer_C = question.getAnswer_C_DE();
            }
            default -> throw new IllegalArgumentException("Unsupported language!" + language);
        }

        QuestionDto questionDto = QuestionDto.builder()
                .id(question.getId())
                .name(question.getName())
                .content(content)
                .answer_A(answer_A)
                .answer_B(answer_B)
                .answer_C(answer_C)
                .correct(question.getCorrect())
                .type(question.getType())
                .value(question.getValue())
                .media(question.getMedia())
                .build();
        return questionDto;
    }

}
