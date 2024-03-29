package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services;

import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Question;

import java.util.List;

public interface QuestionService extends CrudService<Question, Long>{
    List<Question> getQuestionsByEnglishTestId(Long id);
}
