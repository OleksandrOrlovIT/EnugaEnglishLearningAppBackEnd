package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services;

import org.springframework.web.multipart.MultipartFile;

public interface BookLoaderService {
    void loadBookFromFile(MultipartFile file, String title, String author) throws Exception ;
}
