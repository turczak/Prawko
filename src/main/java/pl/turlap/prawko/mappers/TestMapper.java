package pl.turlap.prawko.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.turlap.prawko.dto.TestDto;
import pl.turlap.prawko.models.Language;
import pl.turlap.prawko.models.Test;

@Component
@AllArgsConstructor
public class TestMapper {
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;

    public TestDto toDto(Test test, Language language) {
        return new TestDto()
                .withUserId(test.getUser().getId())
                .withIsActive(test.getIsActive())
                .withQuestions(test.getQuestions().stream().map(question -> questionMapper.toDto(question, language)).toList())
                .withUserAnswers(test.getUserAnswers().stream().map(answer -> answerMapper.toDto(answer, language)).toList())
                .withCreatedAt(test.getCreatedAt())
                .withScore(test.getScore());
    }

}
