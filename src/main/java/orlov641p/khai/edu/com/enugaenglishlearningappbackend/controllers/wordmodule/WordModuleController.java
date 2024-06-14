package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.request.TestAttemptRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.response.TestAttemptResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.request.WordModuleAttemptRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.response.WordModuleAttemptResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.response.WordModuleResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.wordmodule.WordModuleAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule.WordModuleService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class WordModuleController {

    private final WordModuleService wordModuleService;

    @GetMapping("/word-modules")
    public List<WordModuleResponse> retrieveWordModules(){
        List<WordModuleResponse> wordModuleResponses = new ArrayList<>();

        for(WordModule wordModule : wordModuleService.findAll()){
            wordModuleResponses.add(new WordModuleResponse(wordModule));
        }

        return wordModuleResponses;
    }

    @GetMapping("/word-module/{id}")
    public WordModuleResponse retrieveWordModuleById(@PathVariable Long id){
        return new WordModuleResponse(wordModuleService.findById(id));
    }

    @PostMapping("/word-module")
    public ResponseEntity<WordModuleResponse> createWordModule(@RequestBody WordModule wordModule){
        WordModule savedWordModule = wordModuleService.create(wordModule);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedWordModule.getId())
                .toUri();

        return ResponseEntity.created(location).body(new WordModuleResponse(savedWordModule));
    }

    @PutMapping("/word-module/{id}")
    public WordModuleResponse updateWordModule(@PathVariable Long id, @RequestBody WordModule wordModule){
        if(wordModuleService.findById(id) == null){
            return null;
        }

        wordModule.setId(id);

        return new WordModuleResponse(wordModuleService.update(wordModule));
    }

    @DeleteMapping("/word-module/{id}")
    public ResponseEntity<Void> deleteWordModule(@PathVariable Long id){
        wordModuleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/word-module/take")
    public WordModuleAttemptResponse takeWordModuleTest(@RequestBody WordModuleAttemptRequest wordModuleAttemptRequest){
        return new WordModuleAttemptResponse(wordModuleService.takeTheTest(wordModuleAttemptRequest));
    }
}
