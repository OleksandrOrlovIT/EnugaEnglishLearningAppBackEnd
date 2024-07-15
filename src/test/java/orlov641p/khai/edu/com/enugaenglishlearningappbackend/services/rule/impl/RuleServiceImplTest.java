package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.rule.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.rule.Rule;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the RuleServiceImpl class.
 */

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class RuleServiceImplTest {

    private static final String RULE_NAME = "RuleName";
    private static final String DESCRIPTION = "Description";
    private static final long ID = 1L;

    private static Rule validRule;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    RuleServiceImpl ruleService;

    @BeforeAll
    static void setUp() {
        validRule = Rule
                .builder()
                .id(ID)
                .ruleName(RULE_NAME)
                .description(DESCRIPTION)
                .build();
    }

    @Test
    @Transactional
    void create_NullRule(){
        assertThrows(IllegalArgumentException.class, () -> ruleService.create(null));
    }

    @Test
    @Transactional
    void create_ValidRule(){
        validRule = ruleService.create(validRule);

        assertNotNull(validRule);
    }

    @Test
    @Transactional
    void findById_InvalidId(){
        assertThrows(IllegalArgumentException.class, () -> ruleService.findById(null));
    }

    @Test
    @Transactional
    void findById_ValidId(){
        validRule = ruleService.create(validRule);

        Rule rule = ruleService.findById(validRule.getId());

        assertEquals(rule, validRule);
    }

    @Test
    @Transactional
    void update_Null(){
        assertThrows(IllegalArgumentException.class, () -> ruleService.update(null));
    }

    @Test
    @Transactional
    void update_RuleWithNullId(){
        assertThrows(IllegalArgumentException.class, () -> ruleService.update(Rule.builder().id(null).build()));
    }

    @Test
    @Transactional
    void update_ruleWithWrongId(){
        Rule rule = Rule.builder().id(validRule.getId() + 100L).build();
        assertThrows(EntityNotFoundException.class, () -> ruleService.update(rule));
    }

    @Test
    @Transactional
    void update_validRule(){
        validRule = ruleService.create(validRule);

        String updatedRuleName = RULE_NAME + "Update";
        String updatedDescription = DESCRIPTION + "Update";

        Rule rule = Rule.builder()
                .id(ID)
                .ruleName(updatedRuleName)
                .description(updatedDescription)
                .build();

        validRule = ruleService.update(rule);

        assertEquals(validRule, rule);
    }

    @Test
    @Transactional
    void findAll(){
        validRule = ruleService.create(validRule);

        List<Rule> rules = new java.util.ArrayList<>(List.of(validRule));
        assertEquals(rules, ruleService.findAll());

        Rule rule = Rule.builder().ruleName("SOMERULE").description("SOMETHING").build();
        rules.add(rule);
        ruleService.create(rule);

        assertEquals(rules, ruleService.findAll());
    }

    @Test
    @Transactional
    void delete_null(){
        assertThrows(IllegalArgumentException.class, () -> ruleService.delete(null));
    }

    @Test
    @Transactional
    void delete_nonExistingRule(){
        assertDoesNotThrow(() -> ruleService.delete(Rule.builder().build()));
    }

    @Test
    @Transactional
    void delete_ExistingRule(){
        Long savedId = validRule.getId();
        assertDoesNotThrow(() -> ruleService.delete(validRule));

        assertThrows(EntityNotFoundException.class, () -> ruleService.findById(savedId));
    }

    @Test
    @Transactional
    void deleteById_null(){
        assertThrows(IllegalArgumentException.class, () -> ruleService.deleteById(null));
    }

    @Test
    @Transactional
    void deleteById_nonExistingRuleId(){
        assertDoesNotThrow(() -> ruleService.deleteById(100000L));
    }

    @Test
    @Transactional
    void deleteById_validRule(){
        ruleService.deleteById(ID + 1);

        assertThrows(EntityNotFoundException.class, () -> ruleService.findById(ID + 1));
    }
}