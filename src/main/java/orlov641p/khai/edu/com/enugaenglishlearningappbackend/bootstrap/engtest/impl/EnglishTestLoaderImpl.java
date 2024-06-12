package orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.engtest.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.engtest.EnglishTestLoader;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.EnglishTestService;

import java.util.List;

@Profile("!test")
@Slf4j
@AllArgsConstructor
@Component
public class EnglishTestLoaderImpl implements EnglishTestLoader {

    private final EnglishTestService englishTestService;

    @Override
    public void loadEnglishTests() {
        if (englishTestService.getFirst() == null) {
            loadTest1WithQuestions();
            loadTest2WithQuestions();
            log.info("Questions were loaded");
        } else {
            log.info("Questions loading were skipped");
        }
    }

    private void loadTest1WithQuestions() {
        Question question1Test1 = Question.builder()
                .questionText("In present simple, what form will be a to be verb for He")
                .answer("is")
                .build();

        Question question2Test1 = Question.builder()
                .questionText("One tooth, a lot of ?")
                .answer("teeth")
                .build();

        Question question3Test1 = Question.builder()
                .questionText("Trout in plural")
                .answer("Trout")
                .build();

        EnglishTest englishTest1 = EnglishTest.builder()
                .testName("First temp test")
                .questions(List.of(question1Test1, question2Test1, question3Test1))
                .build();

        englishTestService.create(englishTest1);
    }

    private void loadTest2WithQuestions() {
        Question question1Test2 = Question.builder()
                .questionText("My name __ Sasha")
                .answer("is")
                .build();

        Question question2Test2 = Question.builder()
                .questionText("I __ Sasha")
                .answer("am")
                .build();

        EnglishTest englishTest2 = EnglishTest.builder()
                .testName("Second temp test")
                .questions(List.of(question1Test2, question2Test2))
                .build();

        englishTestService.create(englishTest2);
    }
}
