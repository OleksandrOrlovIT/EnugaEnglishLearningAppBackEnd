package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.rule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A RuleTest created to check Rule class Methods.
 */


class RuleTest {

    private static Rule rule;

    private static final String RULE_NAME = "RuleName";
    private static final String DESCRIPTION = "Description";

    @BeforeEach
    void setUp() {
        rule = Rule.builder().id(1L).build();
    }

    @Test
    void getSetRuleName() {
        assertNull(rule.getRuleName());

        rule.setRuleName(RULE_NAME);

        assertEquals(RULE_NAME, rule.getRuleName());
    }

    @Test
    void getSetDescription() {
        assertNull(rule.getDescription());

        rule.setDescription(DESCRIPTION);

        assertEquals(DESCRIPTION, rule.getDescription());
    }

    @Test
    void builder() {
        assertNull(rule.getRuleName());
        assertNull(rule.getDescription());

        rule = Rule
                .builder()
                .ruleName(RULE_NAME)
                .description(DESCRIPTION)
                .build();

        assertEquals(RULE_NAME, rule.getRuleName());
        assertEquals(DESCRIPTION, rule.getDescription());
    }

    @Test
    void hashCodeAndEquals_Id() {
        Rule secondRule = Rule.builder().id(2L).build();

        assertNotEquals(rule, secondRule);
        assertNotEquals(rule.hashCode(), secondRule.hashCode());

        secondRule.setId(1L);

        assertEquals(rule, secondRule);
        assertEquals(rule.hashCode(), secondRule.hashCode());
    }

    @Test
    void hashCodeAndEquals_ruleName() {
        Rule secondRule = Rule.builder().ruleName("Different Rule Name").build();
        secondRule.setId(rule.getId());

        assertNotEquals(rule, secondRule);
        assertNotEquals(rule.hashCode(), secondRule.hashCode());

        secondRule.setRuleName(rule.getRuleName());

        assertEquals(rule, secondRule);
        assertEquals(rule.hashCode(), secondRule.hashCode());
    }

    @Test
    void hashCodeAndEquals_description() {
        Rule secondRule = Rule.builder().ruleName(RULE_NAME).description("DiffDescription").build();
        secondRule.setId(rule.getId());

        assertNotEquals(rule, secondRule);
        assertNotEquals(rule.hashCode(), secondRule.hashCode());

        secondRule.setRuleName(rule.getRuleName());
        secondRule.setDescription(rule.getDescription());

        assertEquals(rule, secondRule);
        assertEquals(rule.hashCode(), secondRule.hashCode());
    }

    @Test
    void ruleToString(){
        rule.setRuleName(RULE_NAME);
        rule.setDescription(DESCRIPTION);

        String expected = "Rule{id=1, ruleName='RuleName', description='Description'}";
        assertEquals(expected, rule.toString());
    }

    @Test
    void ruleBuilderToString(){
        Rule.RuleBuilder ruleBuilder = Rule.builder()
                .id(2L)
                .ruleName(RULE_NAME)
                .description(DESCRIPTION);

        String expected = "Rule.RuleBuilder(id=2, ruleName=RuleName, description=Description)";
        assertEquals(expected, ruleBuilder.toString());
    }
}