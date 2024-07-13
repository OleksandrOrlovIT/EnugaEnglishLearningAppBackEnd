package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.response.CustomPairResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.CustomPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule.CustomPairService;

import java.net.URI;
import java.util.List;

import static orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.mapper.CustomPairMapper.customPairListToCustomPairResponseList;
import static orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.mapper.CustomPairMapper.customPairToCustomPairResponse;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class CustomPairController {
    private final CustomPairService customPairService;

    @GetMapping("/custom-pairs")
    public List<CustomPairResponse> retrieveCustomPairs(){
        return customPairListToCustomPairResponseList(customPairService.findAll());
    }

    @GetMapping("/custom-pair/{id}")
    public CustomPairResponse retrieveCustomPairById(@PathVariable Long id){
        return customPairToCustomPairResponse(customPairService.findById(id));
    }

    @GetMapping("/custom-pairs/word-module/{wordModuleId}")
    public List<CustomPairResponse> retrieveCustomPairsByWordModuleId(@PathVariable Long wordModuleId){
        return customPairListToCustomPairResponseList(customPairService.getCustomPairsByWordModuleId(wordModuleId));
    }

    @PostMapping("/custom-pair")
    public ResponseEntity<CustomPairResponse> createCustomPair(@RequestBody CustomPair customPair){
        CustomPair savedCustomPair = customPairService.create(customPair);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCustomPair.getId())
                .toUri();

        return ResponseEntity.created(location).body(customPairToCustomPairResponse(savedCustomPair));
    }

    @PutMapping("/custom-pair/{id}")
    public CustomPairResponse updateCustomPair(@PathVariable Long id, @RequestBody CustomPair customPair){
        if(customPairService.findById(id) == null){
            return null;
        }

        return customPairToCustomPairResponse(customPairService.update(customPair));
    }

    @DeleteMapping("/custom-pair/{id}")
    public ResponseEntity<Void> deleteCustomPair(@PathVariable Long id){
        customPairService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}