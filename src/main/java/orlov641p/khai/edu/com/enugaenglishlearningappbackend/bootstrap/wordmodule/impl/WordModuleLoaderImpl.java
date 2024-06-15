package orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.wordmodule.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.wordmodule.WordModuleLoader;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.CustomPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.enums.Visibility;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule.WordModuleService;

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
            User user = userService.getUserByEmail("test1@email.com");

            loadPublicWordModules(user);

            loadPrivateWordModule(user);
            log.info("Word Modules were loaded");
        } else {
            log.info("Word Modules loading were skipped");
        }
    }

    private void loadPublicWordModules(User user){
        loadPublicWordModule1(user);
        loadPublicWordModule2(user);
    }

    public void loadPublicWordModule1(User user){
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

    public void loadPublicWordModule2(User user){
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

    public void loadPrivateWordModule(User user){
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
