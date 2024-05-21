package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services;

import org.springframework.web.multipart.MultipartFile;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Book;

import java.io.File;

public interface BookLoaderService {
    void loadBookFromMultipartFile(MultipartFile file, Book book) throws Exception ;
    void loadBookFromFile(File file, Book book) throws Exception ;
}
