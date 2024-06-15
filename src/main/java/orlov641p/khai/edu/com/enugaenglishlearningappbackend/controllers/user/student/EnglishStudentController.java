package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.student;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.student.dto.mapper.EnglishStudentMapper;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.student.dto.request.EnglishTeacherIdPageRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.student.dto.response.EnglishStudentResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.student.EnglishStudent;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.teacher.EnglishTeacher;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.engteacher.EnglishTeacherSecurity;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.student.EnglishStudentService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class EnglishStudentController {

    private final EnglishStudentService englishStudentService;
    private final EnglishTeacherSecurity englishTeacherSecurity;

    @GetMapping("/english-students")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<EnglishStudentResponse> retrieveEnglishStudents(){
        List<EnglishStudent> englishStudents = englishStudentService.findAll();

        List<EnglishStudentResponse> responses = new ArrayList<>();

        for(EnglishStudent englishStudent : englishStudents){
            responses.add(new EnglishStudentResponse(englishStudent));
        }

        return responses;
    }

    @GetMapping("/english-student/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public EnglishStudentResponse retrieveEnglishStudentById(@PathVariable Long id){
        return new EnglishStudentResponse(englishStudentService.findById(id));
    }

    @PostMapping("/english-student")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EnglishStudentResponse> createEnglishStudent(@RequestBody EnglishStudent englishStudent){
        EnglishStudent savedEnglishStudent = englishStudentService.create(englishStudent);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEnglishStudent.getId())
                .toUri();

        return ResponseEntity.created(location).body(new EnglishStudentResponse(savedEnglishStudent));
    }

    @PutMapping("/english-student/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public EnglishStudentResponse updateEnglishStudent(@PathVariable Long id, @RequestBody EnglishStudent englishStudent){
        if(englishStudentService.findById(id) == null) {
            return null;
        }

        return new EnglishStudentResponse(englishStudentService.update(englishStudent));
    }

    @DeleteMapping("/english-student/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteEnglishStudent(@PathVariable Long id){
        englishStudentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/english-student/by-teacher")
    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    public Page<EnglishStudentResponse> getStudentsPageByTeacher(@RequestBody EnglishTeacherIdPageRequest request){
        if(englishTeacherSecurity.checkIfLoggedUserIsRequestedTeacher(request.getEnglishTeacherId())) {
            Page<EnglishStudent> englishStudents = englishStudentService.findEnglishStudentsByEnglishTeacher(
                    request.getEnglishTeacherId(),
                    PageRequest.of(request.getPageNumber(), request.getPageSize())
            );

            return EnglishStudentMapper.convertEnglishStudentPageToResponse(englishStudents);
        } else {
            throw new AccessDeniedException("Access Denied");
        }
    }
}