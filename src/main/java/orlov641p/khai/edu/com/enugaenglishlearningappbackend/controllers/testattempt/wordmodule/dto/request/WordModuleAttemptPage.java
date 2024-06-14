package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class WordModuleAttemptPage {
    public Long userId;

    public Integer pageSize;

    public Integer pageNumber;
}
