package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.book.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.enums.BookGenre;

@NoArgsConstructor
@Setter
@Getter
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private BookGenre bookGenre;
    private int pagesCount;

    public BookResponse(Long id, String title, String author, BookGenre bookGenre, int pagesCount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.bookGenre = bookGenre;
        this.pagesCount = pagesCount;
    }
}
