package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.response.CustomPairResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.CustomPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule.CustomPairService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class CustomPairController {
    private final CustomPairService customPairService;

    @GetMapping("/custom-pairs")
    public List<CustomPairResponse> retrieveCustomPairs(){
        List<CustomPairResponse> customPairResponses = new ArrayList<>();

        for(CustomPair customPair : customPairService.findAll()){
            customPairResponses.add(new CustomPairResponse(customPair));
        }

        return customPairResponses;
    }

    @GetMapping("/custom-pair/{id}")
    public CustomPairResponse retrieveCustomPairById(@PathVariable Long id){
        return new CustomPairResponse(customPairService.findById(id));
    }

    @GetMapping("/custom-pairs/word-module/{wordModuleId}")
    public List<CustomPairResponse> retrieveCustomPairsByWordModuleId(@PathVariable Long wordModuleId){
        List<CustomPairResponse> customPairResponses = new ArrayList<>();

        for(CustomPair customPair : customPairService.getCustomPairsByWordModuleId(wordModuleId)){
            customPairResponses.add(new CustomPairResponse(customPair));
        }

        return customPairResponses;
    }

    @PostMapping("/custom-pair")
    public ResponseEntity<CustomPairResponse> createCustomPair(@RequestBody CustomPair customPair){
        CustomPair savedCustomPair = customPairService.create(customPair);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCustomPair.getId())
                .toUri();

        return ResponseEntity.created(location).body(new CustomPairResponse(savedCustomPair));
    }

    @PutMapping("/custom-pair/{id}")
    public CustomPairResponse updateCustomPair(@PathVariable Long id, @RequestBody CustomPair customPair){
        if(customPairService.findById(id) == null){
            return null;
        }

        return new CustomPairResponse(customPairService.update(customPair));
    }

    @DeleteMapping("/custom-pair/{id}")
    public ResponseEntity<Void> deleteCustomPair(@PathVariable Long id){
        customPairService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

