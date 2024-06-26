package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.vocabulary;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.book.dto.request.CustomPageRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.UkrainianWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.vocabulary.UkrainianWordService;

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
    public Page<UkrainianWord> retrieveUkrainianWordsByPage(@RequestBody @Validated CustomPageRequest customPageRequest){
        Pageable pageable = Pageable
                .ofSize(customPageRequest.getSize())
                .withPage(customPageRequest.getPage());

        return ukrainianWordService.findPageUkrainianWords(pageable);
    }

    @GetMapping("/ukr-word/{id}")
    public UkrainianWord retrieveUkrainianWordById(@PathVariable Long id){
        return ukrainianWordService.findById(id);
    }

    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    @PostMapping("/ukr-word")
    public ResponseEntity<UkrainianWord> createUkrainianWord(@RequestBody UkrainianWord ukrainianWord){
        UkrainianWord savedUkrainianWord = ukrainianWordService.create(ukrainianWord);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUkrainianWord.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedUkrainianWord);
    }

    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    @PutMapping("/ukr-word/{id}")
    public UkrainianWord updateUkrainianWord(@PathVariable Long id, @RequestBody UkrainianWord ukrainianWord){
        if(ukrainianWordService.findById(id) == null) {
            return null;
        }

        return ukrainianWordService.update(ukrainianWord);
    }

    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    @DeleteMapping("/ukr-word/{id}")
    public ResponseEntity<Void> deleteUkrainianWord(@PathVariable Long id){
        ukrainianWordService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
