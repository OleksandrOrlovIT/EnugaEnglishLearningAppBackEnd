package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest;

import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.CrudService;

import java.util.List;

public interface QuestionService extends CrudService<Question, Long> {
    List<Question> getQuestionsByEnglishTestId(Long id);

    List<Question> createAll(List<Question> questions);

    boolean existsById(Long id);
}
