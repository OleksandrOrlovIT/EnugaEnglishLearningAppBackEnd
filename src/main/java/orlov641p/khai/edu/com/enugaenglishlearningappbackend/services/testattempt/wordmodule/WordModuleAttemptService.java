package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.testattempt.wordmodule;

import org.springframework.data.domain.Page;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.request.WordModuleAttemptPage;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.request.WordModuleAttemptWithoutAnswers;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.wordmodule.WordModuleAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.CrudService;

import java.util.List;

public interface WordModuleAttemptService extends CrudService<WordModuleAttempt, Long> {
    List<WordModuleAttempt> findWordModuleAttemptsByUser(User user);

    Page<WordModuleAttempt> findWordModuleAttemptsPageByUser(WordModuleAttemptPage wordModuleAttemptPage);

    Page<WordModuleAttempt> findLastWordModuleAttemptsPageByUserSortedByDate(WordModuleAttemptPage wordModuleAttemptPage);

    WordModuleAttempt findMaximumScoreWordModuleAttempt(WordModuleAttemptWithoutAnswers moduleAttemptWithoutAnswers);

    WordModuleAttempt findLastWordModuleAttemptScore(WordModuleAttemptWithoutAnswers moduleAttemptWithoutAnswers);
}
