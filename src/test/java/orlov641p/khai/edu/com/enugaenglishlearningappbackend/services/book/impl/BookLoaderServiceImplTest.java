package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.book.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.Book;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.Page;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.book.BookService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.book.PageService;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookLoaderServiceImplTest {
    @Mock
    private BookService bookService;

    @Mock
    private PageService pageService;

    @InjectMocks
    private BookLoaderServiceImpl bookLoaderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadBookFromMultipartFile() throws Exception {
        String content = "Line1\nLine2\nLine3\nLine4\nLine5\nLine6\nLine7\nLine8\nLine9\nLine10\n" +
                "Line11\nLine12\nLine13\nLine14\nLine15\nLine16\nLine17\nLine18\nLine19\nLine20\n" +
                "Line21\n";
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", content.getBytes(StandardCharsets.UTF_8));
        Book book = new Book();

        bookLoaderService.loadBookFromMultipartFile(file, book);

        verify(bookService).create(book);
        verify(pageService, times(2)).create(any(Page.class));
    }

    @Test
    public void testLoadBookFromFile() throws Exception {
        String content = "Line1\nLine2\nLine3\nLine4\nLine5\nLine6\nLine7\nLine8\nLine9\nLine10\n" +
                "Line11\nLine12\nLine13\nLine14\nLine15\nLine16\nLine17\nLine18\nLine19\nLine20\n" +
                "Line21\n";
        File tempFile = File.createTempFile("test", ".txt");
        tempFile.deleteOnExit();
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(content.getBytes(StandardCharsets.UTF_8));
        }
        Book book = new Book();

        bookLoaderService.loadBookFromFile(tempFile, book);

        verify(bookService).create(book);
        verify(pageService, times(2)).create(any(Page.class));
    }
}