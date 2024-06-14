package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.EnglishTestService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class WordModule {
    private static final String TEST_NAME = "Test Name";

    private static Long ID = 1L;

    private static EnglishTest validEnglishTest;

    @Autowired
    private EnglishTestService englishTestService;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUpValidEnglishTest(){
        validEnglishTest = EnglishTest
                .builder()
                .testName(TEST_NAME)
                .build();
    }

    @Test
    @Transactional
    void createEnglishTest_null(){
        assertThrows(NullPointerException.class, () -> englishTestService.create(null));
    }

    @Test
    @Transactional
    void createEnglishTest_IdNotNull(){
        assertThrows(IllegalArgumentException.class,
                () -> englishTestService.create(EnglishTest.builder().id(1L).build()));
    }

    @Test
    @Transactional
    void createEnglishTest_validEnglishTest(){
        validEnglishTest = englishTestService.create(validEnglishTest);

        EnglishTest englishTest = validEnglishTest;
        englishTest.setId(null);

        englishTest = englishTestService.create(englishTest);

        assertNotNull(englishTest);
    }

    @Test
    @Transactional
    void updateEnglishTest_null(){
        assertThrows(NullPointerException.class, () -> englishTestService.update(null));
    }

    @Test
    @Transactional
    void updateEnglishTest_nullId(){
        assertThrows(NullPointerException.class,
                () -> englishTestService.update(EnglishTest.builder().id(null).build()));
    }

    @Test
    @Transactional
    void updateEnglishTest_IdDoesntExist(){
        assertThrows(IllegalArgumentException.class,
                () -> englishTestService.update(EnglishTest.builder().id(1000000L).build()));
    }

    @Test
    @Transactional
    void updateEnglishTest_valid(){
        validEnglishTest = englishTestService.create(validEnglishTest);

        String tempName = TEST_NAME + "111";
        EnglishTest englishTest = EnglishTest.builder().id(validEnglishTest.getId()).testName(tempName).build();

        englishTest = englishTestService.update(englishTest);
        assertEquals(englishTest.getTestName(), tempName);
        assertEquals(englishTestService.findById(validEnglishTest.getId()).getTestName(), tempName);
    }

    @Test
    @Transactional
    void findById_NullId(){
        assertThrows(NullPointerException.class, () -> englishTestService.findById(null));
    }

    @Test
    @Transactional
    void findById_IdDoesntExist(){
        assertThrows(EntityNotFoundException.class, () -> englishTestService.findById(2000000L));
    }

    @Test
    @Transactional
    void findById_validId(){
        validEnglishTest = englishTestService.create(validEnglishTest);

        EnglishTest englishTest = englishTestService.findById(validEnglishTest.getId());
        assertEquals(englishTest, validEnglishTest);
    }

    @Test
    @Transactional
    void findAll_one(){
        validEnglishTest = englishTestService.create(validEnglishTest);
        List<EnglishTest> englishTestList = List.of(validEnglishTest);
        assertEquals(englishTestList, englishTestService.findAll());
    }


    @Test
    @Transactional
    void findAll_zero(){
        englishTestService.delete(validEnglishTest);
        List<EnglishTest> englishTestList = List.of();
        assertEquals(englishTestList, englishTestService.findAll());
    }

    @Test
    @Transactional
    void findAll_2(){
        validEnglishTest.setId(null);
        englishTestService.create(validEnglishTest);
        EnglishTest tempEnglishTest = EnglishTest.builder().build();
        ID = englishTestService.create(tempEnglishTest).getId();

        List<EnglishTest> englishTestList = List.of(validEnglishTest, tempEnglishTest);
        assertEquals(englishTestList, englishTestService.findAll());
    }

    @Test
    @Transactional
    void addQuestion_Valid(){
        validEnglishTest = englishTestService.create(validEnglishTest);

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
    @Transactional
    void deleteQuestion_Valid(){
        validEnglishTest = englishTestService.create(validEnglishTest);

        validEnglishTest = englishTestService.findById(validEnglishTest.getId());

        Question question = Question.builder()
                .questionText("Text")
                .answer("Answer")
                .englishTest(validEnglishTest)
                .build();

        englishTestService.addQuestion(question);

        englishTestService.deleteQuestion(question);

        validEnglishTest = englishTestService.findById(validEnglishTest.getId());

        assertEquals(0, validEnglishTest.getQuestions().size());
    }

    @Test
    @Transactional
    void delete_null(){
        assertThrows(NullPointerException.class, () -> englishTestService.delete(null));
    }

    @Test
    @Transactional
    void delete_valid(){
        validEnglishTest = englishTestService.create(validEnglishTest);

        assertEquals(1, englishTestService.findAll().size());

        englishTestService.delete(validEnglishTest);

        assertEquals(0, englishTestService.findAll().size());
    }

    @Test
    @Transactional
    void deleteById_null(){
        assertThrows(NullPointerException.class, () -> englishTestService.deleteById(null));
    }

    @Test
    @Transactional
    void deleteById_valid(){
        englishTestService.deleteById(ID);

        assertEquals(0, englishTestService.findAll().size());
    }
}