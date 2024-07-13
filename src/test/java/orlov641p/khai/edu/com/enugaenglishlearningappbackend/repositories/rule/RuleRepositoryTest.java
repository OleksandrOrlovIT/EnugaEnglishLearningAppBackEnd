package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.rule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.rule.Rule;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@DataJpaTest
class RuleRepositoryTest {

    private static final String RULE_NAME = "RULE_NAME";
    private Rule rule;

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setRule(){
        rule = new Rule();
        rule.setRuleName(RULE_NAME);
    }

    @Transactional
    @Test
    void givenNewRule_whenSave_thenRuleSaved() {
        Rule savedRule = ruleRepository.save(rule);
        assertThat(entityManager.find(Rule.class, savedRule.getId())).isEqualTo(rule);
    }

    @Transactional
    @Test
    void givenRuleCreated_whenUpdate_thenSuccess() {
        entityManager.persist(rule);
        String newName = "NEW NAME " + rule.getRuleName();
        rule.setRuleName(newName);
        ruleRepository.save(rule);
        assertThat(entityManager.find(Rule.class, rule.getId()).getRuleName()).isEqualTo(newName);
    }

    @Transactional
    @Test
    void givenRuleCreated_whenFindById_thenSuccess() {
        entityManager.persist(rule);
        Optional<Rule> retrievedRule = ruleRepository.findById(rule.getId());
        assertThat(retrievedRule).contains(rule);
    }

    @Transactional
    @Test
    void givenRuleCreated_whenDelete_thenSuccess() {
        entityManager.persist(rule);
        ruleRepository.delete(rule);
        assertThat(entityManager.find(Rule.class, rule.getId())).isNull();
    }

    @Transactional
    @Test
    void givenTwoCreatedRules_whenFindAll_thenSuccess() {
        entityManager.persist(rule);

        Rule rule2 = new Rule();
        rule2.setRuleName("RULE_NAME_2");
        entityManager.persist(rule2);

        List<Rule> rules = ruleRepository.findAll();

        assertEquals(2, rules.size());
        assertTrue(rules.contains(rule));
        assertTrue(rules.contains(rule2));
    }
}