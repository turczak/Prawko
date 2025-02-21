package pl.turlap.prawko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.turlap.prawko.model.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
