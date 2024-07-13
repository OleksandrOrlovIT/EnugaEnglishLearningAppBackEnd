package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.engtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@DataJpaTest
class EnglishTestRepositoryTest {
    private static final String TEST_NAME = "TEST_NAME";
    private EnglishTest englishTest;

    @Autowired
    private EnglishTestRepository englishTestRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setRule(){
        englishTest = new EnglishTest();
        englishTest.setTestName(TEST_NAME);
    }

    @Transactional
    @Test
    void givenNewEnglishTest_whenSave_thenEnglishTestSaved() {
        EnglishTest savedEnglishTest = englishTestRepository.save(englishTest);
        assertThat(entityManager.find(EnglishTest.class, savedEnglishTest.getId())).isEqualTo(englishTest);
    }

    @Transactional
    @Test
    void givenEnglishTestCreated_whenUpdate_thenSuccess() {
        entityManager.persist(englishTest);
        String newName = "NEW NAME " + englishTest.getTestName();
        englishTest.setTestName(newName);
        englishTestRepository.save(englishTest);
        assertThat(entityManager.find(EnglishTest.class, englishTest.getId()).getTestName()).isEqualTo(newName);
    }

    @Transactional
    @Test
    void givenEnglishTestCreated_whenFindById_thenSuccess() {
        entityManager.persist(englishTest);
        Optional<EnglishTest> retrievedRule = englishTestRepository.findById(englishTest.getId());
        assertThat(retrievedRule).contains(englishTest);
    }

    @Transactional
    @Test
    void givenEnglishTestCreated_whenDelete_thenSuccess() {
        entityManager.persist(englishTest);
        englishTestRepository.delete(englishTest);
        assertThat(entityManager.find(EnglishTest.class, englishTest.getId())).isNull();
    }

    @Transactional
    @Test
    void givenTwoCreatedEnglishTests_whenFindAll_thenSuccess() {
        entityManager.persist(englishTest);

        EnglishTest rule2 = new EnglishTest();
        rule2.setTestName("TEST_NAME_2");
        entityManager.persist(rule2);

        List<EnglishTest> rules = englishTestRepository.findAll();

        assertEquals(2, rules.size());
        assertTrue(rules.contains(englishTest));
        assertTrue(rules.contains(rule2));
    }
}