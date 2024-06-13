package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.vocabulary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.EnglishWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.CrudService;

import java.util.Collection;
import java.util.List;

public interface EnglishWordService extends CrudService<EnglishWord, Long> {
    Page<EnglishWord> findPageEnglishWords(Pageable pageable);

    EnglishWord findByWord(String word);

    boolean existsByWord(String word);

    List<EnglishWord> findAllByWordIgnoreCase(String word);

    List<EnglishWord> createAll(Collection<EnglishWord> englishWords);
}
