package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.vocabulary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.EnglishWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.TranslationPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.UkrainianWord;

import java.util.List;

@Repository
public interface TranslationPairRepository extends JpaRepository<TranslationPair, Long> {
    List<TranslationPair> findAllByEnglishWord(EnglishWord englishWord);

    List<TranslationPair> findAllByUkrainianWord(UkrainianWord ukrainianWord);
}
