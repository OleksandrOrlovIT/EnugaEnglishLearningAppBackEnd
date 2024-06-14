package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.request.WordModuleAttemptRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.wordmodule.WordModuleAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.CustomPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.wordmodule.WordModuleRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.testattempt.wordmodule.WordModuleAttemptService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule.CustomPairService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule.WordModuleService;

import java.util.*;

@AllArgsConstructor
@Service
@Transactional
public class WordModuleServiceImpl implements WordModuleService {

    private final WordModuleRepository wordModuleRepository;
    private final CustomPairService customPairService;
    private final UserService userService;
    private final WordModuleAttemptService wordModuleAttemptService;

    @Override
    public List<WordModule> findAll() {
        return wordModuleRepository.findAll();
    }

    @Override
    public WordModule findById(Long id) {
        Objects.requireNonNull(id, "WordModule id cannot be null");

        WordModule wordModule = wordModuleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Word Module with id = " + id + " doesn't exist"));
        wordModule.setCustomPairs(customPairService.getCustomPairsByWordModuleId(wordModule.getId()));

        return wordModule;
    }

    @Override
    public WordModule create(WordModule wordModule) {
        validateWordModuleForCreation(wordModule);

        wordModule = wordModuleRepository.save(wordModule);

        if(wordModule.getCustomPairs() != null){
            for(CustomPair customPair : wordModule.getCustomPairs()){
                customPair.setWordModule(wordModule);
            }

            List<CustomPair> savedCustomPairs = customPairService.createAll(wordModule.getCustomPairs());

            wordModule.setCustomPairs(savedCustomPairs);
        }

        return wordModule;
    }

    @Override
    public WordModule update(WordModule wordModule) {
        validateWordModuleForUpdate(wordModule);

        WordModule oldWordModule = findById(wordModule.getId());

        if(oldWordModule.getCustomPairs() != null && !oldWordModule.getCustomPairs().isEmpty()) {
            removeDeletedCustomPairs(wordModule, oldWordModule);
        }

        if(wordModule.getCustomPairs() != null && !wordModule.getCustomPairs().isEmpty()) {
            updateOrAddCustomPairs(wordModule);
        }

        return wordModuleRepository.save(wordModule);
    }

    private void removeDeletedCustomPairs(WordModule updatedWordModule, WordModule oldWordModule) {
        List<CustomPair> oldCustomPairs = oldWordModule.getCustomPairs();
        oldCustomPairs.removeAll(updatedWordModule.getCustomPairs());

        for (CustomPair customPair : oldCustomPairs) {
            customPairService.deleteById(customPair.getId());
        }
    }

    private void updateOrAddCustomPairs(WordModule wordModule) {
        List<CustomPair> savedCustomPairs = new ArrayList<>();
        for(CustomPair customPair : wordModule.getCustomPairs()){
            ensureCustomPairBelongsToWordModule(customPair, wordModule);

            customPair.setWordModule(wordModule);

            if(customPair.getId() != null && customPair.equals(customPairService.findById(customPair.getId()))){
                customPair = customPairService.update(customPair);
            } else {
                customPair.setId(null);
                customPair = customPairService.create(customPair);
            }
            savedCustomPairs.add(customPair);
        }
        wordModule.setCustomPairs(savedCustomPairs);
    }

    private void ensureCustomPairBelongsToWordModule(CustomPair customPair, WordModule wordModule) {
        if (customPair.getWordModule() != null && !customPair.getWordModule().getId().equals(wordModule.getId())) {
            throw new IllegalArgumentException("Word Module custom pair do not match");
        }
    }

    @Override
    public void delete(WordModule wordModule) {
        Objects.requireNonNull(wordModule, "WordModule cannot be null");
        wordModuleRepository.delete(wordModule);
    }

    @Override
    public void deleteById(Long id) {
        Objects.requireNonNull(id, "Word Module id cannot be null");
        wordModuleRepository.deleteById(id);
    }

    @Override
    public WordModule getFirst() {
        Pageable pageable = Pageable
                .ofSize(1)
                .first();

        Page<WordModule> wordModules = wordModuleRepository.findAll(pageable);

        return wordModules.hasContent() ? wordModules.getContent().get(0) : null;
    }

    @Override
    public WordModuleAttempt takeTheTest(WordModuleAttemptRequest wordModuleAttemptRequest) {
        WordModule wordModule = findById(wordModuleAttemptRequest.getWordModuleId());
        User user = userService.findById(wordModuleAttemptRequest.getUserId());

        Map<Long, String> answers = wordModuleAttemptRequest.getAnswers();

        validateWordModuleNotNull(wordModule);
        validateWordModuleIdNotNull(wordModule.getId());

        List<CustomPair> customPairs = customPairService.getCustomPairsByWordModuleId(wordModule.getId());

        Map<Long, String> wrongAnswers = new HashMap<>();
        int rightAnswersCount = 0;

        for(CustomPair customPair : customPairs){
            Long customPairId = customPair.getId();
            if(!answers.containsKey(customPairId)){
                throw new IllegalArgumentException("Answers don't have an answer for custom pair question");
            }

            if(answers.get(customPairId).equals(customPair.getTranslation())){
                rightAnswersCount++;
            } else {
                wrongAnswers.put(customPairId, answers.get(customPairId));
            }
        }

        WordModuleAttempt wordModuleAttempt = WordModuleAttempt.builder()
                .wordModule(wordModule)
                .user(user)
                .rightAnswers(rightAnswersCount)
                .wrongAnswers(wrongAnswers)
                .build();

        return wordModuleAttemptService.create(wordModuleAttempt);
    }

    private void validateWordModuleNotNull(WordModule wordModule) {
        Objects.requireNonNull(wordModule, "WordModule cannot be null");
    }

    private void validateWordModuleIdNotNull(Long id){
        Objects.requireNonNull(id, "WordModule ID must be provided for update");
    }

    private void validateWordModuleForCreation(WordModule wordModule) {
        validateWordModuleNotNull(wordModule);
        if (wordModule.getId() != null) {
            throw new IllegalArgumentException("WordModule ID should not be provided for creation");
        }
    }

    private void validateWordModuleForUpdate(WordModule wordModule) {
        validateWordModuleNotNull(wordModule);
        validateWordModuleIdNotNull(wordModule.getId());
        if (!wordModuleRepository.existsById(wordModule.getId())) {
            throw new IllegalArgumentException("WordModule with id = " + wordModule.getId() + " does not exist");
        }
    }
}