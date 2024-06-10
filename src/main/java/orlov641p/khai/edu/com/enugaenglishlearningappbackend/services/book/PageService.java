package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.book;

import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.Page;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.CrudService;

public interface PageService extends CrudService<Page, Long> {
    Page getPageByBookAndNumber(Long bookId, Integer pageNumber);
}
