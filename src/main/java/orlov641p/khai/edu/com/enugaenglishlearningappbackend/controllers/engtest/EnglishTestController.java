package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.engtest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.dto.request.TestAttemptRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.dto.response.TestAttemptResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.TestAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.EnglishTestService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class EnglishTestController {

    private final EnglishTestService englishTestService;

    @GetMapping("/english-tests")
    public List<EnglishTest> retrieveEnglishTests(){return  englishTestService.findAll();}

    @GetMapping("/english-test/{id}")
    public EnglishTest retrieveEnglishTestById(@PathVariable Long id){return englishTestService.findById(id);}

    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    @PostMapping("/english-test")
    public ResponseEntity<EnglishTest> createEnglishTest(@RequestBody EnglishTest englishTest){
        EnglishTest savedEnglishTest = englishTestService.create(englishTest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEnglishTest.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedEnglishTest);
    }

    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    @PutMapping("/english-test/{id}")
    public EnglishTest updateEnglishTest(@PathVariable Long id, @RequestBody EnglishTest englishTest){
        if(englishTestService.findById(id) == null){
            return null;
        }

        englishTest.setId(id);

        return englishTestService.update(englishTest);
    }

    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    @DeleteMapping("/english-test/{id}")
    public ResponseEntity<Void> deleteEnglishTest(@PathVariable Long id){
        englishTestService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/english-test/take")
    public TestAttemptResponse takeEnglishTest(@RequestBody TestAttemptRequest testAttemptRequest){
        return new TestAttemptResponse(englishTestService.takeTheTest(testAttemptRequest));
    }
}
