package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule;

import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.request.WordModuleAttemptRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.wordmodule.WordModuleAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.CrudService;

public interface WordModuleService extends CrudService<WordModule, Long> {
    WordModuleAttempt takeTheTest(WordModuleAttemptRequest wordModuleAttemptRequest);
}