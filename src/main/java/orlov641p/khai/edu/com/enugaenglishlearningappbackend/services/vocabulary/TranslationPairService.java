package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.vocabulary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.EnglishWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.TranslationPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.UkrainianWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.CrudService;

import java.util.List;

public interface TranslationPairService extends CrudService<TranslationPair, Long> {
    Page<TranslationPair> findPageTranslationPairs(Pageable pageable);

    List<UkrainianWord> translateEnglishWordToUkrainian(EnglishWord englishWord);

    List<List<UkrainianWord>> translateBulkEnglishWordsToUkrainian(List<EnglishWord> englishWords);

    List<TranslationPair> createAll(List<TranslationPair> translationPairs);

    List<EnglishWord> translateUkrainianWordToEnglish(UkrainianWord ukrainianWord);
}
