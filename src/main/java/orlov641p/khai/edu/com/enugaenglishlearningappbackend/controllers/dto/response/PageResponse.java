package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PageResponse {

    private Long id;
    private Integer pageNumber;
    private String content;
    private BookResponse bookResponse;

    public PageResponse(int pageNumber, String content, BookResponse bookResponse) {
        this.pageNumber = pageNumber;
        this.content = content;
        this.bookResponse = bookResponse;
    }
}