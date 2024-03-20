package orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Rule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.RuleService;

@Component
public class InitialBootstrap implements CommandLineRunner {

    private final RuleService ruleService;

    public InitialBootstrap(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @Override
    public void run(String... args) throws Exception {
        loadRules();
    }

    private void loadRules(){
        Rule presentSimple = Rule.builder()
                .ruleName("Present Simple")
                .description("The present simple tense is a grammatical form used to describe actions or states that" +
                        " are habitual, regular, or timeless. It's often employed to convey facts, general truths," +
                        " routines, or actions that occur regularly. In English, it's formed by using the base form of" +
                        " the verb for most subjects, but with the addition of 's' or 'es' for third-person singular" +
                        " subjects (he, she, it). For example, \"She eats breakfast every morning\" or \"The sun rises" +
                        " in the east.\" It's also used in certain cases to describe scheduled events in the future, as" +
                        " in \"The train leaves at 10:00 tomorrow.\"\n ©ChatGPT OpenAI")
                .build();

        Rule presentContinuous = Rule.builder()
                .ruleName("Present continuous")
                .description("The present continuous tense, also known as the present progressive tense," +
                        " is a grammatical form used to describe actions that are happening right now," +
                        " at the moment of speaking, or actions that are ongoing but not necessarily happening" +
                        " at the moment of speaking. It's formed by using the present tense of the verb \"to be\"" +
                        " (am, is, are) followed by the present participle (-ing form) of the main verb. For example," +
                        " \"She is reading a book\" describes an action happening at the moment of speaking, while" +
                        " \"They are playing soccer\" describes an ongoing action. This tense can also be used to" +
                        " describe future arrangements or plans, especially when they are fixed or definite." +
                        "\n ©ChatGPT OpenAI")
                .build();

        ruleService.save(presentSimple);
        ruleService.save(presentContinuous);
    }
}
