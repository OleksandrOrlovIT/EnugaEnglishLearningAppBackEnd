package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EnglishTestTest {
    private static EnglishTest englishTest;

    private static final Long ID = 1L;
    private static final String TEST_NAME = "testName";

    @BeforeEach
    void setUp(){
        englishTest = EnglishTest.builder()
                .id(ID)
                .testName(TEST_NAME)
                .build();
    }

    @Test
    void getSetTestNameInEnglishTest(){
        assertEquals(TEST_NAME, englishTest.getTestName());

        String tempName = TEST_NAME + "!";
        englishTest.setTestName(tempName);

        assertEquals(tempName, englishTest.getTestName());
    }

    @Test
    void builder(){
        englishTest = EnglishTest
                .builder()
                .testName(TEST_NAME)
                .questions(new ArrayList<>())
                .build();

        assertEquals(TEST_NAME, englishTest.getTestName());
        assertEquals(englishTest.getQuestions().size(), 0);
    }

    @Test
    void equalsHashCode_ID(){
        EnglishTest englishTest1 = EnglishTest.builder().id(ID).build();
        EnglishTest englishTest2 = EnglishTest.builder().id(ID).build();

        assertEquals(englishTest1, englishTest2);
        assertEquals(englishTest1.hashCode(), englishTest2.hashCode());

        englishTest1.setId(englishTest2.getId() + 1);

        assertNotEquals(englishTest1, englishTest2);
        assertNotEquals(englishTest1.hashCode(), englishTest2.hashCode());
    }

    @Test
    void equalsHashCode_ID_TestName(){
        EnglishTest englishTest1 = EnglishTest.builder().id(ID).testName(TEST_NAME).build();
        EnglishTest englishTest2 = EnglishTest.builder().id(ID).testName(TEST_NAME).build();

        assertEquals(englishTest1, englishTest2);
        assertEquals(englishTest1.hashCode(), englishTest2.hashCode());

        englishTest1.setTestName(englishTest2.getTestName() + "1");

        assertNotEquals(englishTest1, englishTest2);
        assertNotEquals(englishTest1.hashCode(), englishTest2.hashCode());
    }

    @Test
    void englishTestToString(){
        String expected = "EnglishTest{id=1, testName='testName'}";
        assertEquals(expected, englishTest.toString());
    }

    @Test
    void englishTestBuilderToString(){
        EnglishTest.EnglishTestBuilder englishTestBuilder = EnglishTest.builder()
                .id(ID)
                .testName(TEST_NAME)
                .questions(List.of(new Question(), new Question()));

        String expected = "EnglishTest.EnglishTestBuilder(id=1, testName=testName, questions=[Question{id=null," +
                " questionText='null', answer='null'}, Question{id=null, questionText='null', answer='null'}]," +
                " testAttempts=null)";

        assertEquals(expected, englishTestBuilder.toString());
    }
}