package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EnglishTestTest {
    private static EnglishTest englishTest;

    private static final String TEST_NAME = "testName";

    @BeforeEach
    void setUp(){englishTest = new EnglishTest();}

    @Test
    void getSetTestNameInEnglishTest(){
        assertNull(englishTest.getTestName());

        englishTest.setTestName(TEST_NAME);

        assertEquals(TEST_NAME, englishTest.getTestName());
    }

    @Test
    void builder(){
        assertNull(englishTest.getTestName());
        assertNull(englishTest.getQuestions());

        englishTest = EnglishTest
                .builder()
                .testName(TEST_NAME)
                .questions(new ArrayList<>())
                .build();

        assertEquals(TEST_NAME, englishTest.getTestName());
        assertEquals(englishTest.getQuestions().size(), 0);
    }
}