package pl.turlap.prawko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.turlap.prawko.model.Question;
import pl.turlap.prawko.model.QuestionType;
import pl.turlap.prawko.model.Test;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    @Query("SELECT t FROM test t WHERE t.user.id = :userId AND t.isActive = true")
    Test findActiveUserTest(@Param("userId") Long userId);

    @Query("SELECT q FROM question q WHERE q.type = :type AND q.value = :value")
    List<Question> findQuestionsByTypeAndValue(@Param("type") QuestionType type, @Param("value") int value);

    @Query("SELECT t FROM test t WHERE t.user.id = :userId")
    List<Test> findAllByUserId(@Param("userId") Long userId);

}
