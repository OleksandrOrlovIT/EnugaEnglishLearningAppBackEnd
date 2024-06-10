package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.book;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.book.dto.response.PageResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.Page;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.book.PageService;

import java.net.URI;
import java.util.List;

import static orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.book.dto.mapper.PageMapper.pageListToPageResponseList;
import static orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.book.dto.mapper.PageMapper.pageToPageResponse;

@AllArgsConstructor
@RestController
@RequestMapping("/v1")
public class PageController {
    private final PageService pageService;

    @GetMapping("/pages")
    public List<PageResponse> retrievePages(){
        List<Page> pages = pageService.findAll();
        return pageListToPageResponseList(pages);
    }

    @GetMapping("/page/{id}")
    public PageResponse retrievePageById(@PathVariable Long id){
        Page page = pageService.findById(id);
        return pageToPageResponse(page);
    }

    @GetMapping("book/{bookId}/page/{pageNumber}")
    public PageResponse retrievePageByBookIdAndPageNumber(@PathVariable Long bookId, @PathVariable Integer pageNumber){
        Page page = pageService.getPageByBookAndNumber(bookId, pageNumber);

        return pageToPageResponse(page);
    }

    @PostMapping("/page")
    public ResponseEntity<PageResponse> createPage(@RequestBody Page page){
        Page savedPage = pageService.create(page);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPage.getId())
                .toUri();

        PageResponse pageResponse = pageToPageResponse(savedPage);

        return ResponseEntity.created(location).body(pageResponse);
    }

    @PutMapping("/page/{id}")
    public PageResponse updatePage(@PathVariable Long id, @RequestBody Page page){
        if(pageService.findById(id) == null) {
            return null;
        }

        Page updatedPage = pageService.update(page);
        return pageToPageResponse(updatedPage);
    }

    @DeleteMapping("/page/{id}")
    public ResponseEntity<Void> deletePage(@PathVariable Long id){
        pageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
