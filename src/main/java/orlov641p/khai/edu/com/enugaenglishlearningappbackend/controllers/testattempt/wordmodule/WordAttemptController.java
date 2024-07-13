package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.mapper.WordModuleAttemptMapper;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.request.WordModuleAttemptPage;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.request.WordModuleAttemptWithoutAnswers;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.response.WordModuleAttemptResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.wordmodule.WordModuleAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.testattempt.wordmodule.WordModuleAttemptService;

import java.net.URI;
import java.util.List;

import static orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.mapper.WordModuleAttemptMapper.convertWordModuleAttemptListToResponse;
import static orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.mapper.WordModuleAttemptMapper.convertWordModuleAttemptToResponse;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class WordAttemptController {
    private final WordModuleAttemptService wordModuleAttemptService;

    @GetMapping("/word-module-attempts")
    public List<WordModuleAttemptResponse> retrieveWordModuleAttempts(){
        return convertWordModuleAttemptListToResponse(wordModuleAttemptService.findAll());
    }

    @PostMapping("/word-module-attempts/user/stats")
    public Page<WordModuleAttemptResponse> getPageByUser
            (@RequestBody @Validated WordModuleAttemptPage wordModuleAttemptPage){

        Page<WordModuleAttempt> wordModuleAttempts =
                wordModuleAttemptService.findWordModuleAttemptsPageByUser(wordModuleAttemptPage);

        return WordModuleAttemptMapper.convertPageWordModuleAttemptToResponse(wordModuleAttempts);
    }

    @PostMapping("/word-module-attempts/user/stats-list-last")
    public Page<WordModuleAttemptResponse> getPageByUserSortedByDate
            (@RequestBody @Validated WordModuleAttemptPage testAttemptPage){

        Page<WordModuleAttempt> wordModuleAttempts = wordModuleAttemptService
                .findLastWordModuleAttemptsPageByUserSortedByDate(testAttemptPage);

        return WordModuleAttemptMapper.convertPageWordModuleAttemptToResponse(wordModuleAttempts);
    }

    @PostMapping("/word-module-attempts/user/stats-best")
    public WordModuleAttemptResponse getBestWordModuleAttemptByUserId
            (@RequestBody WordModuleAttemptWithoutAnswers testAttemptWithoutAnswers){

        return convertWordModuleAttemptToResponse(
                wordModuleAttemptService.findMaximumScoreWordModuleAttempt(testAttemptWithoutAnswers)
        );
    }

    @PostMapping("/word-module-attempts/user/stats-last")
    public WordModuleAttemptResponse getLastWordModuleAttemptByUserId
            (@RequestBody WordModuleAttemptWithoutAnswers testAttemptWithoutAnswers){
        return convertWordModuleAttemptToResponse(
                wordModuleAttemptService.findLastWordModuleAttemptScore(testAttemptWithoutAnswers)
        );
    }

    @GetMapping("/word-module-attempt/{id}")
    public WordModuleAttemptResponse retrieveWordModuleAttemptById(@PathVariable Long id){
        return convertWordModuleAttemptToResponse(wordModuleAttemptService.findById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/word-module-attempt")
    public ResponseEntity<WordModuleAttemptResponse> createWordModuleAttempt(@RequestBody WordModuleAttempt wordModuleAttempt){
        WordModuleAttemptResponse savedWordModuleAttempt =
                convertWordModuleAttemptToResponse(wordModuleAttemptService.create(wordModuleAttempt));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedWordModuleAttempt.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedWordModuleAttempt);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/word-module-attempt/{id}")
    public WordModuleAttemptResponse updateWordModuleAttempt
            (@PathVariable Long id, @RequestBody WordModuleAttempt wordModuleAttempt){
        if(wordModuleAttemptService.findById(id) == null) {
            return null;
        }
        return convertWordModuleAttemptToResponse(wordModuleAttemptService.update(wordModuleAttempt));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/word-module-attempt/{id}")
    public ResponseEntity<Void> deleteWordModuleAttempt(@PathVariable Long id){
        wordModuleAttemptService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
