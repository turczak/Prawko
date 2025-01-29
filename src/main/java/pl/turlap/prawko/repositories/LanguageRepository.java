package pl.turlap.prawko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.turlap.prawko.models.Language;

import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    Optional<Language> findByCode(String code);

    Language findByName(String name);

    @Query("SELECT l FROM language l WHERE l.code = :lang OR l.name = :lang")
    Language findByNameOrCode(@Param("lang") String lang);
}
