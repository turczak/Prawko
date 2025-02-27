package pl.turlap.prawko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.turlap.prawko.models.Category;
import pl.turlap.prawko.models.Question;
import pl.turlap.prawko.models.QuestionType;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM question q WHERE q.type = :type AND q.value = :value")
    List<Question> findQuestionsByTypeAndValue(@Param("type") QuestionType type, @Param("value") int value);

    @Query("SELECT q FROM question q WHERE q.type = :type")
    List<Question> findAllQuestionsByType(@Param("type") QuestionType type);

    @Query("SELECT q FROM question q " +
            "JOIN q.categories c " +
            "WHERE c = :category AND q.type = :type")
    List<Question> findAllByCategoryAndType(@Param("category") Category category,
                                            @Param("type") QuestionType type);

    @Query("SELECT q FROM question q WHERE :category MEMBER OF q.categories")
    List<Question> findAllByCategory(@Param("category") Category category);

}
