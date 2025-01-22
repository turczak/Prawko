package pl.turlap.prawko.mappers;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.turlap.prawko.dto.AnswerDto;
import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.models.*;
import pl.turlap.prawko.repositories.CategoryRepository;
import pl.turlap.prawko.repositories.LanguageRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class QuestionMapper {

    private final LanguageRepository languageRepository;

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper = new CategoryMapper();


    public QuestionDto mapToQuestionDto(Question question, Language language) {

        List<AnswerDto> translatedAnswers = getAnswerDtos(question, language);

        QuestionTranslation translatedQuestionContent = question.getTranslations().stream()
                .filter(translation -> translation.getLanguage().equals(language))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No translation found for question."));

        return QuestionDto.builder()
                .name(question.getName())
                .questionTranslation(translatedQuestionContent.getContent())
                .answers(translatedAnswers)
                .type(question.getType())
                .value(question.getValue())
                .media(question.getMedia())
                .categories(categoryMapper.toCategoryDto(question.getCategories()))
                .build();
    }

    private List<AnswerDto> getAnswerDtos(Question question, Language language) {
        List<AnswerDto> translatedAnswers = new ArrayList<>();
        for (Answer answer : question.getAnswers()) {
            AnswerTranslation answerTranslation = answer.getTranslations().stream()
                    .filter(translation -> translation.getLanguage().equals(language))
                    .findFirst()
                    .orElse(null);
            AnswerDto answerDto = AnswerDto.builder()
                    .label(answer.getLabel())
                    .build();
            if (answerTranslation != null) {
                answerDto.setContent(answerTranslation.getContent());
            }
            translatedAnswers.add(answerDto);
        }
        return translatedAnswers;
    }

    public List<Question> mapCSVtoQuestions(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            HeaderColumnNameMappingStrategy<QuestionCSVRepresentation> strategy =
                    new HeaderColumnNameMappingStrategy<>();
            strategy.setType(QuestionCSVRepresentation.class);

            CsvToBean<QuestionCSVRepresentation> csvToBean =
                    new CsvToBeanBuilder<QuestionCSVRepresentation>(reader)
                            .withMappingStrategy(strategy)
                            .withIgnoreEmptyLine(true)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();

            return getQuestions(csvToBean);
        } catch (IOException e) {
            throw new RuntimeException("CSV data is failed to parse: " + e.getMessage());
        }
    }

    private List<Question> getQuestions(CsvToBean<QuestionCSVRepresentation> csvToBean) {
        return csvToBean.parse()
                .stream()
                .map(csvLine -> {
                            Question question = Question.builder()
                                    .id(Long.valueOf(csvLine.getId()))
                                    .name(csvLine.getName())
                                    .type(QuestionType.valueOf(csvLine.getType()))
                                    .media(csvLine.getMediaName())
                                    .value(csvLine.getValue())
                                    .build();

                            List<Category> categories = getCategoriesFromCSV(csvLine);
                            question.setCategories(categories);

                            List<QuestionTranslation> translations = mapQuestionTranslations(csvLine, question);
                            question.setTranslations(translations);

                            List<Answer> answers = getAnswerTranslationsFromCSV(csvLine, question);
                            question.setAnswers(answers);

                            return question;
                        }
                )
                .collect(Collectors.toList());
    }

    private List<Category> getCategoriesFromCSV(QuestionCSVRepresentation csvLine) {
        List<Category> categories = new ArrayList<>();

        for (String s : csvLine.getCategories().split(",")) {
            if (categoryRepository.findByName(s) != null) {
                categories.add(categoryRepository.findByName(s));
            }
        }

        return categories;
    }

    private List<Answer> getAnswerTranslationsFromCSV(QuestionCSVRepresentation csvLine, Question question) {
        List<Answer> answers = new ArrayList<>();
        Character correctAnswer = csvLine.getCorrectAnswer();

        if (csvLine.getType().equals(QuestionType.PODSTAWOWY.name())) {
            List<Answer> basicAnswers = mapBasicQuestionAnswers(csvLine, question);
            answers.addAll(basicAnswers);
        }

        if (csvLine.getType().equals(QuestionType.SPECJALISTYCZNY.name())) {

            List<Answer> specialAnswers = getSpecialQuestionAnswersFromCSV(csvLine, question);
            answers.addAll(specialAnswers);

            for (Answer answer : specialAnswers) {
                if (correctAnswer.equals(answer.getLabel())) {
                    answer.setIsCorrect(true);
                }
            }
        }

        return answers;
    }

    private List<Answer> getSpecialQuestionAnswersFromCSV(QuestionCSVRepresentation csvLine, Question question) {
        List<Answer> answers = new ArrayList<>();

        char[] labels = {'A', 'B', 'C'};
        List<Language> languages = languageRepository.findAll();

        try {
            for (char label : labels) {
                Answer answer = new Answer(label, question);
                List<AnswerTranslation> translations = new ArrayList<>();
                for (Language language : languages) {
                    AnswerTranslation translation = new AnswerTranslation(answer, language);
                    translation.setContent(getTranslationContent(language, label, csvLine));
                    translations.add(translation);
                }
                answer.setTranslations(translations);
                answers.add(answer);
            }
        } catch (NoSuchMethodException e) {
            System.err.println("Provided method not found: " + e.getMessage());
        } catch (InvocationTargetException e) {
            System.err.println("Error invoking a method: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.err.println("Illegal access to method: " + e.getMessage());
        }
        return answers;
    }

    private String getTranslationContent(Language language, char label, QuestionCSVRepresentation csvLine) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String prefix = "getAnswer_";
        Method method = csvLine.getClass().getMethod(prefix + label + "_" + language.getCode().toUpperCase());
        return method.invoke(csvLine).toString();
    }

    private List<QuestionTranslation> mapQuestionTranslations(QuestionCSVRepresentation csvLine, Question question) {
        return languageRepository.findAll().stream()
                .map(language -> {
                    String content = switch (language.getCode()) {
                        case "PL" -> csvLine.getContent_PL();
                        case "EN" -> csvLine.getContent_EN();
                        case "DE" -> csvLine.getContent_DE();
                        default -> throw new IllegalStateException("Unexpected value: " + language.getCode());
                    };
                    return QuestionTranslation.builder()
                            .question(question)
                            .language(language)
                            .content(content)
                            .build();
                }).collect(Collectors.toList());
    }

    private List<Answer> mapBasicQuestionAnswers(QuestionCSVRepresentation csvLine, Question question) {
        boolean isCorrectAnswerT = csvLine.getCorrectAnswer().equals('T');

        Answer answer_1 = Answer.builder()
                .question(question)
                .label('T')
                .isCorrect(isCorrectAnswerT)
                .build();
        Answer answer_2 = Answer.builder()
                .question(question)
                .label('N')
                .isCorrect(!isCorrectAnswerT)
                .build();

        return List.of(answer_1, answer_2);
    }

}