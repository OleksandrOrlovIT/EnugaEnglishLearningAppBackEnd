package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.vocabulary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.EnglishWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.UkrainianWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.CrudService;

import java.util.Collection;
import java.util.List;

public interface UkrainianWordService extends CrudService<UkrainianWord, Long> {
    Page<UkrainianWord> findPageUkrainianWords(Pageable pageable);

    UkrainianWord findByWord(String word);

    boolean existsByWord(String word);

    List<UkrainianWord> findAllByWordIgnoreCase(String word);

    List<UkrainianWord> createAll(Collection<UkrainianWord> ukrainianWords);
}
