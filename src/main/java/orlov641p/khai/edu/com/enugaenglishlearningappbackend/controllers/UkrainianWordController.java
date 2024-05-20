package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.dto.PageRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.UkrainianWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.UkrainianWordService;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class UkrainianWordController {
    private final UkrainianWordService ukrainianWordService;

    @GetMapping("/ukr-words")
    public List<UkrainianWord> retrieveUkrainianWords(){
        return ukrainianWordService.findAll();
    }

    @PostMapping("/ukr-words")
    public Page<UkrainianWord> retrieveUkrainianWordsByPage(@RequestBody @Validated PageRequest pageRequest){
        Pageable pageable = Pageable
                .ofSize(pageRequest.getSize())
                .withPage(pageRequest.getPage());

        return ukrainianWordService.findPageUkrainianWords(pageable);
    }

    @GetMapping("/ukr-word/{id}")
    public UkrainianWord retrieveUkrainianWordById(@PathVariable Long id){
        return ukrainianWordService.findById(id);
    }

    @PostMapping("/ukr-word")
    public ResponseEntity<UkrainianWord> createUkrainianWord(@RequestBody UkrainianWord ukrainianWord){
        UkrainianWord savedUkrainianWord = ukrainianWordService.create(ukrainianWord);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUkrainianWord.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedUkrainianWord);
    }

    @PutMapping("/ukr-word/{id}")
    public UkrainianWord updateUkrainianWord(@PathVariable Long id, @RequestBody UkrainianWord ukrainianWord){
        if(ukrainianWordService.findById(id) == null) {
            return null;
        }

        return ukrainianWordService.update(ukrainianWord);
    }

    @DeleteMapping("/ukr-word/{id}")
    public ResponseEntity<Void> deleteUkrainianWord(@PathVariable Long id){
        ukrainianWordService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
