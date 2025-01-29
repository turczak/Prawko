package pl.turlap.prawko.dto;

import lombok.Data;

@Data
public class UserPreferencesDto {
    private Long userId;
    private String languageCode;
    private String categoryName;
}