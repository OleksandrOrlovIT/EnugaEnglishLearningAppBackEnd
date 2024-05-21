package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.dto.mapper;

import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.dto.response.BookResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Book;

import java.util.List;

public class BookMapper {
    public static BookResponse fromBookToBookResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getBookGenre(),
                book.getPages().size()
        );
    }

    public static List<BookResponse> bookListToBookResponseList(List<Book> books){
        return books.stream()
                .map(BookMapper::fromBookToBookResponse)
                .toList();
    }
}