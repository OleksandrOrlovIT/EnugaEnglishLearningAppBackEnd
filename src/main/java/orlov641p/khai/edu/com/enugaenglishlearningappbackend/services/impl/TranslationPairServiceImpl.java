package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.TranslationPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.UkrainianWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.TranslationPairRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.EnglishWordService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.TranslationPairService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.UkrainianWordService;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class TranslationPairServiceImpl implements TranslationPairService {

    private final TranslationPairRepository translationPairRepository;
    private final EnglishWordService englishWordService;
    private final UkrainianWordService ukrainianWordService;

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
    public TranslationPair create(TranslationPair translationPair) {
        checkTranslationPairNull(translationPair);

        EnglishWord englishWord = englishWordService.findByWord(translationPair.getEnglishWord().getWord());

        if(englishWord == null){
            englishWord = englishWordService.create(translationPair.getEnglishWord());
        }

        translationPair.setEnglishWord(englishWord);

        UkrainianWord ukrainianWord = ukrainianWordService.findByWord(translationPair.getUkrainianWord().getWord());

        if(ukrainianWord == null){
            ukrainianWord = ukrainianWordService.create(translationPair.getUkrainianWord());
        }

        translationPair.setUkrainianWord(ukrainianWord);

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

    @Override
    public List<UkrainianWord> translateEnglishWordToUkrainian(EnglishWord englishWord) {
        List<EnglishWord> foundEngWords = englishWordService.findAllByWordIgnoreCase(englishWord.getWord());

        if(foundEngWords == null || foundEngWords.isEmpty()){
            throw new EntityNotFoundException("EnglishWord doesn't exist with word = " + englishWord);
        }

        EnglishWord foundEngWord = foundEngWords.get(0);

        List<TranslationPair> translationPairs = translationPairRepository.findAllByEnglishWord(foundEngWord);

        List<UkrainianWord> translations = new ArrayList<>();

        for(TranslationPair translationPair : translationPairs){
            translations.add(translationPair.getUkrainianWord());
        }

        return translations;
    }

    @Override
    public List<List<UkrainianWord>> translateBulkEnglishWordsToUkrainian(List<EnglishWord> englishWords) {
        List<List<UkrainianWord>> list = new ArrayList<>();

        for(EnglishWord word : englishWords){
            list.add(translateEnglishWordToUkrainian(word));
        }

        return list;
    }

    @Override
    public List<TranslationPair> createAll(List<TranslationPair> translationPairs) {
        return translationPairRepository.saveAll(translationPairs);
    }

    @Override
    public List<EnglishWord> translateUkrainianWordToEnglish(UkrainianWord ukrainianWord) {
        List<UkrainianWord> foundUkrWords = ukrainianWordService.findAllByWordIgnoreCase(ukrainianWord.getWord());

        if(foundUkrWords == null || foundUkrWords.isEmpty()){
            throw new EntityNotFoundException("UkrainianWord doesn't exist with word = " + ukrainianWord);
        }

        UkrainianWord foundUkrWord = foundUkrWords.get(0);

        List<TranslationPair> translationPairs = translationPairRepository.findAllByUkrainianWord(foundUkrWord);

        List<EnglishWord> translations = new ArrayList<>();

        for(TranslationPair translationPair : translationPairs){
            translations.add(translationPair.getEnglishWord());
        }

        return translations;
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
