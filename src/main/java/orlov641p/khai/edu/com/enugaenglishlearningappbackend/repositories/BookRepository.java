package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
