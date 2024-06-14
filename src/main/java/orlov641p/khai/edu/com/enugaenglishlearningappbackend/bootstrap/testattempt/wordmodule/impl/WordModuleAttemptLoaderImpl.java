package orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.testattempt.wordmodule.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.testattempt.wordmodule.WordModuleAttemptLoader;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.wordmodule.WordModuleAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.CustomPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.testattempt.wordmodule.WordModuleAttemptService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule.CustomPairService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule.WordModuleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Profile("!test")
@Slf4j
@AllArgsConstructor
@Component
public class WordModuleAttemptLoaderImpl implements WordModuleAttemptLoader {

    private final WordModuleAttemptService wordModuleAttemptService;
    private final WordModuleService wordModuleService;
    private final CustomPairService customPairService;
    private final UserService userService;

    @Override
    public void loadWordModuleAttempts() {
        if (wordModuleAttemptService.getFirst() == null) {
            saveTestAttempts();
            log.info("WordModuleAttempts were loaded");
        } else {
            log.info("WordModuleAttempts loading were skipped");
        }
    }

    private void saveTestAttempts() {
        User firstUser = userService.getUserByEmail("test1@email.com");
        WordModule wordModule = wordModuleService.getFirst();

        List<CustomPair> customPairs = customPairService.getCustomPairsByWordModuleId(wordModule.getId());

        Map<Long, String> wrongAnswers1 = new HashMap<>();
        for(CustomPair customPair : customPairs) {
            wrongAnswers1.put(customPair.getId(), customPair.getTranslation() + "asd");
        }

        Map<Long, String> wrongAnswers2 = new HashMap<>(wrongAnswers1);
        Map<Long, String> wrongAnswers3 = new HashMap<>(wrongAnswers1);

        WordModuleAttempt wordModuleAttempt1 = WordModuleAttempt.builder()
                .wordModule(wordModule)
                .user(firstUser)
                .rightAnswers(customPairs.size())
                .build();

        WordModuleAttempt wordModuleAttempt2 = WordModuleAttempt.builder()
                .wordModule(wordModule)
                .user(firstUser)
                .wrongAnswers(wrongAnswers2)
                .build();

        int rightAnswers = 0;
        for (int i = 0; i < customPairs.size() / 2; i++) {
            wrongAnswers3.remove(customPairs.get(i).getId());
            rightAnswers++;
        }

        WordModuleAttempt wordModuleAttempt3 = WordModuleAttempt.builder()
                .wordModule(wordModule)
                .user(firstUser)
                .rightAnswers(rightAnswers)
                .wrongAnswers(wrongAnswers3)
                .build();

        wordModuleAttemptService.create(wordModuleAttempt1);
        wordModuleAttemptService.create(wordModuleAttempt2);
        wordModuleAttemptService.create(wordModuleAttempt3);
    }

}