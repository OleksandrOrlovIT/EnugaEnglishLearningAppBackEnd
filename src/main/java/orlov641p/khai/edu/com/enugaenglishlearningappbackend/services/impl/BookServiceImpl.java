package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Book;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.BookRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.BookService;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        checkBookIdNull(id);

        Book book = bookRepository.findById(id).orElse(null);

        if(book == null){
            throw new EntityNotFoundException("Book with id = " + id + " doesn't exist");
        }

        return book;
    }

    @Override
    public Book create(Book book) {
        checkBookNull(book);

        return bookRepository.save(book);
    }

    @Override
    public Book update(Book book) {
        checkBookNull(book);

        findById(book.getId());

        return bookRepository.save(book);
    }

    @Override
    public void delete(Book book) {
        checkBookNull(book);

        bookRepository.delete(book);
    }

    @Override
    public void deleteById(Long id) {
        checkBookIdNull(id);

        bookRepository.deleteById(id);
    }

    @Override
    public Book getFirst() {
        Pageable pageable = Pageable
                .ofSize(1)
                .first();

        Page<Book> books = bookRepository.findAll(pageable);

        return books.hasContent() ? books.getContent().get(0) : null;
    }

    private void checkBookNull(Book book){
        if (book == null) {
            throw new IllegalArgumentException("Book can`t be null");
        }
    }

    private void checkBookIdNull(Long id){
        if(id == null){
            throw new IllegalArgumentException("Book id can`t be null");
        }
    }
}
