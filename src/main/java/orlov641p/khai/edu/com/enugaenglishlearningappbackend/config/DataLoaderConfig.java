package orlov641p.khai.edu.com.enugaenglishlearningappbackend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.book.BookLoader;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.engtest.EnglishTestLoader;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.rule.RuleLoader;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.user.UserLoader;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.vocabulary.VocabularyLoader;

@Profile("!test")
@Configuration
public class DataLoaderConfig {
    @Bean
    public CommandLineRunner loadData(RuleLoader ruleLoader, EnglishTestLoader englishTestLoader,
                                      BookLoader bookLoader, UserLoader userLoader,
                                      VocabularyLoader vocabularyLoader) {
        return args -> {
            ruleLoader.loadRules();
            englishTestLoader.loadEnglishTests();
            bookLoader.loadBooks();
            userLoader.loadUsers();
            vocabularyLoader.loadVocabulary();
        };
    }
}