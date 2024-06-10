package orlov641p.khai.edu.com.enugaenglishlearningappbackend.bootstrap.book;

import org.springframework.boot.CommandLineRunner;

public interface BookLoader extends CommandLineRunner {
    void loadBooks();
}
