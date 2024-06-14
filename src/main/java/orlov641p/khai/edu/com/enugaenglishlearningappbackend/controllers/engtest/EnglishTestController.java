package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.engtest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.engtest.dto.response.EnglishTestResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.request.TestAttemptRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.response.TestAttemptResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.user.UserSecurity;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.EnglishTestService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class EnglishTestController {

    private final EnglishTestService englishTestService;
    private final UserSecurity userSecurity;

    @GetMapping("/english-tests")
    public List<EnglishTestResponse> retrieveEnglishTests(){
        List<EnglishTestResponse> englishTestResponses = new ArrayList<>();

        for(EnglishTest englishTest : englishTestService.findAll()){
            englishTestResponses.add(new EnglishTestResponse(englishTest));
        }

        return englishTestResponses;
    }

    @GetMapping("/english-test/{id}")
    public EnglishTestResponse retrieveEnglishTestById(@PathVariable Long id){
        return new EnglishTestResponse(englishTestService.findById(id));
    }

    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    @PostMapping("/english-test")
    public ResponseEntity<EnglishTestResponse> createEnglishTest(@RequestBody EnglishTest englishTest){
        EnglishTest savedEnglishTest = englishTestService.create(englishTest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEnglishTest.getId())
                .toUri();

        return ResponseEntity.created(location).body(new EnglishTestResponse(savedEnglishTest));
    }

    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    @PutMapping("/english-test/{id}")
    public EnglishTestResponse updateEnglishTest(@PathVariable Long id, @RequestBody EnglishTest englishTest){
        if(englishTestService.findById(id) == null){
            return null;
        }

        englishTest.setId(id);

        return new EnglishTestResponse(englishTestService.update(englishTest));
    }

    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    @DeleteMapping("/english-test/{id}")
    public ResponseEntity<Void> deleteEnglishTest(@PathVariable Long id){
        englishTestService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/english-test/take")
    public TestAttemptResponse takeEnglishTest(@RequestBody TestAttemptRequest testAttemptRequest){
        if(userSecurity.hasRoleAdminOrIsSelf(testAttemptRequest.getUserId())) {
            return new TestAttemptResponse(englishTestService.takeTheTest(testAttemptRequest));
        } else {
            throw new AccessDeniedException("Access Denied");
        }
    }
}
