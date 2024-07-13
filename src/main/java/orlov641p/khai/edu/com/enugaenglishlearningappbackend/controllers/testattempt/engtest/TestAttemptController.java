package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.request.TestAttemptPage;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.request.TestAttemptWithoutAnswers;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.response.TestAttemptResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.engtest.TestAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.user.UserSecurity;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.testattempt.engtest.TestAttemptService;

import java.net.URI;
import java.util.List;

import static orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.mapper.TestAttemptMapper.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class TestAttemptController {

    private final TestAttemptService testAttemptService;
    private final UserSecurity userSecurity;

    @GetMapping("/test-attempts")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<TestAttemptResponse> retrieveTestAttempts(){
        return convertTestAttemptListToResponseList(testAttemptService.findAll());
    }

    @PostMapping("/test-attempts/user/stats")
    public Page<TestAttemptResponse> getPageByUser(@RequestBody @Validated TestAttemptPage testAttemptPage){
        if(userSecurity.hasRoleAdminOrIsSelf(testAttemptPage.userId)){
            Page<TestAttempt> testAttempts = testAttemptService.findTestAttemptsPageByUser(testAttemptPage);
            return convertPageTestAttemptToResponse(testAttempts);
        } else {
            throw new AccessDeniedException("Access is denied");
        }
    }

    @PostMapping("/test-attempts/user/stats-list-last")
    public Page<TestAttemptResponse> getPageByUserSortedByDate(@RequestBody @Validated TestAttemptPage testAttemptPage){
        if(userSecurity.hasRoleAdminOrIsSelf(testAttemptPage.userId)) {
            Page<TestAttempt> testAttempts = testAttemptService.findLastTestAttemptsPageByUserSortedByDate(testAttemptPage);

            return convertPageTestAttemptToResponse(testAttempts);
        } else {
            throw new AccessDeniedException("Access is denied");
        }
    }

    @PostMapping("/test-attempts/user/stats-best")
    public TestAttemptResponse getBestTestAttemptByUserId(@RequestBody TestAttemptWithoutAnswers testAttemptWithoutAnswers){
        if(userSecurity.hasRoleAdminOrIsSelf(testAttemptWithoutAnswers.getUserId())) {
            return convertTestAttemptToResponse(testAttemptService.findMaximumScoreAttempt(testAttemptWithoutAnswers));
        } else {
            throw new AccessDeniedException("Access is denied");
        }
    }

    @PostMapping("/test-attempts/user/stats-last")
    public TestAttemptResponse getLastTestAttemptByUserId(@RequestBody TestAttemptWithoutAnswers testAttemptWithoutAnswers){
        if(userSecurity.hasRoleAdminOrIsSelf(testAttemptWithoutAnswers.getUserId())) {
            return convertTestAttemptToResponse(testAttemptService.findLastAttemptScore(testAttemptWithoutAnswers));
        } else {
            throw new AccessDeniedException("Access is denied");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/test-attempt/{id}")
    public TestAttemptResponse retrieveTestAttemptById(@PathVariable Long id){
        return convertTestAttemptToResponse(testAttemptService.findById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/test-attempt")
    public ResponseEntity<TestAttemptResponse> createTestAttempt(@RequestBody TestAttempt testAttempt){
        TestAttemptResponse savedTestAttempt = convertTestAttemptToResponse(testAttemptService.create(testAttempt));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTestAttempt.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedTestAttempt);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/test-attempt/{id}")
    public TestAttemptResponse updateTestAttempt(@PathVariable Long id, @RequestBody TestAttempt testAttempt){
        if(testAttemptService.findById(id) == null) {
            return null;
        }
        return convertTestAttemptToResponse(testAttemptService.update(testAttempt));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/test-attempt/{id}")
    public ResponseEntity<Void> deleteTestAttempt(@PathVariable Long id){
        testAttemptService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}