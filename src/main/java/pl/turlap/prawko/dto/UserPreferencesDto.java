package pl.turlap.prawko.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserPreferencesDto {
    private Long userId;
    private String languageCode;
    private String categoryName;
}