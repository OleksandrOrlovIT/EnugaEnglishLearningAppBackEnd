package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishWord;

public interface EnglishWordService extends CrudService<EnglishWord, Long> {
    Page<EnglishWord> findPageEnglishWords(Pageable pageable);
}
