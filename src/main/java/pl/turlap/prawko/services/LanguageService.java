package pl.turlap.prawko.services;

import pl.turlap.prawko.models.Language;

import java.util.List;

public interface LanguageService {

    List<Language> findAll();

    Language findByCode(String languageCode);

    Language findByNameOrCode(String nameOrCode);

}
