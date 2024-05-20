package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.dto.PageRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.TranslationPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.TranslationPairService;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class TranslationPairController {
    private final TranslationPairService translationPairService;

    @GetMapping("/translation-pairs")
    public List<TranslationPair> retrieveTranslationPairs(){
        return translationPairService.findAll();
    }

    @PostMapping("/translation-pairs")
    public Page<TranslationPair> retrieveTranslationPairsByPage(@RequestBody @Validated PageRequest pageRequest){
        Pageable pageable = Pageable
                .ofSize(pageRequest.getSize())
                .withPage(pageRequest.getPage());

        return translationPairService.findPageTranslationPairs(pageable);
    }

    @GetMapping("/translation-pair/{id}")
    public TranslationPair retrieveTranslationPairById(@PathVariable Long id){
        return translationPairService.findById(id);
    }

    @PostMapping("/translation-pair")
    public ResponseEntity<TranslationPair> createTranslationPair(@RequestBody TranslationPair englishWord){
        TranslationPair savedEnglishWord = translationPairService.create(englishWord);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEnglishWord.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedEnglishWord);
    }

    @PutMapping("/translation-pair/{id}")
    public TranslationPair updateTranslationPair(@PathVariable Long id, @RequestBody TranslationPair englishWord){
        if(translationPairService.findById(id) == null) {
            return null;
        }

        return translationPairService.update(englishWord);
    }

    @DeleteMapping("/translation-pair/{id}")
    public ResponseEntity<Void> deleteTranslationPair(@PathVariable Long id){
        translationPairService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
