package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.testattempt.wordmodule.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.request.WordModuleAttemptPage;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.request.WordModuleAttemptWithoutAnswers;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.wordmodule.WordModuleAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.CustomPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.testattempt.wordmodule.WordModuleAttemptRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.testattempt.wordmodule.WordModuleAttemptService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule.CustomPairService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class WordModuleAttemptServiceImpl implements WordModuleAttemptService {

    private final WordModuleAttemptRepository wordModuleAttemptRepository;
    private final CustomPairService customPairService;
    private final UserService userService;

    @Override
    public List<WordModuleAttempt> findAll() {
        return wordModuleAttemptRepository.findAll();
    }

    @Override
    public WordModuleAttempt findById(Long id) {
        checkWordModuleAttemptIdNull(id);

        return wordModuleAttemptRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WordModuleAttempt with id = " + id + " doesn't exist"));
    }

    @Override
    public WordModuleAttempt create(WordModuleAttempt wordModuleAttempt) {
        checkWordModuleAttemptNull(wordModuleAttempt);

        List<CustomPair> customPairs = getWordModuleCustomPairs(wordModuleAttempt);

        int questionsSize = customPairs == null ? 0 : customPairs.size();

        int rightAnswers = wordModuleAttempt.getRightAnswers() == null ? 0 : wordModuleAttempt.getRightAnswers();

        int wrongAnswersSize = wordModuleAttempt.getWrongAnswers() == null ? 0 : wordModuleAttempt.getWrongAnswers().size();

        if(rightAnswers + wrongAnswersSize != questionsSize) {
            throw new IllegalArgumentException("Wrong amount of rightAnswers and wrongAnswers");
        }

        double successPercentage = !customPairs.isEmpty() ?
                ((double) rightAnswers / questionsSize) * 100 : 0.0;

        wordModuleAttempt.setSuccessPercentage(successPercentage);

        if (wordModuleAttempt.getAttemptDate() == null) {
            ZoneId kievZone = ZoneId.of("Europe/Kiev");
            wordModuleAttempt.setAttemptDate(ZonedDateTime.now(kievZone).toLocalDateTime());
        }

        return wordModuleAttemptRepository.save(wordModuleAttempt);
    }

    @Override
    public WordModuleAttempt update(WordModuleAttempt wordModuleAttempt) {
        checkWordModuleAttemptNull(wordModuleAttempt);

        findById(wordModuleAttempt.getId());

        return wordModuleAttemptRepository.save(wordModuleAttempt);
    }

    @Override
    public void delete(WordModuleAttempt wordModuleAttempt) {
        checkWordModuleAttemptNull(wordModuleAttempt);

        wordModuleAttemptRepository.delete(wordModuleAttempt);
    }

    @Override
    public void deleteById(Long id) {
        checkWordModuleAttemptIdNull(id);

        wordModuleAttemptRepository.deleteById(id);
    }

    @Override
    public WordModuleAttempt getFirst() {
        Pageable pageable = Pageable
                .ofSize(1)
                .first();

        Page<WordModuleAttempt> wordModuleAttempts = wordModuleAttemptRepository.findAll(pageable);

        return wordModuleAttempts.hasContent() ? wordModuleAttempts.getContent().get(0) : null;
    }

    @Override
    public List<WordModuleAttempt> findWordModuleAttemptsByUser(User user) {
        return wordModuleAttemptRepository.findByUser(user);
    }

    @Override
    public Page<WordModuleAttempt> findWordModuleAttemptsPageByUser(WordModuleAttemptPage wordModuleAttemptPage) {
        User user = userService.findById(wordModuleAttemptPage.getUserId());

        Pageable pageable = Pageable
                .ofSize(wordModuleAttemptPage.getPageSize())
                .withPage(wordModuleAttemptPage.getPageNumber());

        return wordModuleAttemptRepository.findByUser(user, pageable);
    }

    @Override
    public Page<WordModuleAttempt> findLastWordModuleAttemptsPageByUserSortedByDate(WordModuleAttemptPage wordModuleAttemptPage) {
        User user = userService.findById(wordModuleAttemptPage.getUserId());

        Sort sort = Sort.by(Sort.Direction.DESC, "attemptDate");

        Pageable pageable = PageRequest.of(
                wordModuleAttemptPage.getPageNumber(),
                wordModuleAttemptPage.getPageSize(),
                sort
        );

        return wordModuleAttemptRepository.findByUser(user, pageable);
    }

    @Override
    public WordModuleAttempt findMaximumScoreWordModuleAttempt(WordModuleAttemptWithoutAnswers moduleAttemptWithoutAnswers) {
        User user = userService.findById(moduleAttemptWithoutAnswers.getUserId());

        List<WordModuleAttempt> wordModuleAttempts = wordModuleAttemptRepository
                .findTopByUserAndWordModuleOrderBySuccessPercentageDesc(user, moduleAttemptWithoutAnswers.getWordModuleId());

        if (wordModuleAttempts != null && !wordModuleAttempts.isEmpty()) {
            return wordModuleAttempts.get(0);
        }

        return null;
    }

    @Override
    public WordModuleAttempt findLastWordModuleAttemptScore(WordModuleAttemptWithoutAnswers moduleAttemptWithoutAnswers) {
        User user = userService.findById(moduleAttemptWithoutAnswers.getUserId());

        List<WordModuleAttempt> testAttempts = wordModuleAttemptRepository
                .findNewestByUserAndWordModuleOrderByAttemptDateDesc(user, moduleAttemptWithoutAnswers.getWordModuleId());

        if(testAttempts != null && !testAttempts.isEmpty()) {
            return testAttempts.get(0);
        }

        return null;
    }

    private void checkWordModuleAttemptNull(WordModuleAttempt wordModuleAttempt){
        if(wordModuleAttempt == null){
            throw new IllegalArgumentException("WordModuleAttempt can't be null");
        }
        if(wordModuleAttempt.getUser() == null){
            throw new IllegalArgumentException("WordModuleAttempt user can't be null");
        }
        if(wordModuleAttempt.getWordModule() == null){
            throw new IllegalArgumentException("WordModuleAttempt english test can't be null");
        }
    }

    private void checkWordModuleAttemptIdNull(Long id){
        if(id == null){
            throw new IllegalArgumentException("WordModuleAttempt id can`t be null");
        }
    }

    private List<CustomPair> getWordModuleCustomPairs(WordModuleAttempt wordModuleAttempt){
        WordModule wordModule = wordModuleAttempt.getWordModule();

        if(wordModule == null){
            throw new IllegalArgumentException("WordModuleAttempt WordModule can't be null");
        }

        List<CustomPair> customPairs = customPairService.getCustomPairsByWordModuleId(wordModule.getId());

        if(customPairs == null){
            throw new IllegalArgumentException("WordModuleAttempt WordModule customPairs can't be null");
        }

        return customPairs;
    }
}
