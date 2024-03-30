package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Rule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.RuleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Test class for the RuleServiceImpl class.
 */

@ExtendWith(MockitoExtension.class)
class RuleServiceImplTest {

    private static final String RULE_NAME = "RuleName";
    private static final String DESCRIPTION = "Description";
    private static final long ID = 1L;

    private Rule returnRule;

    @Mock
    RuleRepository ruleRepository;

    @InjectMocks
    RuleServiceImpl ruleService;

    @BeforeEach
    void setUp() {
        returnRule = Rule
                .builder()
                .id(ID)
                .ruleName(RULE_NAME)
                .description(DESCRIPTION)
                .build();
    }

    @Test
    void findAll() {
        List<Rule> rules = new ArrayList<>();
        rules.add(Rule.builder().id(2L).build());
        rules.add(Rule.builder().id(3L).build());

        when(ruleRepository.findAll()).thenReturn(rules);

        List<Rule> returnedRules = ruleRepository.findAll();

        assertNotNull(returnedRules);
        assertEquals(rules, returnedRules);
    }

    @Test
    void findById() {
        when(ruleRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnRule));

        Rule rule = ruleService.findById(ID);

        assertNotNull(rule);
    }

    @Test
    void findByIdNotFound() {
        when(ruleRepository.findById(anyLong())).thenReturn(Optional.empty());

        Rule rule = ruleService.findById(ID);

        assertNull(rule);
    }

    @Test
    void save() {
        when(ruleRepository.save(any())).thenReturn(returnRule);

        Rule rule = ruleService.save(Rule.builder().id(1L).build());

        assertNotNull(rule);
        verify(ruleRepository, times(1)).save(any());
    }

    @Test
    void delete() {
        ruleService.delete(returnRule);

        verify(ruleRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ruleService.deleteById(returnRule.getId());

        verify(ruleRepository, times(1)).deleteById(anyLong());
    }
}