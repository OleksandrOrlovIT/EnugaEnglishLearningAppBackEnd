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
        return ruleRepository.findById(id).orElse(null);
    }

    @Override
    public Rule save(Rule rule) {
        if (rule == null) {
            return null;
        }
        return ruleRepository.save(rule);
    }

    @Override
    public void delete(Rule rule) {
        ruleRepository.delete(rule);
    }

    @Override
    public void deleteById(Long id) {
        ruleRepository.deleteById(id);
    }
}

