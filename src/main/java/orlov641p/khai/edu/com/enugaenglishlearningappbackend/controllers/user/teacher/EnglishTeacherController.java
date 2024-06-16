package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.teacher;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.teacher.dto.request.EnglishTeacherCreateRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.teacher.dto.request.EnglishTeacherUpdateRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.teacher.dto.response.EnglishTeacherResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.teacher.EnglishTeacher;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.annotations.IsAdminOrSelf;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.annotations.teacher.IsAdminOrSelfTeacherRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.teacher.EnglishTeacherService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class EnglishTeacherController {
    private final EnglishTeacherService englishTeacherService;

    @GetMapping("/english-teachers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<EnglishTeacherResponse> retrieveEnglishTeachers(){
        List<EnglishTeacher> englishTeachers = englishTeacherService.findAll();

        List<EnglishTeacherResponse> responses = new ArrayList<>();

        for(EnglishTeacher englishTeacher : englishTeachers){
            responses.add(new EnglishTeacherResponse(englishTeacher));
        }

        return responses;
    }

    @GetMapping("/english-teacher/{id}")
    @IsAdminOrSelfTeacherRequest
    public EnglishTeacherResponse retrieveEnglishTeacherById(@PathVariable Long id){
        return new EnglishTeacherResponse(englishTeacherService.findById(id));
    }

    @GetMapping("/english-teacher/user/{id}")
    @IsAdminOrSelf
    public EnglishTeacherResponse retrieveEnglishTeacherByUserId(@PathVariable Long id){
        return new EnglishTeacherResponse(englishTeacherService.findByUserId(id));
    }

    @PostMapping("/english-teacher")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EnglishTeacherResponse> createEnglishTeacher
            (@RequestBody EnglishTeacherCreateRequest englishTeacherCreateRequest){
        EnglishTeacher savedEnglishTeacher = englishTeacherService.createFromRequest(englishTeacherCreateRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEnglishTeacher.getId())
                .toUri();

        return ResponseEntity.created(location).body(new EnglishTeacherResponse(savedEnglishTeacher));
    }

    @PutMapping("/english-teacher/{id}")
    @IsAdminOrSelfTeacherRequest
    public EnglishTeacherResponse updateEnglishTeacher(@PathVariable Long id,
                                                       @RequestBody EnglishTeacherUpdateRequest englishTeacherUpdateRequest){
        englishTeacherUpdateRequest.setEnglishTeacherId(id);
        return new EnglishTeacherResponse(englishTeacherService.updateFromRequest(englishTeacherUpdateRequest));
    }

    @DeleteMapping("/english-teacher/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteEnglishTeacher(@PathVariable Long id){
        englishTeacherService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}