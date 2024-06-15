package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.teacher;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.teacher.dto.EnglishTeacherResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.teacher.EnglishTeacher;
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
    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    public EnglishTeacherResponse retrieveEnglishTeacherById(@PathVariable Long id){
        return new EnglishTeacherResponse(englishTeacherService.findById(id));
    }

    @PostMapping("/english-teacher")
    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    public ResponseEntity<EnglishTeacherResponse> createEnglishTeacher(@RequestBody EnglishTeacher englishTeacher){
        EnglishTeacher savedEnglishTeacher = englishTeacherService.create(englishTeacher);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEnglishTeacher.getId())
                .toUri();

        return ResponseEntity.created(location).body(new EnglishTeacherResponse(savedEnglishTeacher));
    }

    @PutMapping("/english-teacher/{id}")
    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    public EnglishTeacherResponse updateEnglishTeacher(@PathVariable Long id, @RequestBody EnglishTeacher englishTeacher){
        if(englishTeacherService.findById(id) == null) {
            return null;
        }

        return new EnglishTeacherResponse(englishTeacherService.update(englishTeacher));
    }

    @DeleteMapping("/english-teacher/{id}")
    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    public ResponseEntity<Void> deleteEnglishTeacher(@PathVariable Long id){
        englishTeacherService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}