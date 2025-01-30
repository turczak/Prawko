package pl.turlap.prawko.services.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.exceptions.LanguageNotFoundException;
import pl.turlap.prawko.models.Language;
import pl.turlap.prawko.repositories.LanguageRepository;
import pl.turlap.prawko.services.LanguageService;

import java.util.List;

@Service
@AllArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    @Override
    public Language findByCode(String languageCode) {
        return languageRepository.findByCode(languageCode).orElseThrow(() -> new LanguageNotFoundException("Language with code " + languageCode + " not found."));
    }

    @Override
    public List<Language> findAll() {
        return languageRepository.findAll();
    }
}