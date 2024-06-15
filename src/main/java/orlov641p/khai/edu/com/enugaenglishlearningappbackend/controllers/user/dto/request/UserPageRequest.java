package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPageRequest {
    Integer pageNumber;
    Integer pageSize;
}
