package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.book.dto.mapper;

import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.book.dto.response.BookResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.Book;

import java.util.List;

public class BookMapper {
    public static BookResponse fromBookToBookResponse(Book book) {
        if(book == null){
            return null;
        }

        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getBookGenre(),
                book.getPages() != null ? book.getPages().size() : 0
        );
    }

    public static List<BookResponse> bookListToBookResponseList(List<Book> books){
        return books.stream()
                .map(BookMapper::fromBookToBookResponse)
                .toList();
    }
}