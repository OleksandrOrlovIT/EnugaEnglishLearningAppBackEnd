package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.CustomPair;

@Setter
@Getter
@NoArgsConstructor
public class CustomPairResponse {

    public Long id;

    public String word;

    public String translation;

    public Long wordModuleId;

    public CustomPairResponse(CustomPair customPair) {
        this.id = customPair.getId();
        this.word = customPair.getWord();
        this.translation = customPair.getTranslation();
        this.wordModuleId = customPair.getWordModule().getId();
    }
}
