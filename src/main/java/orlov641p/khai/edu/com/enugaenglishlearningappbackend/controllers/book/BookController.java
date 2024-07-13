package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.book;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.book.dto.response.BookResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.Book;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.book.BookLoaderService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.book.BookService;

import java.net.URI;
import java.util.List;

import static orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.book.dto.mapper.BookMapper.bookListToBookResponseList;
import static orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.book.dto.mapper.BookMapper.fromBookToBookResponse;

@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_USER_WITH_SUBSCRIPTION')")
@RestController
@RequestMapping("/v1")
public class BookController {

    private final BookService bookService;
    private final BookLoaderService bookLoaderService;

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

    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    @PostMapping(path = "/book")
    public ResponseEntity<BookResponse> createBook(@RequestPart("book") Book book,
                                                   @RequestParam("file") MultipartFile file) throws Exception {
        Book savedBook = bookLoaderService.loadBookFromMultipartFile(file, book);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();

        BookResponse bookResponse = fromBookToBookResponse(savedBook);

        return ResponseEntity.created(location).body(bookResponse);
    }

    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    @PutMapping("/book/{id}")
    public BookResponse updateBook(@PathVariable Long id, @RequestBody Book book){
        if(bookService.findById(id) == null) {
            return null;
        }

        book.setId(id);

        Book updatedBook = bookService.update(book);
        return fromBookToBookResponse(updatedBook);
    }

    @PreAuthorize("hasRole('ROLE_ENGLISH_TEACHER_USER')")
    @DeleteMapping("/book/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
