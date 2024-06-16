package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.student;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.mapper.TestAttemptMapper;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.response.TestAttemptResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.mapper.WordModuleAttemptMapper;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.response.WordModuleAttemptResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.student.dto.mapper.EnglishStudentMapper;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.student.dto.request.EnglishStudentCreateRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.student.dto.request.EnglishStudentIdPageRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.student.dto.request.EnglishStudentUpdateRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.student.dto.request.EnglishTeacherIdPageRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.student.dto.response.EnglishStudentResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.engtest.TestAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.wordmodule.WordModuleAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.student.EnglishStudent;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.annotations.teacher.IsAdminOrSelfTeacherRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.annotations.teacher.IsTeacherAndHasStudent;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.student.EnglishStudentService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.student.EnglishStudentStatService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class EnglishStudentController {

    private final EnglishStudentService englishStudentService;
    private final EnglishStudentStatService englishStudentStatService;

    @GetMapping("/english-students")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<EnglishStudentResponse> retrieveEnglishStudents() {
        List<EnglishStudent> englishStudents = englishStudentService.findAll();

        List<EnglishStudentResponse> responses = new ArrayList<>();

        for (EnglishStudent englishStudent : englishStudents) {
            responses.add(new EnglishStudentResponse(englishStudent));
        }

        return responses;
    }

    @GetMapping("/english-student/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public EnglishStudentResponse retrieveEnglishStudentById(@PathVariable Long id) {
        return new EnglishStudentResponse(englishStudentService.findById(id));
    }

    @PostMapping("/english-student")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EnglishStudentResponse> createEnglishStudent
            (@RequestBody EnglishStudentCreateRequest englishStudentRequest){
        EnglishStudent savedEnglishStudent = englishStudentService.createEnglishStudentFromRequest(englishStudentRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEnglishStudent.getId())
                .toUri();

        return ResponseEntity.created(location).body(new EnglishStudentResponse(savedEnglishStudent));
    }

    @PutMapping("/english-student/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public EnglishStudentResponse updateEnglishStudent
            (@PathVariable Long id, @RequestBody EnglishStudentUpdateRequest englishStudentUpdateRequest) {
        englishStudentUpdateRequest.setEnglishStudentId(id);
        return new EnglishStudentResponse(englishStudentService.updateEnglishStudentFromRequest(englishStudentUpdateRequest));
    }

    @DeleteMapping("/english-student/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteEnglishStudent(@PathVariable Long id) {
        englishStudentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/english-student/by-teacher")
    @IsAdminOrSelfTeacherRequest
    public Page<EnglishStudentResponse> getStudentsPageByTeacher(@RequestBody EnglishTeacherIdPageRequest request) {
        Page<EnglishStudent> englishStudents = englishStudentService.findEnglishStudentsByEnglishTeacher(
                request.getEnglishTeacherId(),
                PageRequest.of(request.getPageNumber(), request.getPageSize())
        );

        return EnglishStudentMapper.convertEnglishStudentPageToResponse(englishStudents);
    }

    @PostMapping("/english-student/english-tests-attempts-page/by-teacher")
    @IsTeacherAndHasStudent
    public Page<TestAttemptResponse> getStudentsTestAttemptsPageByTeacher(@RequestBody EnglishStudentIdPageRequest request) {
        Page<TestAttempt> testAttemptPage = englishStudentStatService.getEnglishTestAttempts(request.getEnglishStudentId(),
                PageRequest.of(request.getPageNumber(), request.getPageSize()));

        return TestAttemptMapper.convertPageTestAttemptToResponse(testAttemptPage);
    }

    @PostMapping("/english-student/word-modules-attempts-page/by-teacher")
    @IsTeacherAndHasStudent
    public Page<WordModuleAttemptResponse> getStudentsPublicWordModulesAttemptPageByTeacher
            (@RequestBody EnglishStudentIdPageRequest request) {
        Page<WordModuleAttempt> wordModuleAttemptPage = englishStudentStatService
                .getPublicWordModulesAttempts(request.getEnglishStudentId(),
                PageRequest.of(request.getPageNumber(), request.getPageSize()));

        return WordModuleAttemptMapper.convertPageWordModuleAttemptToResponse(wordModuleAttemptPage);
    }
}