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

    public TestDto toTestDto(Test test, Language language) {
        return new TestDto()
                .withUserId(test.getUser().getId())
                .withIsActive(test.getIsActive())
                .withQuestions(questionMapper.mapToQuestionsDtos(test.getQuestions(), language))
                .withUserAnswers(test.getUserAnswers().stream().map(answerMapper::toAnswerDto).toList())
                .withCreatedAt(test.getCreatedAt());
    }
}
