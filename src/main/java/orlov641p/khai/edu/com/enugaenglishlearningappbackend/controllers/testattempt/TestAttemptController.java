package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.dto.mapper.TestAttemptMapper;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.dto.request.TestAttemptPage;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.dto.request.TestAttemptWithoutAnswers;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.dto.response.TestAttemptResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.TestAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.testattempt.TestAttemptService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class TestAttemptController {

    private final TestAttemptService testAttemptService;

    @GetMapping("/test-attempts")
    public List<TestAttemptResponse> retrieveTestAttempts(){
        List<TestAttempt> testAttempts = testAttemptService.findAll();

        List<TestAttemptResponse> testAttemptResponses = new ArrayList<>();

        for(TestAttempt testAttempt : testAttempts) {
            testAttemptResponses.add(new TestAttemptResponse(testAttempt));
        }

        return testAttemptResponses;
    }

    @PostMapping("/test-attempts/user/stats")
    public Page<TestAttemptResponse> getPageByUser(@RequestBody @Validated TestAttemptPage testAttemptPage){
        Page<TestAttempt> testAttempts = testAttemptService.findTestAttemptsPageByUser(testAttemptPage);

        return TestAttemptMapper.convertPageTestAttemptToResponse(testAttempts);
    }

    @PostMapping("/test-attempts/user/stats-best")
    public TestAttemptResponse getBestTestAttemptByUserId(@RequestBody TestAttemptWithoutAnswers testAttemptWithoutAnswers){
        return new TestAttemptResponse(testAttemptService.findMaximumScoreAttempt(testAttemptWithoutAnswers));
    }

    @GetMapping("/test-attempt/{id}")
    public TestAttemptResponse retrieveTestAttemptById(@PathVariable Long id){
        return new TestAttemptResponse(testAttemptService.findById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/test-attempt")
    public ResponseEntity<TestAttemptResponse> createTestAttempt(@RequestBody TestAttempt testAttempt){
        TestAttemptResponse savedTestAttempt = new TestAttemptResponse(testAttemptService.create(testAttempt));

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
        return new TestAttemptResponse(testAttemptService.update(testAttempt));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/test-attempt/{id}")
    public ResponseEntity<Void> deleteTestAttempt(@PathVariable Long id){
        testAttemptService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
