package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import org.springframework.stereotype.Service;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.EnglishTestRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.EnglishTestService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.QuestionService;

import java.util.List;

@Service
public class EnglishTestServiceImpl implements EnglishTestService {

    private final EnglishTestRepository englishTestRepository;
    private final QuestionService questionService;

    public EnglishTestServiceImpl(EnglishTestRepository englishTestRepository, QuestionService questionService) {
        this.englishTestRepository = englishTestRepository;
        this.questionService = questionService;
    }

    @Override
    public List<EnglishTest> findAll() {
        List<EnglishTest> englishTests = englishTestRepository.findAll();

        for(EnglishTest englishTest : englishTests){
            englishTest.setQuestions(questionService.getQuestionsByEnglishTestId(englishTest.getId()));
        }

        return englishTests;
    }

    @Override
    public EnglishTest findById(Long id) {
        EnglishTest englishTest = englishTestRepository.findById(id).orElse(null);
        if(englishTest != null){
            englishTest.setQuestions(questionService.getQuestionsByEnglishTestId(id));
            return englishTest;
        } else {
            return null;
        }
    }

    @Override
    public EnglishTest save(EnglishTest englishTest) {
        if(englishTest != null){
            return englishTestRepository.save(englishTest);
        }

        return null;
    }

    @Override
    public void delete(EnglishTest englishTest) {
        if(findById(englishTest.getId()) != null){
            deleteAllQuestions(englishTest);
            englishTestRepository.delete(englishTest);
        }
    }

    @Override
    public void deleteById(Long id) {
        EnglishTest englishTest = findById(id);
        if(englishTest != null) {
            deleteAllQuestions(englishTest);
            englishTestRepository.deleteById(id);
        }
    }

    private void deleteAllQuestions(EnglishTest englishTest){
        List<Question> questions = englishTest.getQuestions();
        if(questions != null) {
            for (Question question : englishTest.getQuestions()) {
                questionService.delete(question);
            }
        }
    }
}
