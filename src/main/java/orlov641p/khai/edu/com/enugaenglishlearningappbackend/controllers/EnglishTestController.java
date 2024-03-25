package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishTest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.EnglishTestService;

import java.net.URI;
import java.util.List;

@RestController()
@RequestMapping("/v1")
public class EnglishTestController {
    private final EnglishTestService englishTestService;

    public EnglishTestController(EnglishTestService englishTestService) {
        this.englishTestService = englishTestService;
    }

    @GetMapping("/english-tests")
    public List<EnglishTest> retrieveEnglishTests(){return  englishTestService.findAll();}

    @GetMapping("/english-test/{id}")
    public EnglishTest retrieveEnglishTestById(@PathVariable Long id){return englishTestService.findById(id);}

    @PostMapping("/english-test")
    public ResponseEntity<EnglishTest> createEnglishTest(@RequestBody EnglishTest englishTest){
        EnglishTest savedEnglishTest = englishTestService.save(englishTest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEnglishTest.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedEnglishTest);
    }

    @PutMapping("/english-test/{id}")
    public EnglishTest updateEnglishTest(@PathVariable Long id, @RequestBody EnglishTest englishTest){
        if(englishTestService.findById(id) == null){
            return null;
        }

        englishTest.setId(id);
        return englishTestService.save(englishTest);
    }

    @DeleteMapping("/english-test/{id}")
    public ResponseEntity<Void> deleteEnglishTest(@PathVariable Long id){
        englishTestService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
