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
            saveWordModuleAttempts();
            log.info("WordModuleAttempts were loaded");
        } else {
            log.info("WordModuleAttempts loading were skipped");
        }
    }

    private void saveWordModuleAttempts() {
        User admin = userService.getUserByEmail("admin@admin.com");

        List<WordModule> wordModules = wordModuleService.findByVisibilityPublicAndUserNot(admin.getId());

        WordModule wordModule1 = wordModules.get(0);
        List<CustomPair> customPairs1 = customPairService.getCustomPairsByWordModuleId(wordModule1.getId());
        Map<Integer, Map<Long, String>> cache1 = cacheWrongAnswers(customPairs1);

        WordModule wordModule2 = wordModules.get(1);
        List<CustomPair> customPairs2 = customPairService.getCustomPairsByWordModuleId(wordModule2.getId());
        Map<Integer, Map<Long, String>> cache2 = cacheWrongAnswers(customPairs2);

        int times1 = 1, times2 = 3;

        for (User user : userService.findAll()) {
            if (times1 == 4) {
                times1 = 1;
            }
            if (times2 == 4) {
                times2 = 1;
            }

            loadWordModuleAttempt(wordModule1, user, times1, customPairs1, cache1);
            loadWordModuleAttempt(wordModule2, user, times2, customPairs2, cache2);
            times1++;
            times2++;
        }
    }

    private void loadWordModuleAttempt(WordModule wordModule, User user, int makeAttempts, List<CustomPair> customPairs,
                                       Map<Integer, Map<Long, String>> cache) {

        saveWordModuleAttempt(wordModule, user, customPairs.size() - cache.get(1).size(), cache.get(1));

        if(makeAttempts >= 2){
            saveWordModuleAttempt(wordModule, user, customPairs.size() - cache.get(2).size(), cache.get(2));

            if(makeAttempts == 3){
                saveWordModuleAttempt(wordModule, user, customPairs.size() - cache.get(3).size(), cache.get(3));
            }
        }
    }

    private void saveWordModuleAttempt(WordModule wordModule, User user, int rightAnswers, Map<Long, String> wrongAnswers) {
        WordModuleAttempt wordModuleAttempt = WordModuleAttempt.builder()
                .wordModule(wordModule)
                .user(user)
                .rightAnswers(rightAnswers)
                .wrongAnswers(wrongAnswers)
                .build();

        wordModuleAttemptService.create(wordModuleAttempt);
    }

    private Map<Integer, Map<Long, String>> cacheWrongAnswers(List<CustomPair> customPairs) {
        Map<Integer, Map<Long, String>> res = new HashMap<>();

        Map<Long, String> wrongAnswers1 = new HashMap<>();

        for (CustomPair customPair : customPairs) {
            wrongAnswers1.put(customPair.getId(), customPair.getTranslation() + "asd");
        }

        res.put(1, wrongAnswers1);
        res.put(2, new HashMap<>());

        Map<Long, String> wrongAnswers3 = new HashMap<>(wrongAnswers1);

        for (int i = 0; i < customPairs.size() / 2; i++) {
            wrongAnswers3.remove(customPairs.get(i).getId());
        }

        res.put(3, wrongAnswers3);

        return res;
    }

}