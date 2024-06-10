package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.book;

import org.springframework.data.jpa.repository.JpaRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
