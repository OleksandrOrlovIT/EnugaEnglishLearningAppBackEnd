package orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.*;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@AllArgsConstructor
@Component
@Profile("!test")
public class InitialBootstrap implements CommandLineRunner {

    private final RuleService ruleService;
    private final QuestionService questionService;
    private final EnglishTestService englishTestService;
    private final UkrainianWordService ukrainianWordService;
    private final EnglishWordService englishWordService;
    private final TranslationPairService translationPairService;

    @Override
    public void run(String... args) throws Exception {
        if(ruleService.getFirst() == null) {
            loadRules();
        }
        if(questionService.getFirst() == null) {
            loadQuestionsAndTests();
        }
        if(translationPairService.getFirst() == null) {
            loadWordsToDB();
        }
    }

    private void loadRules(){
        Rule presentSimple = Rule.builder()
                .ruleName("Present Simple")
                .description("The present simple tense is a grammatical form used to describe actions or states that" +
                        " are habitual, regular, or timeless. It's often employed to convey facts, general truths," +
                        " routines, or actions that occur regularly. In English, it's formed by using the base form of" +
                        " the verb for most subjects, but with the addition of 's' or 'es' for third-person singular" +
                        " subjects (he, she, it). For example, \"She eats breakfast every morning\" or \"The sun rises" +
                        " in the east.\" It's also used in certain cases to describe scheduled events in the future, as" +
                        " in \"The train leaves at 10:00 tomorrow.\"\n ©ChatGPT OpenAI")
                .build();

        Rule presentContinuous = Rule.builder()
                .ruleName("Present continuous")
                .description("The present continuous tense, also known as the present progressive tense," +
                        " is a grammatical form used to describe actions that are happening right now," +
                        " at the moment of speaking, or actions that are ongoing but not necessarily happening" +
                        " at the moment of speaking. It's formed by using the present tense of the verb \"to be\"" +
                        " (am, is, are) followed by the present participle (-ing form) of the main verb. For example," +
                        " \"She is reading a book\" describes an action happening at the moment of speaking, while" +
                        " \"They are playing soccer\" describes an ongoing action. This tense can also be used to" +
                        " describe future arrangements or plans, especially when they are fixed or definite." +
                        "\n ©ChatGPT OpenAI")
                .build();

        ruleService.create(presentSimple);
        ruleService.create(presentContinuous);
    }

    private void loadQuestionsAndTests(){
        loadTest1WithQuestions();
        loadTest2WithQuestions();
    }

    private void loadTest1WithQuestions(){
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
                .build();

        question1Test1.setEnglishTest(englishTest1);
        question2Test1.setEnglishTest(englishTest1);
        question3Test1.setEnglishTest(englishTest1);

        englishTestService.create(englishTest1);

        questionService.create(question1Test1);
        questionService.create(question2Test1);
        questionService.create(question3Test1);
    }

    private void loadTest2WithQuestions(){
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
                .build();

        question1Test2.setEnglishTest(englishTest2);
        question2Test2.setEnglishTest(englishTest2);

        englishTestService.create(englishTest2);

        questionService.create(question1Test2);
        questionService.create(question2Test2);
    }

    private void loadWordsToDB() {
        Path path = Paths.get("src/main/resources/static/english-ukraine.txt");

        try (Stream<String> lines = Files.lines(path)) {
            lines.parallel().forEach(this::addWordsFromLine);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addWordsFromLine(String line){
        String[] words = line.split(" ");
        EnglishWord englishWord = EnglishWord.builder().word(words[0]).build();
        UkrainianWord ukrainianWord = UkrainianWord.builder().word(words[1]).build();
        TranslationPair translationPair = TranslationPair.builder()
                .englishWord(englishWord)
                .ukrainianWord(ukrainianWord)
                .build();

        englishWordService.create(englishWord);
        ukrainianWordService.create(ukrainianWord);
        translationPairService.create(translationPair);
    }
}
