package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.TranslationPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.UkrainianWord;

public interface TranslationPairService extends CrudService<TranslationPair, Long>{
    Page<TranslationPair> findPageTranslationPairs(Pageable pageable);

    List<UkrainianWord> translateEnglishWordToUkrainian(EnglishWord englishWord);

    List<List<UkrainianWord>> translateBulkEnglishWordsToUkrainian(List<EnglishWord> englishWords);

    List<TranslationPair> createAll(List<TranslationPair> translationPairs);

    List<EnglishWord> translateUkrainianWordToEnglish(UkrainianWord ukrainianWord);
}
