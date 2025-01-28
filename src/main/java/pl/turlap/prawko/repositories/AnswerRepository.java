package pl.turlap.prawko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.turlap.prawko.models.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
