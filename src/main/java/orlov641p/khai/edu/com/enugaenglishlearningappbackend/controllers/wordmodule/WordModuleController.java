package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.request.TestAttemptRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.response.TestAttemptResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.request.WordModuleAttemptRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.response.WordModuleAttemptResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.response.WordModuleResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.wordmodule.WordModuleAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.user.UserSecurity;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule.WordModuleService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class WordModuleController {

    private final WordModuleService wordModuleService;
    private final UserSecurity userSecurity;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/word-modules")
    public List<WordModuleResponse> retrieveWordModules() {
        List<WordModuleResponse> wordModuleResponses = new ArrayList<>();

        for (WordModule wordModule : wordModuleService.findAll()) {
            wordModuleResponses.add(new WordModuleResponse(wordModule));
        }

        return wordModuleResponses;
    }

    @GetMapping("/word-module/{id}")
    public WordModuleResponse retrieveWordModuleById(@PathVariable Long id) {
        WordModule wordModule = wordModuleService.findById(id);

        if (userSecurity.hasRoleAdminOrIsSelfOrPublicVisibility(wordModule)) {
            return new WordModuleResponse(wordModule);
        } else {
            throw new AccessDeniedException("Access Denied");
        }
    }

    @PostMapping("/word-module")
    public ResponseEntity<WordModuleResponse> createWordModule(@RequestBody WordModule wordModule) {
        if (userSecurity.hasRoleAdminOrIsSelf(wordModule.getUser().getId())) {
            WordModule savedWordModule = wordModuleService.create(wordModule);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedWordModule.getId())
                    .toUri();

            return ResponseEntity.created(location).body(new WordModuleResponse(savedWordModule));
        } else {
            throw new AccessDeniedException("Access Denied");
        }
    }

    @PutMapping("/word-module/{id}")
    public WordModuleResponse updateWordModule(@PathVariable Long id, @RequestBody WordModule wordModule) {
        if (userSecurity.hasRoleAdminOrIsSelf(wordModule.getUser().getId())) {
            if (wordModuleService.findById(id) == null) {
                return null;
            }

            wordModule.setId(id);

            return new WordModuleResponse(wordModuleService.update(wordModule));
        } else {
            throw new AccessDeniedException("Access Denied");
        }
    }

    @DeleteMapping("/word-module/{id}")
    public ResponseEntity<Void> deleteWordModule(@PathVariable Long id) {
        WordModule wordModule = wordModuleService.findById(id);

        if (userSecurity.hasRoleAdminOrIsSelf(wordModule.getUser().getId())) {
            wordModuleService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new AccessDeniedException("Access Denied");
        }
    }

    @PostMapping("/word-module/take")
    public WordModuleAttemptResponse takeWordModuleTest(@RequestBody WordModuleAttemptRequest wordModuleAttemptRequest) {
        WordModule wordModule = wordModuleService.findById(wordModuleAttemptRequest.getWordModuleId());

        if (userSecurity.hasRoleAdminOrIsSelfOrPublicVisibility(wordModule)
                && userSecurity.authUserTriesToTakeTest(wordModuleAttemptRequest.getUserId())) {
            return new WordModuleAttemptResponse(wordModuleService.takeTheTest(wordModuleAttemptRequest));
        } else {
            throw new AccessDeniedException("Access Denied");
        }
    }
}
