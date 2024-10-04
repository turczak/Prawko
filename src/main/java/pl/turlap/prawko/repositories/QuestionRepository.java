package pl.turlap.prawko.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.turlap.prawko.models.Question;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findById(Long id);

    @Query("SELECT q FROM questions q WHERE q.type = :type AND q.value = :value")
    List<Question> findQuestionsByTypeAndValue(@Param("type") String type, @Param("value")int value);
}
