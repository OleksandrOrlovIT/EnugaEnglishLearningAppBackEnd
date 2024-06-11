package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.engtest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.Question;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.engtest.QuestionService;

import java.net.URI;
import java.util.List;

@RestController()
@RequestMapping("/v1")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions")
    public List<Question> retrieveQuestions(){return questionService.findAll();}

    @GetMapping("/question/{id}")
    public Question retrieveQuestionById(@PathVariable Long id){
        return questionService.findById(id);
    }

    @GetMapping("/questions/{englishTestId}")
    public List<Question> retrieveQuestionsByEnglishTestId(@PathVariable Long englishTestId){
        return questionService.getQuestionsByEnglishTestId(englishTestId);
    }

    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    @PostMapping("/question")
    public ResponseEntity<Question> createQuestion(@RequestBody Question question){
        Question savedQuestion = questionService.create(question);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedQuestion.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedQuestion);
    }

    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    @PutMapping("/question/{id}")
    public Question updateQuestion(@PathVariable Long id, @RequestBody Question question){
        if(questionService.findById(id) == null){
            return null;
        }

        return questionService.update(question);
    }

    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    @DeleteMapping("/question/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id){
        questionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
