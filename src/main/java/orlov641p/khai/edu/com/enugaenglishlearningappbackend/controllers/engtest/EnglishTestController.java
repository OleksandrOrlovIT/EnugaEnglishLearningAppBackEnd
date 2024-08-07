package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.engtest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.engtest.dto.response.EnglishTestResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.request.TestAttemptRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.response.TestAttemptResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.annotations.user.IsAdminOrSelfIdFromRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.EnglishTestService;

import java.net.URI;
import java.util.List;

import static orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.engtest.dto.mapper.EnglishTestMapper.englishTestListToResponseList;
import static orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.engtest.dto.mapper.EnglishTestMapper.englishTestToResponse;
import static orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.mapper.TestAttemptMapper.convertTestAttemptToResponse;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class EnglishTestController {

    private final EnglishTestService englishTestService;

    @GetMapping("/english-tests")
    public List<EnglishTestResponse> retrieveEnglishTests(){
        return englishTestListToResponseList(englishTestService.findAll());
    }

    @GetMapping("/english-test/{id}")
    public EnglishTestResponse retrieveEnglishTestById(@PathVariable Long id){
        return englishTestToResponse(englishTestService.findById(id));
    }

    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    @PostMapping("/english-test")
    public ResponseEntity<EnglishTestResponse> createEnglishTest(@RequestBody EnglishTest englishTest){
        EnglishTest savedEnglishTest = englishTestService.create(englishTest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEnglishTest.getId())
                .toUri();

        return ResponseEntity.created(location).body(englishTestToResponse(savedEnglishTest));
    }

    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    @PutMapping("/english-test/{id}")
    public EnglishTestResponse updateEnglishTest(@PathVariable Long id, @RequestBody EnglishTest englishTest){
        if(englishTestService.findById(id) == null){
            return null;
        }

        englishTest.setId(id);

        return englishTestToResponse(englishTestService.update(englishTest));
    }

    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    @DeleteMapping("/english-test/{id}")
    public ResponseEntity<Void> deleteEnglishTest(@PathVariable Long id){
        englishTestService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @IsAdminOrSelfIdFromRequest
    @PostMapping("/english-test/take")
    public TestAttemptResponse takeEnglishTest(@RequestBody TestAttemptRequest request){
        return convertTestAttemptToResponse(englishTestService.takeTheTest(request));
    }
}
