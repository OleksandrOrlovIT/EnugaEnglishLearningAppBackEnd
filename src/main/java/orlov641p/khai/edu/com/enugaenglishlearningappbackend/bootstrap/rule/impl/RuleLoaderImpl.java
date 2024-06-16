package orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.rule.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.rule.RuleLoader;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.rule.Rule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.rule.RuleService;

import java.util.ArrayList;
import java.util.List;

@Profile("!test")
@Slf4j
@AllArgsConstructor
@Component
public class RuleLoaderImpl implements RuleLoader {

    private final RuleService ruleService;

    @Override
    public void loadRules() {
        if (ruleService.getFirst() == null) {
            saveRules();
            log.info("Rules were loaded");
        } else {
            log.info("Rules loading were skipped");
        }
    }

    public void saveRules() {
        List<Rule> rules = new ArrayList<>();
        rules.add(getRule("Present Simple","The present simple tense is a grammatical form used to describe actions or states that" +
                " are habitual, regular, or timeless. It's often employed to convey facts, general truths," +
                " routines, or actions that occur regularly. In English, it's formed by using the base form of" +
                " the verb for most subjects, but with the addition of 's' or 'es' for third-person singular" +
                " subjects (he, she, it). For example, \"She eats breakfast every morning\" or \"The sun rises" +
                " in the east.\" It's also used in certain cases to describe scheduled events in the future, as" +
                " in \"The train leaves at 10:00 tomorrow.\"\n"));

        rules.add(getRule("Present continuous","The present continuous tense, also known as the present progressive tense," +
                " is a grammatical form used to describe actions that are happening right now," +
                " at the moment of speaking, or actions that are ongoing but not necessarily happening" +
                " at the moment of speaking. It's formed by using the present tense of the verb \"to be\"" +
                " (am, is, are) followed by the present participle (-ing form) of the main verb. For example," +
                " \"She is reading a book\" describes an action happening at the moment of speaking, while" +
                " \"They are playing soccer\" describes an ongoing action. This tense can also be used to" +
                " describe future arrangements or plans, especially when they are fixed or definite." +
                "\n"));

        rules.add(getRule("Past Simple", "Used to describe actions that happened at a" +
                " specific time in the past.\nExample: \"She visited the museum yesterday.\""));

        rules.add(getRule("Past Continuous", "Used to describe actions that were in" +
                " progress at a specific time in the past.\n" +
                "Example: \"She was visiting the museum when it started to rain.\""));

        rules.add(getRule("Past Perfect", "Used to describe actions that were" +
                " completed before another action in the past.\n" +
                "\n" +
                "Example: \"She had visited the museum before it closed.\"\n"));

        rules.add(getRule("Past Perfect Continuous", "Used to describe actions that were ongoing in the past up until another point in the past.\n" +
                "\n" +
                "Example: \"She had been visiting the museum for hours before it closed.\""));

        rules.add(getRule("Future Simple", "Used to describe actions that will happen at a specific time in the future.\n" +
                "\n" +
                "Example: \"She will visit the museum tomorrow.\"\n"));

        rules.add(getRule("Future Continuous", "Used to describe actions that will be in progress at a specific time in the future.\n" +
                "\n" +
                "Example: \"She will be visiting the museum at this time tomorrow.\""));

        rules.add(getRule("Future Perfect", " Used to describe actions that will be completed before a specific time in the future.\n" +
                "\n" +
                "Example: \"She will have visited the museum by the time you arrive.\"\n"));

        rules.add(getRule("Future Perfect Continuous", "Used to describe actions that will be ongoing in the future up until a specific point in the future.\n" +
                "\n" +
                "Example: \"She will have been visiting the museum for hours by the time you arrive.\""));

        for(Rule rule : rules){
            ruleService.create(rule);
        }
    }

    private Rule getRule(String ruleName, String description){
        return Rule.builder()
                .ruleName(ruleName)
                .description(description)
                .build();
    }
}
