package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.UkrainianWord;

public interface UkrainianWordService extends CrudService<UkrainianWord, Long>{
    Page<UkrainianWord> findPageUkrainianWords(Pageable pageable);
}
