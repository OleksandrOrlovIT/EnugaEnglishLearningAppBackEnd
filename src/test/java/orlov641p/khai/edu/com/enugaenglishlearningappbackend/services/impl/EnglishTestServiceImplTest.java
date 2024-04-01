package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.EnglishTestService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
class EnglishTestServiceImplTest {
    private static final String TEST_NAME = "Test Name";

    private static Long ID = 1L;

    private static EnglishTest validEnglishTest;

    @Autowired
    private EnglishTestService englishTestService;

    @BeforeAll
    static void setUpValidEnglishTest(){
        validEnglishTest = EnglishTest
                .builder()
                .testName(TEST_NAME)
                .build();
    }

    @Test
    @Order(1)
    void createEnglishTest_null(){
        assertThrows(NullPointerException.class, () -> englishTestService.create(null));
    }

    @Test
    @Order(2)
    void createEnglishTest_IdNotNull(){
        assertThrows(IllegalArgumentException.class,
                () -> englishTestService.create(EnglishTest.builder().id(1L).build()));
    }

    @Test
    @Order(3)
    void createEnglishTest_validEnglishTest(){
        EnglishTest englishTest = englishTestService.create(validEnglishTest);

        assertNotNull(englishTest);

        validEnglishTest = englishTest;
    }

    @Test
    @Order(4)
    void updateEnglishTest_null(){
        assertThrows(NullPointerException.class, () -> englishTestService.update(null));
    }

    @Test
    @Order(5)
    void updateEnglishTest_nullId(){
        assertThrows(NullPointerException.class,
                () -> englishTestService.update(EnglishTest.builder().id(null).build()));
    }

    @Test
    @Order(6)
    void updateEnglishTest_IdDoesntExist(){
        assertThrows(IllegalArgumentException.class,
                () -> englishTestService.update(EnglishTest.builder().id(1000000L).build()));
    }

    @Test
    @Order(7)
    void updateEnglishTest_valid(){
        String tempName = TEST_NAME + "111";
        EnglishTest englishTest = EnglishTest.builder().id(validEnglishTest.getId()).testName(tempName).build();

        englishTest = englishTestService.update(englishTest);
        assertEquals(englishTest.getTestName(), tempName);
        assertEquals(englishTestService.findById(validEnglishTest.getId()).getTestName(), tempName);

        englishTestService.update(validEnglishTest);
        assertEquals(englishTestService.findById(validEnglishTest.getId()).getTestName(), TEST_NAME);
    }

    @Test
    @Order(8)
    void findById_NullId(){
        assertThrows(NullPointerException.class, () -> englishTestService.findById(null));
    }

    @Test
    @Order(9)
    void findById_IdDoesntExist(){
        assertThrows(IllegalArgumentException.class, () -> englishTestService.findById(2000000L));
    }

    @Test
    @Order(10)
    void findById_validId(){
        EnglishTest englishTest = englishTestService.findById(validEnglishTest.getId());
        assertEquals(englishTest, validEnglishTest);
    }

    @Test
    @Order(11)
    void findAll_one(){
        List<EnglishTest> englishTestList = List.of(validEnglishTest);
        assertEquals(englishTestList, englishTestService.findAll());
    }


    @Test
    @Order(12)
    void findAll_zero(){
        englishTestService.delete(validEnglishTest);
        List<EnglishTest> englishTestList = List.of();
        assertEquals(englishTestList, englishTestService.findAll());
    }

    @Test
    @Order(13)
    void findAll_2(){
        validEnglishTest.setId(null);
        englishTestService.create(validEnglishTest);
        EnglishTest tempEnglishTest = EnglishTest.builder().build();
        ID = englishTestService.create(tempEnglishTest).getId();

        List<EnglishTest> englishTestList = List.of(validEnglishTest, tempEnglishTest);
        assertEquals(englishTestList, englishTestService.findAll());
    }

    @Test
    @Order(14)
    void addQuestion_Valid(){
        Question question = Question.builder()
                .questionText("Text")
                .answer("Answer")
                .englishTest(validEnglishTest)
                .build();

        englishTestService.addQuestion(question);

        validEnglishTest = englishTestService.findById(validEnglishTest.getId());
        assertEquals(1, validEnglishTest.getQuestions().size());
    }

    @Test
    @Order(15)
    void deleteQuestion_Valid(){
        Question question = validEnglishTest.getQuestions().get(0);

        System.out.println("Question = " + question);
        System.out.println(question.getEnglishTest());
        englishTestService.deleteQuestion(question);

        validEnglishTest = englishTestService.findById(validEnglishTest.getId());

        assertEquals(0, validEnglishTest.getQuestions().size());
    }

    @Test
    @Order(16)
    void delete_null(){
        assertThrows(NullPointerException.class, () -> englishTestService.delete(null));
    }

    @Test
    @Order(17)
    void delete_valid(){
        englishTestService.delete(validEnglishTest);

        assertEquals(1, englishTestService.findAll().size());
    }

    @Test
    @Order(18)
    void deleteById_null(){
        assertThrows(NullPointerException.class, () -> englishTestService.deleteById(null));
    }

    @Test
    @Order(19)
    void deleteById_valid(){
        englishTestService.deleteById(ID);

        assertEquals(0, englishTestService.findAll().size());
    }
}