package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Book;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("TITLE");
        book.setAuthor("AUTHOR");
    }

    @Test
    void whenFindAll_thenCorrect() {
        when(bookRepository.findAll()).thenReturn(List.of(new Book(), new Book()));

        assertEquals(2, bookService.findAll().size());
    }


    @Test
    void givenNullId_whenFindById_thenException() {
        assertThrows(IllegalArgumentException.class, () -> bookService.findById(null));
    }

    @Test
    void givenNonExistingId_whenFindById_thenException() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.findById(1L));
    }

    @Test
    void givenExistingId_whenFindById_thenCorrect() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.ofNullable(book));

        assertEquals(book, bookService.findById(book.getId()));
    }

    @Test
    void givenNull_whenCreate_thenException() {
        assertThrows(IllegalArgumentException.class, () -> bookService.create(null));
    }

    @Test
    void givenValid_whenCreate_thenCorrect() {
        assertDoesNotThrow(() -> bookService.create(book));
    }

    @Test
    void givenNull_whenUpdate_thenException() {
        assertThrows(IllegalArgumentException.class, () -> bookService.update(null));
    }

    @Test
    void givenNonExistingID_whenUpdate_thenException() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.update(book));
    }

    @Test
    void givenValid_whenUpdate_thenCorrect() {
        Book updatedBook = new Book();
        updatedBook.setId(book.getId());
        updatedBook.setTitle("UPDATED " + book.getTitle());

        when(bookRepository.findById(anyLong())).thenReturn(Optional.ofNullable(book));
        when(bookRepository.save(any())).thenReturn(updatedBook);

        Book result = bookService.update(updatedBook);
        assertNotNull(result);
        assertEquals(updatedBook.getTitle(), result.getTitle());
    }

    @Test
    void givenNull_whenDelete_thenException(){
        assertThrows(IllegalArgumentException.class, () -> bookService.delete(null));
        verify(bookRepository, times(0)).deleteById(any());
    }

    @Test
    void givenValid_whenDelete_ThenWorked(){
        bookService.delete(book);

        verify(bookRepository, times(1)).delete(any());
    }

    @Test
    void givenNullId_whenDeleteById_thenException(){
        assertThrows(IllegalArgumentException.class, () -> bookService.deleteById(null));
        verify(bookRepository, times(0)).deleteById(any());
    }

    @Test
    void givenValid_whenDeleteById_ThenWorked(){
        bookService.deleteById(book.getId());

        verify(bookRepository, times(1)).deleteById(any());
    }
}