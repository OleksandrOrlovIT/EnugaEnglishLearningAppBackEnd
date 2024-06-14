package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.request.WordModuleAttemptRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.wordmodule.WordModuleAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.CrudService;

import java.util.List;

public interface WordModuleService extends CrudService<WordModule, Long> {
    WordModuleAttempt takeTheTest(WordModuleAttemptRequest wordModuleAttemptRequest);

    List<WordModule> findByUserOrderByIdDesc(Long userId);

    List<WordModule> findByVisibilityPublicAndUserNot(Long userId);

    Page<WordModule> findPageByUserOrderByIdDesc(Long userId, Pageable pageable);

    Page<WordModule> findPageByVisibilityPublicAndUserNot(Long userId, Pageable pageable);
}