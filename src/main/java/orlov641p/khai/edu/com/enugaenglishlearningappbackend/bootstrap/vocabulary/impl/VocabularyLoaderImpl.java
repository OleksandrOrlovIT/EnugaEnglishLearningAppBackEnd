package orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.vocabulary.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.vocabulary.VocabularyLoader;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.EnglishWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.TranslationPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.UkrainianWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.vocabulary.EnglishWordService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.vocabulary.TranslationPairService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.vocabulary.UkrainianWordService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Profile("!test")
@AllArgsConstructor
@Slf4j
@Component
public class VocabularyLoaderImpl implements VocabularyLoader {

    private final UkrainianWordService ukrainianWordService;
    private final EnglishWordService englishWordService;
    private final TranslationPairService translationPairService;

    @Override
    public void run(String... args) throws Exception {

    }

    @Override
    public void loadVocabulary() {
        if (translationPairService.getFirst() == null) {
            loadWordsToDB();
            log.info("Translation pairs were loaded");
        } else {
            log.info("Translation pairs loading were skipped");
        }
    }

    private void loadWordsToDB() {
        Path path = Paths.get("src/main/resources/static/Vocabulary.txt");

        try (Stream<String> lines = Files.lines(path).parallel()) {
            AtomicInteger counter = new AtomicInteger(0);
            Instant start = Instant.now();

            Cache<String, EnglishWord> englishWordCache = CacheBuilder.newBuilder()
                    .maximumSize(80000)
                    .build();

            Cache<String, UkrainianWord> ukrainianWordCache = CacheBuilder.newBuilder()
                    .maximumSize(80000)
                    .build();

            List<TranslationPair> translationPairs = new ArrayList<>();

            lines.forEach(line -> {
                try {
                    counter.incrementAndGet();
                    addWordsFromLine(line, englishWordCache, ukrainianWordCache, translationPairs);
                    if (counter.get() % 5000 == 0) {
                        Duration elapsed = Duration.between(start, Instant.now());
                        System.out.println("Processed " + counter.get() + " lines. Time elapsed: " + elapsed.toMillis() + " ms");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            batchInsertTranslationPairs(translationPairs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addWordsFromLine(String line, Cache<String, EnglishWord> englishWordCache, Cache<String, UkrainianWord> ukrainianWordCache, List<TranslationPair> translationPairs) {
        String[] words = line.split(" ");
        String englishWordStr = words[0];
        String ukrainianWordStr = words[1];

        EnglishWord englishWord = englishWordCache.getIfPresent(englishWordStr);
        if (englishWord == null) {
            EnglishWord foundEnglishWord = englishWordService.findByWord(englishWordStr);
            englishWord = foundEnglishWord != null ? foundEnglishWord : englishWordService.create(EnglishWord.builder().word(englishWordStr).build());
            englishWordCache.put(englishWordStr, englishWord);
        }

        UkrainianWord ukrainianWord = ukrainianWordCache.getIfPresent(ukrainianWordStr);
        if (ukrainianWord == null) {
            UkrainianWord foundUkrainianWord = ukrainianWordService.findByWord(ukrainianWordStr);
            ukrainianWord = foundUkrainianWord != null ? foundUkrainianWord : ukrainianWordService.create(UkrainianWord.builder().word(ukrainianWordStr).build());
            ukrainianWordCache.put(ukrainianWordStr, ukrainianWord);
        }

        TranslationPair translationPair = TranslationPair.builder()
                .englishWord(englishWord)
                .ukrainianWord(ukrainianWord)
                .build();

        synchronized (translationPairs) {
            translationPairs.add(translationPair);
            if (translationPairs.size() >= 1000) {
                batchInsertTranslationPairs(new ArrayList<>(translationPairs));
                translationPairs.clear();
            }
        }
    }

    private void batchInsertTranslationPairs(List<TranslationPair> translationPairs) {
        translationPairService.createAll(translationPairs);
    }
}
