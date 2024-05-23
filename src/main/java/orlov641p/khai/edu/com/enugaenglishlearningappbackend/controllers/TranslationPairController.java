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
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.TranslationPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.UkrainianWord;
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
    public Page<TranslationPair> retrieveTranslationPairsByPage(@RequestBody @Validated CustomPageRequest customPageRequest){
        Pageable pageable = Pageable
                .ofSize(customPageRequest.getSize())
                .withPage(customPageRequest.getPage());

        return translationPairService.findPageTranslationPairs(pageable);
    }

    @GetMapping("/translation-pair/{id}")
    public TranslationPair retrieveTranslationPairById(@PathVariable Long id){
        return translationPairService.findById(id);
    }

    @PostMapping("/translation-pair/translate/eng-to-ukr-word")
    public List<UkrainianWord> translateEnglishToUkrainianWord(@RequestBody EnglishWord englishWord){
        return translationPairService.translateEnglishWordToUkrainian(englishWord);
    }

    @PostMapping("/translation-pair/translateBulk/eng-to-ukr-word")
    public List<List<UkrainianWord>> translateEnglishBulkToUkrainianWord(@RequestBody List<EnglishWord> englishWords){
        return translationPairService.translateBulkEnglishWordsToUkrainian(englishWords);
    }

    @PostMapping("/translation-pair")
    public ResponseEntity<TranslationPair> createTranslationPair(@RequestBody TranslationPair translationPair){
        TranslationPair savedTranslationPair = translationPairService.create(translationPair);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTranslationPair.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedTranslationPair);
    }

    @PutMapping("/translation-pair/{id}")
    public TranslationPair updateTranslationPair(@PathVariable Long id, @RequestBody TranslationPair translationPair){
        if(translationPairService.findById(id) == null) {
            return null;
        }

        return translationPairService.update(translationPair);
    }

    @DeleteMapping("/translation-pair/{id}")
    public ResponseEntity<Void> deleteTranslationPair(@PathVariable Long id){
        translationPairService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
