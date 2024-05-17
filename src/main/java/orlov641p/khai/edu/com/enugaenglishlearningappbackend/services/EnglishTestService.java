package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services;

import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Question;

public interface EnglishTestService extends CrudService<EnglishTest, Long>{
    void addQuestion(Question question);

    void deleteQuestion(Question question);
}
