package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.book;

import org.springframework.data.jpa.repository.JpaRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.Book;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.Page;

public interface PageRepository extends JpaRepository<Page, Long> {
    Page findByBookAndPageNumber(Book book, int pageNumber);
}
