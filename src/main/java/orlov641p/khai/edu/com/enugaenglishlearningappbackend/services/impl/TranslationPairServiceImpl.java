package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.TranslationPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.TranslationPairRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.TranslationPairService;

import java.util.List;

@AllArgsConstructor
@Service
public class TranslationPairServiceImpl implements TranslationPairService {

    private final TranslationPairRepository translationPairRepository;

    @Override
    public List<TranslationPair> findAll() {
        return translationPairRepository.findAll();
    }

    @Override
    public TranslationPair findById(Long id) {
        checkTranslationPairIdNull(id);

        TranslationPair translationPair = translationPairRepository.findById(id).orElse(null);

        if(translationPair == null){
            throw new EntityNotFoundException("TranslationPair with id = " + id + " doesn't exist");
        }

        return translationPair;
    }

    @Override
    public synchronized TranslationPair create(TranslationPair translationPair) {
        checkTranslationPairNull(translationPair);

        return translationPairRepository.save(translationPair);
    }

    @Override
    public TranslationPair update(TranslationPair translationPair) {
        checkTranslationPairNull(translationPair);

        findById(translationPair.getId());

        return translationPairRepository.save(translationPair);
    }

    @Override
    public void delete(TranslationPair translationPair) {
        checkTranslationPairNull(translationPair);

        translationPairRepository.delete(translationPair);
    }

    @Override
    public void deleteById(Long id) {
        checkTranslationPairIdNull(id);

        translationPairRepository.deleteById(id);
    }

    @Override
    public TranslationPair getFirst() {
        Pageable pageable = Pageable
                .ofSize(1)
                .first();

        Page<TranslationPair> translationPairs = translationPairRepository.findAll(pageable);

        return translationPairs.hasContent() ? translationPairs.getContent().get(0) : null;
    }

    @Override
    public Page<TranslationPair> findPageTranslationPairs(Pageable pageable) {
        return translationPairRepository.findAll(pageable);
    }

    private void checkTranslationPairNull(TranslationPair translationPair){
        if (translationPair == null) {
            throw new IllegalArgumentException("TranslationPair can`t be null");
        }
    }

    private void checkTranslationPairIdNull(Long id){
        if(id == null){
            throw new IllegalArgumentException("TranslationPair id can`t be null");
        }
    }
}
