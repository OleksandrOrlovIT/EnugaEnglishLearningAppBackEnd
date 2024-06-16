package orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.engtest.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.engtest.EnglishTestLoader;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.EnglishTestService;

import java.util.ArrayList;
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
            createThirdTest();
            createFourthTest();
            createPresentContinuousTest();
            createPresentPerfectTest();
            createFuturePerfectTest();
            createPastPerfectTest();
            log.info("Questions were loaded");
        } else {
            log.info("Questions loading were skipped");
        }
    }

    private void createPastPerfectTest() {
        String testName = "Past Perfect";

        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();

        questions.add("She (to visit) the museum before it closed.");
        answers.add("had visited");

        questions.add("We (to play) soccer after we had finished our homework.");
        answers.add("had played");

        questions.add("He (to finish) his homework by the time I arrived.");
        answers.add("had finished");

        questions.add("They (to watch) the movie once they had eaten dinner.");
        answers.add("had watched");

        questions.add("I (to travel) to many countries before I settled down.");
        answers.add("had traveled");

        createTest(testName, questions, answers);
    }

    private void createFuturePerfectTest() {
        String testName = "Future Perfect";

        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();

        questions.add("She (to visit) the museum by the time you arrive.");
        answers.add("will have visited");

        questions.add("We (to play) soccer for hours by sunset.");
        answers.add("will have played");

        questions.add("He (to finish) his homework by the end of the day.");
        answers.add("will have finished");

        questions.add("They (to watch) the movie by the time we get there.");
        answers.add("will have watched");

        questions.add("I (to travel) to many countries by the time I turn 30.");
        answers.add("will have traveled");

        createTest(testName, questions, answers);
    }

    private void createPresentPerfectTest() {
        String testName = "Present Perfect";

        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();

        questions.add("She (to visit) the museum several times.");
        answers.add("has visited");

        questions.add("We (to play) soccer already today.");
        answers.add("have played");

        questions.add("He (to finish) his homework just now.");
        answers.add("has finished");

        questions.add("They (to watch) a movie this week.");
        answers.add("have watched");

        questions.add("I (to travel) to many countries in my life.");
        answers.add("have traveled");

        createTest(testName, questions, answers);
    }

    private void createPresentContinuousTest() {
        String testName = "Present Continuous";

        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();

        questions.add("She (to visit) the museum right now.");
        answers.add("is visiting");

        questions.add("We (to play) soccer at the park this afternoon.");
        answers.add("are playing");

        questions.add("He (to finish) his homework at the moment.");
        answers.add("is finishing");

        questions.add("They (to watch) a movie right now.");
        answers.add("are watching");

        questions.add("I (to travel) to Japan currently.");
        answers.add("am traveling");

        createTest(testName, questions, answers);
    }

    private void createFourthTest(){
        String testName = "Future simple";

        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();

        questions.add("She (to visit) the museum tomorrow.");
        answers.add("will visit");

        questions.add("We (to play) soccer at the park on Saturday.");
        answers.add("will play");

        questions.add("He (to finish) his homework after dinner.");
        answers.add("will finish");

        questions.add("They (to watch) a movie tonight.");
        answers.add("will watch");

        questions.add("I (to travel) to Japan next summer.");
        answers.add("will travel");

        createTest(testName, questions, answers);
    }

    private void createThirdTest(){
        String testName = "Past simple";

        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();

        questions.add("She (to visit) the museum yesterday.");
        answers.add("visited");

        questions.add("He (to finish) his homework before dinner.");
        answers.add("finished");

        questions.add("They (to watch) a movie last night.");
        answers.add("watched");

        questions.add("I (to travel) to Japan last summer.");
        answers.add("traveled");

        questions.add("We (to play) soccer at the park on Saturday.");
        answers.add("played");

        createTest(testName, questions, answers);
    }

    private void createTest(String testName, List<String> questions, List<String> answers){
        List<Question> questionList = new ArrayList<>();

        for(int i = 0; i < questions.size(); i++){
            questionList.add(Question.builder()
                    .questionText(questions.get(i))
                    .answer(answers.get(i))
                    .build());
        }

        englishTestService.create(EnglishTest.builder()
                .testName(testName)
                .questions(questionList)
                .build());
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
