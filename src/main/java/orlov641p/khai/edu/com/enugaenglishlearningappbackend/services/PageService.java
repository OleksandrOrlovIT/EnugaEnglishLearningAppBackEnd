package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services;

import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Page;

public interface PageService extends CrudService<Page, Long> {
    Page getPageByBookAndNumber(Long bookId, Integer pageNumber);
}
