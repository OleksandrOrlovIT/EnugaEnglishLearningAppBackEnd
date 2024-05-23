package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishWord;

import java.util.Optional;

public interface EnglishWordService extends CrudService<EnglishWord, Long> {
    Page<EnglishWord> findPageEnglishWords(Pageable pageable);

    EnglishWord findByWord(String word);

    boolean existsByWord(String word);
}
