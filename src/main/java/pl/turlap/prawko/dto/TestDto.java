package pl.turlap.prawko.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;
import java.util.List;

@With
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TestDto {
    private LocalDateTime createdAt;
    private Boolean isActive;
    private Long userId;
    private List<QuestionDto> questions;
    private List<AnswerDto> userAnswers;
    private Integer score;
}
