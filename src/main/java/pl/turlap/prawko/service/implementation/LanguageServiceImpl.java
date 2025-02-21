package pl.turlap.prawko.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.exception.CustomNotFoundException;
import pl.turlap.prawko.model.Language;
import pl.turlap.prawko.repository.LanguageRepository;
import pl.turlap.prawko.service.LanguageService;

import java.util.List;

@Service
@AllArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    @Override
    public Language findByCode(String languageCode) {
        return languageRepository.findByCode(languageCode).orElseThrow(() -> new CustomNotFoundException("languageCode", "Language with code " + languageCode + " not found."));
    }

    @Override
    public List<Language> findAll() {
        return languageRepository.findAll();
    }

    @Override
    public Language findByNameOrCode(String nameOrCode) {
        return languageRepository.findByNameOrCode(nameOrCode).orElseThrow(()-> new CustomNotFoundException("language", "Language with name or code '" + nameOrCode + "' not found."));
    }
}
