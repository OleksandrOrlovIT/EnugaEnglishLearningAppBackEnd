package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Rule;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the RuleServiceImpl class.
 */

@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
class RuleServiceImplTest {

    private static final String RULE_NAME = "RuleName";
    private static final String DESCRIPTION = "Description";
    private static final long ID = 1L;

    private static Rule validRule;

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
    @Order(1)
    void create_NullRule(){
        assertThrows(IllegalArgumentException.class, () -> ruleService.create(null));
    }

    @Test
    @Order(2)
    void create_ValidRule(){
        Rule rule = ruleService.create(validRule);

        assertEquals(rule, validRule);
    }

    @Test
    @Order(3)
    void findById_InvalidId(){
        assertThrows(IllegalArgumentException.class, () -> ruleService.findById(null));
    }

    @Test
    @Order(4)
    void findById_ValidId(){
        Rule rule = ruleService.findById(validRule.getId());

        assertEquals(rule, validRule);
    }

    @Test
    @Order(5)
    void update_Null(){
        assertThrows(IllegalArgumentException.class, () -> ruleService.update(null));
    }

    @Test
    @Order(6)
    void update_RuleWithNullId(){
        assertThrows(IllegalArgumentException.class, () -> ruleService.update(Rule.builder().id(null).build()));
    }

    @Test
    @Order(7)
    void update_ruleWithWrongId(){
        Rule rule = Rule.builder().id(validRule.getId() + 100L).build();
        assertThrows(IllegalArgumentException.class, () -> ruleService.update(rule));
    }

    @Test
    @Order(8)
    void update_validRule(){
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
    @Order(9)
    void findAll(){
        List<Rule> rules = new java.util.ArrayList<>(List.of(validRule));
        assertEquals(rules, ruleService.findAll());

        Rule rule = Rule.builder().id(ID + 1).build();
        rules.add(rule);
        ruleService.create(rule);

        assertEquals(rules, ruleService.findAll());
    }

    @Test
    @Order(10)
    void delete_null(){
        assertThrows(IllegalArgumentException.class, () -> ruleService.delete(null));
    }

    @Test
    @Order(11)
    void delete_nonExistingRule(){
        assertDoesNotThrow(() -> ruleService.delete(Rule.builder().build()));
    }

    @Test
    @Order(12)
    void delete_ExistingRule(){
        Long savedId = validRule.getId();
        assertDoesNotThrow(() -> ruleService.delete(validRule));

        assertThrows(IllegalArgumentException.class, () -> ruleService.findById(savedId));
    }

    @Test
    @Order(13)
    void deleteById_null(){
        assertThrows(IllegalArgumentException.class, () -> ruleService.deleteById(null));
    }

    @Test
    @Order(14)
    void deleteById_nonExistingRuleId(){
        ruleService.deleteById(100000L);

        assertDoesNotThrow(() -> ruleService.findById(ID + 1));
    }

    @Test
    @Order(15)
    void deleteById_validRule(){
        ruleService.deleteById(ID + 1);

        assertThrows(IllegalArgumentException.class, () -> ruleService.findById(ID + 1));
    }
}