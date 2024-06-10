package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.book.dto.mapper;

import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.book.dto.response.BookResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.book.dto.response.PageResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.book.Page;

import java.util.ArrayList;
import java.util.List;

public class PageMapper {

    public static PageResponse pageToPageResponse(Page page){
        PageResponse pageResponse = new PageResponse();
        pageResponse.setId(page.getId());
        pageResponse.setPageNumber(page.getPageNumber());
        pageResponse.setContent(page.getContent());

        BookResponse bookResponse = BookMapper.fromBookToBookResponse(page.getBook());
        pageResponse.setBookResponse(bookResponse);

        return pageResponse;
    }

    public static List<PageResponse> pageListToPageResponseList(List<Page> pages){
        List<PageResponse> pageResponses = new ArrayList<>();

        for(Page page : pages){
            pageResponses.add(pageToPageResponse(page));
        }

        return pageResponses;
    }
}
