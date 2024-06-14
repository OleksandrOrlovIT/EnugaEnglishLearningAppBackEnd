package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SimplePageRequest {
    private Integer pageSize;

    private Integer pageNumber;
}
