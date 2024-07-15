package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.request.WordModuleAttemptRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.response.WordModuleAttemptResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.mapper.WordModuleMapper;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.request.PageWithUserIdRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.request.SimplePageRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.request.WordModuleRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.response.WordModuleResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.annotations.user.IsAdminOrSelf;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.annotations.user.IsAdminOrSelfIdFromRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.annotations.wordmodule.IsAdminOrSelfIdOrWordModuleOwner;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.annotations.wordmodule.IsAuthUserTriesToTakeTheWordModule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.annotations.wordmodule.IsLoggedUserAdminOrWordModuleOwner;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.annotations.wordmodule.IsPublicWordModuleOrAdminOrOwner;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.user.UserSecurity;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule.WordModuleService;

import java.net.URI;
import java.util.List;

import static orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.mapper.WordModuleAttemptMapper.convertWordModuleAttemptToResponse;
import static orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.mapper.WordModuleMapper.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class WordModuleController {

    private final WordModuleService wordModuleService;
    private final UserService userService;
    private final UserSecurity userSecurity;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/word-modules")
    public List<WordModuleResponse> retrieveWordModules() {
        return convertWordModuleListToResponseList(wordModuleService.findAll());
    }

    @IsAdminOrSelfIdOrWordModuleOwner
    @GetMapping("/word-module/{id}")
    public WordModuleResponse retrieveWordModuleById(@PathVariable Long id) {
        WordModule wordModule = wordModuleService.findById(id);

        return convertWordModuleToResponse(wordModule);
    }

    @IsAdminOrSelfIdFromRequest
    @PostMapping("/word-module")
    public ResponseEntity<WordModuleResponse> createWordModule(@RequestBody WordModuleRequest request) {
        User user = userService.findById(request.getUserId());

        WordModule wordModule = WordModuleMapper.convertWordModuleRequestToWordModule(request, user);

        WordModule savedWordModule = wordModuleService.create(wordModule);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedWordModule.getId())
                .toUri();

        return ResponseEntity.created(location).body(convertWordModuleToResponse(savedWordModule));
    }

    @IsAdminOrSelfIdFromRequest
    @PutMapping("/word-module/{id}")
    public WordModuleResponse updateWordModule(@PathVariable Long id, @RequestBody WordModuleRequest request) {
        if (wordModuleService.findById(id) == null) {
            return null;
        }

        User user = userService.findById(request.getUserId());

        WordModule wordModule = WordModuleMapper.convertWordModuleRequestToWordModule(request, user);

        wordModule.setId(id);

        return convertWordModuleToResponse(wordModuleService.update(wordModule));
    }

    @IsLoggedUserAdminOrWordModuleOwner
    @DeleteMapping("/word-module/{id}")
    public ResponseEntity<Void> deleteWordModule(@PathVariable Long id) {
        wordModuleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @IsPublicWordModuleOrAdminOrOwner
    @IsAuthUserTriesToTakeTheWordModule
    @PostMapping("/word-module/take")
    public WordModuleAttemptResponse takeWordModuleTest(@RequestBody WordModuleAttemptRequest request) {
        return convertWordModuleAttemptToResponse(wordModuleService.takeTheTest(request));
    }

    @IsAdminOrSelfIdFromRequest
    @PostMapping("/word-module/user")
    public Page<WordModuleResponse> getWordModulesPageByUser(@RequestBody @Validated PageWithUserIdRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        Page<WordModule> wordModules =
                wordModuleService.findPageByUserOrderByIdDesc(request.getUserId(), pageable);
        return convertPageTestAttemptToResponse(wordModules);
    }

    @IsAdminOrSelf
    @GetMapping("/word-module/user/{id}")
    public List<WordModuleResponse> getWordModulesByUserId(@PathVariable Long id) {
        return convertWordModuleListToResponseList(wordModuleService.findByUserOrderByIdDesc(id));
    }

    @GetMapping("/word-module/public-without-logged-user")
    public List<WordModuleResponse> getPublicWordModulesWithoutUser() {
        Long userId = userSecurity.getLoggedUser().getId();

        return convertWordModuleListToResponseList(wordModuleService.findByVisibilityPublicAndUserNot(userId));
    }

    @PostMapping("/word-module/public-without-logged-user")
    public Page<WordModuleResponse> getPublicWordModulesPageWithoutUser(@RequestBody @Validated SimplePageRequest page) {
        Long userId = userSecurity.getLoggedUser().getId();

        Pageable pageable = PageRequest.of(page.getPageNumber(), page.getPageSize());

        Page<WordModule> wordModules =
                wordModuleService.findPageByVisibilityPublicAndUserNot(userId, pageable);

        return convertPageTestAttemptToResponse(wordModules);
    }
}