package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.enums.Visibility;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class WordModuleResponse {

    public Long id;
    public String moduleName;
    public Visibility visibility;
    public Long userId;
    public List<CustomPairResponse> customPairs;

    public WordModuleResponse(WordModule wordModule) {
        this.id = wordModule.getId();
        this.moduleName = wordModule.getModuleName();
        this.visibility = wordModule.getVisibility();
        this.userId = wordModule.getUser().getId();

        this.customPairs = wordModule.getCustomPairs()
                .stream()
                .map(CustomPairResponse::new)
                .collect(Collectors.toList());
    }
}
