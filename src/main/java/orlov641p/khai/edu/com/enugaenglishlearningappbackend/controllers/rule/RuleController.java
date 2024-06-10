package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.rule;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.rule.Rule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.rule.RuleService;

import java.util.List;
import java.net.URI;

@RestController()
@RequestMapping("/v1")
public class RuleController {
    private final RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @GetMapping("/rules")
    public List<Rule> retrieveRules(){
        return ruleService.findAll();
    }

    @GetMapping("/rule/{id}")
    public Rule retrieveRuleById(@PathVariable Long id){
        return ruleService.findById(id);
    }

    @PostMapping("/rule")
    public ResponseEntity<Rule> createRule(@RequestBody Rule rule){
        Rule savedRule = ruleService.create(rule);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRule.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedRule);
    }

    @PutMapping("/rule/{id}")
    public Rule updateRule(@PathVariable Long id, @RequestBody Rule rule){
        if(ruleService.findById(id) == null) {
            return null;
        }

        return ruleService.update(rule);
    }

    @DeleteMapping("/rule/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id){
        ruleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
