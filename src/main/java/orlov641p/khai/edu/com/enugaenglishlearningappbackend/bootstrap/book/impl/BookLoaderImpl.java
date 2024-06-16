package orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.book.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.book.BookLoader;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.Book;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.enums.BookGenre;
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
    public void loadBooks() {
        if (bookService.getFirst() == null) {
            saveBooks();
            log.info("Books were loaded");
        } else {
            log.info("Books loading were skipped");
        }
    }

    private void saveBooks() {
        Book romeoAndJuliet = createBook("Romeo and Juliet", "William Shakespeare", BookGenre.DRAMA);
        saveBook("src/main/resources/static/RomeoAndJuliet.txt", romeoAndJuliet);

        Book mobyDick = createBook("Moby Dick", "Herman Melville", BookGenre.FICTION);
        saveBook("src/main/resources/static/modydick.txt", mobyDick);

        Book middlemarch = createBook("Middlemarch", "George Eliot", BookGenre.FICTION);
        saveBook("src/main/resources/static/middlemarch.txt", middlemarch);

        Book frankenstein = createBook("Frankenstein", "Mary Wollstonecraft Shelley", BookGenre.FICTION);
        saveBook("src/main/resources/static/frankenstein.txt", frankenstein);

        Book holmesSherlock = createBook("The Adventures of Sherlock Holmes", "Arthur Conan Doyle", BookGenre.FICTION);
        saveBook("src/main/resources/static/sherlokHolmes.txt", holmesSherlock);
    }

    private void saveBook(String filePath, Book book){
        try {
            File file = new File(filePath);
            bookLoaderService.loadBookFromFile(file, book);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Book createBook(String title, String author, BookGenre bookGenre){
        return Book.builder()
                .title(title)
                .author(author)
                .bookGenre(bookGenre)
                .build();
    }
}
