package orlov641p.khai.edu.com.enugaenglishlearningappbackend.models;

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
        rule = new Rule();
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
}