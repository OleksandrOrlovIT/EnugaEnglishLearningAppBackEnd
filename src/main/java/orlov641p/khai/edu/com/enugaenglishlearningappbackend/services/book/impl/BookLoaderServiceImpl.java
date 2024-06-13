package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.book.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.Book;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.Page;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.book.BookLoaderService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.book.BookService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.book.PageService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class BookLoaderServiceImpl implements BookLoaderService {

    private final BookService bookService;
    private final PageService pageService;

    @Override
    public Book loadBookFromMultipartFile(MultipartFile file, Book book) throws Exception {
        return loadBook(file.getInputStream(), book);
    }

    @Override
    public Book loadBookFromFile(File file, Book book) throws Exception {
        return loadBook(new FileInputStream(file), book);
    }

    @Transactional
    protected Book loadBook(InputStream inputStream, Book book) throws Exception {
        book = bookService.create(book);
        List<Page> pages = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder pageContent = new StringBuilder();
            String line;
            int pageNumber = 1;
            int linesPerPage = 20;
            int currentLineCount = 0;

            while ((line = reader.readLine()) != null) {
                pageContent.append(line).append("\n");
                currentLineCount++;
                if (currentLineCount >= linesPerPage) {
                    Page page = new Page();
                    page.setPageNumber(pageNumber++);
                    page.setContent(pageContent.toString());
                    page.setBook(book);
                    pageService.create(page);
                    pages.add(page);
                    pageContent.setLength(0);
                    currentLineCount = 0;
                }
            }

            if (!pageContent.isEmpty()) {
                Page page = new Page();
                page.setPageNumber(pageNumber);
                page.setContent(pageContent.toString());
                page.setBook(book);
                pageService.create(page);
                pages.add(page);
            }

        } catch (Exception e) {
            bookService.delete(book);
            for (Page page : pages) {
                pageService.delete(page);
            }
            throw e;
        }

        return book;
    }
}