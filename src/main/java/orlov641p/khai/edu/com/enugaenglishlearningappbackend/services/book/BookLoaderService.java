package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.book;

import org.springframework.web.multipart.MultipartFile;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.Book;

import java.io.File;

public interface BookLoaderService {
    Book loadBookFromMultipartFile(MultipartFile file, Book book) throws Exception ;
    Book loadBookFromFile(File file, Book book) throws Exception ;
}
