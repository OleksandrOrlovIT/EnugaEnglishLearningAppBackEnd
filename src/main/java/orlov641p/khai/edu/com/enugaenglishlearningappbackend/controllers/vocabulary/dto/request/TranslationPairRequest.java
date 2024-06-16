package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.vocabulary.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TranslationPairRequest {
    private String ukrainianWord;
    private String englishWord;
}
