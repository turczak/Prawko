package pl.turlap.prawko.service;

import pl.turlap.prawko.model.Language;

import java.util.List;

public interface LanguageService {

    List<Language> findAll();

    Language findByCode(String languageCode);

    Language findByNameOrCode(String nameOrCode);

}
