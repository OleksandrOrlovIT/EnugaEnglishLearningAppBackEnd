package orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.wordmodule.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.wordmodule.WordModuleLoader;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.CustomPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.enums.Visibility;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule.WordModuleService;

import java.util.ArrayList;
import java.util.List;

@Profile("!test")
@Slf4j
@AllArgsConstructor
@Component
public class WordModuleLoaderImpl implements WordModuleLoader {
    private final WordModuleService wordModuleService;
    private final UserService userService;

    @Override
    public void loadWordModules() {
        if (wordModuleService.getFirst() == null) {
            List<User> users = userService.getUserPage(PageRequest.of(0, 5)).getContent();

            loadPublicWordModules(users);

            loadPrivateWordModule(users.get(0));
            log.info("Word Modules were loaded");
        } else {
            log.info("Word Modules loading were skipped");
        }
    }

    private void loadPublicWordModules(List<User> users) {
        loadPublicWordModule1(users.get(0));
        loadPublicWordModule2(users.get(0));
        createWordModuleColors(users.get(1));
        createWordModuleFruits(users.get(1));
        createWordModuleNumbers(users.get(2));
        createWordModuleDaysOfWeek(users.get(3));
        createWordModuleMonthsOfYear(users.get(4));
        createWordModuleFamilyMembers(users.get(4));
    }

    private void createWordModuleFamilyMembers(User user) {
        String wordModuleName = "Family Members";

        List<String> words = new ArrayList<>();
        List<String> translations = new ArrayList<>();

        words.add("father");
        translations.add("батько");

        words.add("mother");
        translations.add("мати");

        words.add("brother");
        translations.add("брат");

        words.add("sister");
        translations.add("сестра");

        words.add("grandfather");
        translations.add("дідусь");

        createWordModule(wordModuleName, words, translations, user);
    }

    private void createWordModuleMonthsOfYear(User user) {
        String wordModuleName = "Months of the Year";

        List<String> words = new ArrayList<>();
        List<String> translations = new ArrayList<>();

        words.add("January");
        translations.add("січень");

        words.add("February");
        translations.add("лютий");

        words.add("March");
        translations.add("березень");

        words.add("April");
        translations.add("квітень");

        words.add("May");
        translations.add("травень");

        createWordModule(wordModuleName, words, translations, user);
    }

    private void createWordModuleDaysOfWeek(User user) {
        String wordModuleName = "Days of the Week";

        List<String> words = new ArrayList<>();
        List<String> translations = new ArrayList<>();

        words.add("Monday");
        translations.add("понеділок");

        words.add("Tuesday");
        translations.add("вівторок");

        words.add("Wednesday");
        translations.add("середа");

        words.add("Thursday");
        translations.add("четвер");

        words.add("Friday");
        translations.add("п'ятниця");

        createWordModule(wordModuleName, words, translations, user);
    }

    private void createWordModuleNumbers(User user) {
        String wordModuleName = "Numbers";

        List<String> words = new ArrayList<>();
        List<String> translations = new ArrayList<>();

        words.add("one");
        translations.add("один");

        words.add("two");
        translations.add("два");

        words.add("three");
        translations.add("три");

        words.add("four");
        translations.add("чотири");

        words.add("five");
        translations.add("п'ять");

        createWordModule(wordModuleName, words, translations, user);
    }

    private void createWordModuleFruits(User user) {
        String wordModuleName = "Fruits";

        List<String> words = new ArrayList<>();
        List<String> translations = new ArrayList<>();

        words.add("apple");
        translations.add("яблуко");

        words.add("banana");
        translations.add("банан");

        words.add("orange");
        translations.add("апельсин");

        words.add("grape");
        translations.add("виноград");

        words.add("pear");
        translations.add("груша");

        createWordModule(wordModuleName, words, translations, user);
    }

    private void createWordModuleColors(User user) {
        String wordModuleName = "Colors";

        List<String> words = new ArrayList<>();
        List<String> translations = new ArrayList<>();

        words.add("blue");
        translations.add("синій");

        words.add("red");
        translations.add("червоний");

        words.add("black");
        translations.add("чорний");

        words.add("white");
        translations.add("білий");

        words.add("pink");
        translations.add("рожевий");

        createWordModule(wordModuleName, words, translations, user);
    }

    private void createWordModule(String wordModuleName, List<String> words, List<String> translations, User user) {
        List<CustomPair> customPairs1 = new ArrayList<>();

        for (int i = 0; i < words.size(); i++) {
            customPairs1.add(CustomPair.builder()
                    .word(words.get(i))
                    .translation(translations.get(i))
                    .build());
        }

        wordModuleService.create(WordModule.builder()
                .moduleName(wordModuleName)
                .visibility(Visibility.PUBLIC)
                .user(user)
                .customPairs(customPairs1)
                .build());
    }

    public void loadPublicWordModule1(User user) {
        CustomPair customPair1 = new CustomPair("sister", "сестра");
        CustomPair customPair2 = new CustomPair("brother", "брат");
        CustomPair customPair3 = new CustomPair("home", "дім");
        CustomPair customPair4 = new CustomPair("one", "один");
        CustomPair customPair5 = new CustomPair("two", "два");

        List<CustomPair> customPairs = List.of(customPair1, customPair2, customPair3, customPair4, customPair5);

        WordModule wordModule = WordModule.builder()
                .moduleName("My first public module")
                .visibility(Visibility.PUBLIC)
                .user(user)
                .customPairs(customPairs)
                .build();

        wordModuleService.create(wordModule);
    }

    public void loadPublicWordModule2(User user) {
        CustomPair customPair1 = new CustomPair("red", "червоний");
        CustomPair customPair2 = new CustomPair("blue", "синій");
        CustomPair customPair3 = new CustomPair("white", "білий");

        List<CustomPair> customPairs = List.of(customPair1, customPair2, customPair3);

        WordModule wordModule = WordModule.builder()
                .moduleName("My second public module")
                .visibility(Visibility.PUBLIC)
                .user(user)
                .customPairs(customPairs)
                .build();

        wordModuleService.create(wordModule);
    }

    public void loadPrivateWordModule(User user) {
        CustomPair customPair1 = new CustomPair("apple", "яблуко");
        CustomPair customPair2 = new CustomPair("banana", "банан");
        CustomPair customPair3 = new CustomPair("coconut", "кокос");
        CustomPair customPair4 = new CustomPair("three", "три");
        CustomPair customPair5 = new CustomPair("ten", "десять");

        List<CustomPair> customPairs = List.of(customPair1, customPair2, customPair3, customPair4, customPair5);

        WordModule wordModule = WordModule.builder()
                .moduleName("My first private module")
                .visibility(Visibility.PRIVATE)
                .user(user)
                .customPairs(customPairs)
                .build();

        wordModuleService.create(wordModule);
    }
}
