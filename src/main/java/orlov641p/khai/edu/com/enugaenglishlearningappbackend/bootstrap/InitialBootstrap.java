package orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.Book;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.enums.BookGenre;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.rule.Rule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.Role;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.EnglishWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.TranslationPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.UkrainianWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.book.BookLoaderService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.book.BookService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.EnglishTestService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.QuestionService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.rule.RuleService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.vocabulary.EnglishWordService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.vocabulary.TranslationPairService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.vocabulary.UkrainianWordService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@AllArgsConstructor
@Component
@Slf4j
@Profile("!test")
public class InitialBootstrap implements CommandLineRunner {

    private final RuleService ruleService;
    private final QuestionService questionService;
    private final EnglishTestService englishTestService;
    private final UkrainianWordService ukrainianWordService;
    private final EnglishWordService englishWordService;
    private final TranslationPairService translationPairService;
    private final BookLoaderService bookLoaderService;
    private final BookService bookService;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if (ruleService.getFirst() == null) {
            loadRules();
            log.info("Rules were loaded");
        } else {
            log.info("Rules loading were skipped");
        }

        if (questionService.getFirst() == null) {
            loadQuestionsAndTests();
            log.info("Questions were loaded");
        } else {
            log.info("Questions loading were skipped");
        }

        if (bookService.getFirst() == null) {
            loadBooks();
            log.info("Books were loaded");
        } else {
            log.info("Books loading were skipped");
        }

        if (translationPairService.getFirst() == null) {
            loadWordsToDB();
            log.info("Translation pairs were loaded");
        } else {
            log.info("Translation pairs loading were skipped");
        }

        if(userService.getFirst() == null){
            loadUsers();
            log.info("Users were loaded ");
        } else {
            log.info("Users loading were skipped");
        }
    }

    private void loadRules() {
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

    private void loadQuestionsAndTests() {
        loadTest1WithQuestions();
        loadTest2WithQuestions();
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
                .build();

        question1Test1.setEnglishTest(englishTest1);
        question2Test1.setEnglishTest(englishTest1);
        question3Test1.setEnglishTest(englishTest1);

        englishTestService.create(englishTest1);

        questionService.create(question1Test1);
        questionService.create(question2Test1);
        questionService.create(question3Test1);
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
                .build();

        question1Test2.setEnglishTest(englishTest2);
        question2Test2.setEnglishTest(englishTest2);

        englishTestService.create(englishTest2);

        questionService.create(question1Test2);
        questionService.create(question2Test2);
    }

    private void loadWordsToDB() {
        Path path = Paths.get("src/main/resources/static/Vocabulary.txt");

        try (Stream<String> lines = Files.lines(path).parallel()) {
            AtomicInteger counter = new AtomicInteger(0);
            Instant start = Instant.now();

            Cache<String, EnglishWord> englishWordCache = CacheBuilder.newBuilder()
                    .maximumSize(80000)
                    .build();

            Cache<String, UkrainianWord> ukrainianWordCache = CacheBuilder.newBuilder()
                    .maximumSize(80000)
                    .build();

            List<TranslationPair> translationPairs = new ArrayList<>();

            lines.forEach(line -> {
                try {
                    counter.incrementAndGet();
                    addWordsFromLine(line, englishWordCache, ukrainianWordCache, translationPairs);
                    if (counter.get() % 5000 == 0) {
                        Duration elapsed = Duration.between(start, Instant.now());
                        System.out.println("Processed " + counter.get() + " lines. Time elapsed: " + elapsed.toMillis() + " ms");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            batchInsertTranslationPairs(translationPairs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addWordsFromLine(String line, Cache<String, EnglishWord> englishWordCache, Cache<String, UkrainianWord> ukrainianWordCache, List<TranslationPair> translationPairs) {
        String[] words = line.split(" ");
        String englishWordStr = words[0];
        String ukrainianWordStr = words[1];

        EnglishWord englishWord = englishWordCache.getIfPresent(englishWordStr);
        if (englishWord == null) {
            EnglishWord foundEnglishWord = englishWordService.findByWord(englishWordStr);
            englishWord = foundEnglishWord != null ? foundEnglishWord : englishWordService.create(EnglishWord.builder().word(englishWordStr).build());
            englishWordCache.put(englishWordStr, englishWord);
        }

        UkrainianWord ukrainianWord = ukrainianWordCache.getIfPresent(ukrainianWordStr);
        if (ukrainianWord == null) {
            UkrainianWord foundUkrainianWord = ukrainianWordService.findByWord(ukrainianWordStr);
            ukrainianWord = foundUkrainianWord != null ? foundUkrainianWord : ukrainianWordService.create(UkrainianWord.builder().word(ukrainianWordStr).build());
            ukrainianWordCache.put(ukrainianWordStr, ukrainianWord);
        }

        TranslationPair translationPair = TranslationPair.builder()
                .englishWord(englishWord)
                .ukrainianWord(ukrainianWord)
                .build();

        synchronized (translationPairs) {
            translationPairs.add(translationPair);
            if (translationPairs.size() >= 1000) {
                batchInsertTranslationPairs(new ArrayList<>(translationPairs));
                translationPairs.clear();
            }
        }
    }

    private void batchInsertTranslationPairs(List<TranslationPair> translationPairs) {
        translationPairService.createAll(translationPairs);
    }

    private void loadBooks() throws Exception {
        File romeoAndJulietFile = new File("src/main/resources/static/RomeoAndJuliet.txt");

        Book romeoAndJuliet = Book.builder()
                .title("Romeo and Juliet")
                .author("William Shakespeare")
                .bookGenre(BookGenre.DRAMA)
                .build();

        bookLoaderService.loadBookFromFile(romeoAndJulietFile, romeoAndJuliet);
    }

    private void loadUsers() {
        User withoutSubUser = User.builder()
                .email("test1@email.com")
                .firstName("TestName1")
                .lastName("TestName1")
                .password("123")
                .roles(Set.of(Role.USER_WITHOUT_SUBSCRIPTION))
                .build();

        User withSubUser = User.builder()
                .email("test2@email.com")
                .firstName("TestName2")
                .lastName("TestName2")
                .password("123")
                .roles(Set.of(Role.USER_WITHOUT_SUBSCRIPTION, Role.USER_WITH_SUBSCRIPTION))
                .build();

        User englishStudent = User.builder()
                .email("test3@email.com")
                .firstName("TestName3")
                .lastName("TestName3")
                .password("123")
                .roles(Set.of(Role.USER_WITHOUT_SUBSCRIPTION, Role.USER_WITH_SUBSCRIPTION, Role.ENGLISH_STUDENT_USER))
                .build();

        User englishTeacher = User.builder()
                .email("test4@email.com")
                .firstName("TestName4")
                .lastName("TestName4")
                .password("123")
                .roles(Set.of(Role.USER_WITHOUT_SUBSCRIPTION, Role.USER_WITH_SUBSCRIPTION, Role.ENGLISH_STUDENT_USER,
                        Role.ENGLISH_TEACHER_USER))
                .build();

        User admin = User.builder()
                .email("test5@email.com")
                .firstName("TestName5")
                .lastName("TestName5")
                .password("123")
                .roles(Set.of(Role.USER_WITHOUT_SUBSCRIPTION, Role.USER_WITH_SUBSCRIPTION, Role.ENGLISH_STUDENT_USER,
                        Role.ENGLISH_TEACHER_USER, Role.ADMIN))
                .build();

        userService.create(withoutSubUser);
        userService.create(withSubUser);
        userService.create(englishStudent);
        userService.create(englishTeacher);
        userService.create(admin);
    }
}
