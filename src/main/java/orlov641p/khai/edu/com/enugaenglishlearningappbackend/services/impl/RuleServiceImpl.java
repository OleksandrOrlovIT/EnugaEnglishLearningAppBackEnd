package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import org.springframework.stereotype.Service;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Rule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.RuleRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.RuleService;

import java.util.List;


/**
 * The RuleServiceImpl class provides implementation for the RuleService interface,
 * managing Rule entities.
 */
@Service
public class RuleServiceImpl implements RuleService {

    private final RuleRepository ruleRepository;

    /**
     * Constructs a new RuleServiceImpl instance with the specified RuleRepository.
     *
     * @param ruleRepository The RuleRepository used for data access.
     */
    public RuleServiceImpl(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }

    @Override
    public Rule findById(Long id) {
        checkRuleIdNull(id);

        Rule rule = ruleRepository.findById(id).orElse(null);

        if(rule == null){
            throw new IllegalArgumentException("Rule with id = " + id + " doesn't exist");
        }

        return rule;
    }

    @Override
    public Rule create(Rule rule) {
        checkRuleNull(rule);

        return ruleRepository.save(rule);
    }

    @Override
    public Rule update(Rule rule) {
        checkRuleNull(rule);

        findById(rule.getId());

        return ruleRepository.save(rule);
    }

    @Override
    public void delete(Rule rule) {
        checkRuleNull(rule);

        ruleRepository.delete(rule);
    }

    @Override
    public void deleteById(Long id) {
        checkRuleIdNull(id);

        ruleRepository.deleteById(id);
    }

    private void checkRuleNull(Rule rule){
        if (rule == null) {
            throw new IllegalArgumentException("Rule can`t be null");
        }
    }

    private void checkRuleIdNull(Long id){
        if(id == null){
            throw new IllegalArgumentException("Rule id can`t be null");
        }
    }
}

