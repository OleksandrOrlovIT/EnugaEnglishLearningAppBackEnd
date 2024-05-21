package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.dto.request.CustomPageRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.EnglishWordService;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class EnglishWordController {
    private final EnglishWordService englishWordService;

    @GetMapping("/eng-words")
    public List<EnglishWord> retrieveEnglishWords(){
        return englishWordService.findAll();
    }

    @PostMapping("/eng-words")
    public Page<EnglishWord> retrieveEnglishWordsByPage(@RequestBody @Validated CustomPageRequest customPageRequest){
        Pageable pageable = Pageable
                .ofSize(customPageRequest.getSize())
                .withPage(customPageRequest.getPage());

        return englishWordService.findPageEnglishWords(pageable);
    }

    @GetMapping("/eng-word/{id}")
    public EnglishWord retrieveEnglishWordById(@PathVariable Long id){
        return englishWordService.findById(id);
    }

    @PostMapping("/eng-word")
    public ResponseEntity<EnglishWord> createEnglishWord(@RequestBody EnglishWord englishWord){
        EnglishWord savedEnglishWord = englishWordService.create(englishWord);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEnglishWord.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedEnglishWord);
    }

    @PutMapping("/eng-word/{id}")
    public EnglishWord updateEnglishWord(@PathVariable Long id, @RequestBody EnglishWord englishWord){
        if(englishWordService.findById(id) == null) {
            return null;
        }

        return englishWordService.update(englishWord);
    }

    @DeleteMapping("/eng-word/{id}")
    public ResponseEntity<Void> deleteEnglishWord(@PathVariable Long id){
        englishWordService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
