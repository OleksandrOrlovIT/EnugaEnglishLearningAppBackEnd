package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.CustomPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.enums.Visibility;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class WordModuleRequest {

    private String moduleName;

    private Visibility visibility;

    private Long userId;

    private List<CustomPair> customPairs;
}
