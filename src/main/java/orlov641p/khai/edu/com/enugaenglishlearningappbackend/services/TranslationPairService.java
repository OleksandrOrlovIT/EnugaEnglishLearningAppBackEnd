package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.TranslationPair;

public interface TranslationPairService extends CrudService<TranslationPair, Long>{
    Page<TranslationPair> findPageTranslationPairs(Pageable pageable);
}
