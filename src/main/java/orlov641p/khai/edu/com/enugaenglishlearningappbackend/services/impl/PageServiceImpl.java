package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Page;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.PageRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.PageService;

import java.util.List;

@AllArgsConstructor
@Service
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;

    @Override
    public List<Page> findAll() {
        return pageRepository.findAll();
    }

    @Override
    public Page findById(Long id) {
        checkPageIdNull(id);

        Page page = pageRepository.findById(id).orElse(null);

        if(page == null){
            throw new EntityNotFoundException("Page with id = " + id + " doesn't exist");
        }

        return page;
    }

    @Override
    public Page create(Page page) {
        checkPageNull(page);

        return pageRepository.save(page);
    }

    @Override
    public Page update(Page page) {
        checkPageNull(page);

        findById(page.getId());

        return pageRepository.save(page);
    }

    @Override
    public void delete(Page page) {
        checkPageNull(page);

        pageRepository.delete(page);
    }

    @Override
    public void deleteById(Long id) {
        checkPageIdNull(id);

        pageRepository.deleteById(id);
    }

    @Override
    public Page getFirst() {
        Pageable pageable = Pageable
                .ofSize(1)
                .first();

        org.springframework.data.domain.Page<Page> pages = pageRepository.findAll(pageable);

        return pages.hasContent() ? pages.getContent().get(0) : null;
    }

    private void checkPageNull(Page page){
        if (page == null) {
            throw new IllegalArgumentException("Page can`t be null");
        }
    }

    private void checkPageIdNull(Long id){
        if(id == null){
            throw new IllegalArgumentException("Page id can`t be null");
        }
    }
}
