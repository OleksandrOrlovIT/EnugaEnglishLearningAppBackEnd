package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.dto.response.BookResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Book;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.BookService;

import java.net.URI;
import java.util.List;

import static orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.dto.mapper.BookMapper.bookListToBookResponseList;
import static orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.dto.mapper.BookMapper.fromBookToBookResponse;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class BookController {
    private final BookService bookService;

    @GetMapping("/books")
    public List<BookResponse> retrieveBooks(){
        List<Book> books = bookService.findAll();
        return bookListToBookResponseList(books);
    }

    @GetMapping("/book/{id}")
    public BookResponse retrieveBookById(@PathVariable Long id){
        Book book = bookService.findById(id);
        return fromBookToBookResponse(book);
    }

    @PostMapping("/book")
    public ResponseEntity<BookResponse> createBook(@RequestBody Book book){
        Book savedBook = bookService.create(book);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();

        BookResponse bookResponse = fromBookToBookResponse(savedBook);

        return ResponseEntity.created(location).body(bookResponse);
    }

    @PutMapping("/book/{id}")
    public BookResponse updateBook(@PathVariable Long id, @RequestBody Book book){
        if(bookService.findById(id) == null) {
            return null;
        }

        Book updatedBook = bookService.update(book);
        return fromBookToBookResponse(updatedBook);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
