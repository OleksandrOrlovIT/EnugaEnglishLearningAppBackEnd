package orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.book.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.book.BookLoader;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.Book;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.enums.BookGenre;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.book.BookLoaderService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.book.BookService;

import java.io.File;

@Profile("!test")
@Slf4j
@AllArgsConstructor
@Component
public class BookLoaderImpl implements BookLoader {

    private final BookLoaderService bookLoaderService;
    private final BookService bookService;

    @Override
    public void run(String... args) throws Exception {
        if (bookService.getFirst() == null) {
            loadBooks();
            log.info("Books were loaded");
        } else {
            log.info("Books loading were skipped");
        }
    }

    @Override
    public void loadBooks() {
        File romeoAndJulietFile = new File("src/main/resources/static/RomeoAndJuliet.txt");

        Book romeoAndJuliet = Book.builder()
                .title("Romeo and Juliet")
                .author("William Shakespeare")
                .bookGenre(BookGenre.DRAMA)
                .build();

        try {
            bookLoaderService.loadBookFromFile(romeoAndJulietFile, romeoAndJuliet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
