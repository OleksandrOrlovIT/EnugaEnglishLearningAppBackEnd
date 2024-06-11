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
import java.util.*;
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

        Set<String> englishWords = new HashSet<>();
        Set<String> ukrainianWords = new HashSet<>();

        populateSets(path, englishWords, ukrainianWords);

        Cache<String, EnglishWord> englishWordCache = CacheBuilder.newBuilder()
                .maximumSize(80000)
                .build();

        Cache<String, UkrainianWord> ukrainianWordCache = CacheBuilder.newBuilder()
                .maximumSize(80000)
                .build();

        populateEnglishCache(englishWords, englishWordCache);
        populateUkrainianCache(ukrainianWords, ukrainianWordCache);

        populateTranslationPairs(path, englishWordCache, ukrainianWordCache);
    }

    private void populateSets(Path path, Set<String> englishWords, Set<String> ukrainianWords) {
        AtomicInteger counter = new AtomicInteger(0);
        Instant start = Instant.now();
        try (Stream<String> lines = Files.lines(path)) {

            lines.forEach(line -> {
                try {
                    counter.incrementAndGet();
                    addWordsFromLine(line, englishWords, ukrainianWords);
                    if (counter.get() % 5000 == 0) {
                        logExecutionTime(start, counter.get(),
                                "Populating sets. Processed {} lines. Time elapsed: {} ms"
                        );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        logExecutionTime(start, counter.get(),
                "Populating sets. Processed {} lines. Time elapsed: {} ms"
        );
    }

    private void addWordsFromLine(String line, Set<String> englishWords, Set<String> ukrainianWords) {
        int firstSpace = line.indexOf(" ");
        String englishWordStr = line.substring(0, firstSpace);
        String ukrainianWordStr = line.substring(firstSpace + 1);

        englishWords.add(englishWordStr);
        ukrainianWords.add(ukrainianWordStr);
    }

    private void populateEnglishCache(Set<String> englishWords, Cache<String, EnglishWord> englishWordCache) {
        List<String> wordList = new ArrayList<>(englishWords);
        Instant start = Instant.now();
        int size = wordList.size();

        for (int i = 0; i < size; i += 1000) {
            int end = Math.min(i + 1000, size);
            List<EnglishWord> batch = new ArrayList<>();
            for (int j = i; j < end; j++) {
                batch.add(new EnglishWord(wordList.get(j)));
            }

            List<EnglishWord> englishWordsBatch = englishWordService.createAll(batch);

            englishWordsBatch.forEach(word -> englishWordCache.put(word.getWord(), word));

            if (i % 5000 == 0) {
                logExecutionTime(start, i, "Populating english cache. Processed {} objects. Time elapsed: {} ms");
            }
        }

        logExecutionTime(start, size, "Populating english Cache. Processed {} objects. Time elapsed: {} ms");
    }

    private void populateUkrainianCache(Set<String> ukrainianWords, Cache<String, UkrainianWord> ukrainianWordCache) {
        List<String> wordList = new ArrayList<>(ukrainianWords);
        Instant start = Instant.now();
        int size = wordList.size();

        for (int i = 0; i < size; i += 1000) {
            int end = Math.min(i + 1000, size);
            List<UkrainianWord> batch = new ArrayList<>();
            for (int j = i; j < end; j++) {
                batch.add(new UkrainianWord(wordList.get(j)));
            }

            List<UkrainianWord> ukrainianWordsBatch = ukrainianWordService.createAll(batch);

            ukrainianWordsBatch.forEach(word -> ukrainianWordCache.put(word.getWord(), word));

            if (i % 5000 == 0) {
                logExecutionTime(start, i, "Populating ukrainian Cache. Processed {} objects. Time elapsed: {} ms");
            }
        }
        logExecutionTime(start, size, "Populating ukrainian Cache. Processed {} objects. Time elapsed: {} ms");
    }

    private void populateTranslationPairs(Path path, Cache<String, EnglishWord> englishWordCache,
                                          Cache<String, UkrainianWord> ukrainianWordCache) {
        AtomicInteger counter = new AtomicInteger(0);
        Instant start = Instant.now();
        try (Stream<String> lines = Files.lines(path)) {

            List<TranslationPair> translationPairs = new ArrayList<>();

            lines.forEach(line -> {
                try {
                    counter.incrementAndGet();
                    int firstSpace = line.indexOf(" ");
                    String englishWordStr = line.substring(0, firstSpace);
                    String ukrainianWordStr = line.substring(firstSpace + 1);
                    translationPairs.add(
                            new TranslationPair(
                                    englishWordCache.getIfPresent(englishWordStr),
                                    ukrainianWordCache.getIfPresent(ukrainianWordStr)
                            ));
                    if (counter.get() % 1000 == 0) {
                        translationPairService.createAll(translationPairs);
                        translationPairs.clear();
                    }
                    if (counter.get() % 5000 == 0) {
                        logExecutionTime(start, counter.get(),
                                "Populating translationPairs. Processed {} lines. Time elapsed: {} ms");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            translationPairService.createAll(translationPairs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logExecutionTime(start, counter.get(),
                "Populating translationPairs. Processed {} lines. Time elapsed: {} ms");
    }

    private void logExecutionTime(Instant start, int times, String message) {
        Duration elapsed = Duration.between(start, Instant.now());
        log.info(message, times, elapsed.toMillis());
    }
}