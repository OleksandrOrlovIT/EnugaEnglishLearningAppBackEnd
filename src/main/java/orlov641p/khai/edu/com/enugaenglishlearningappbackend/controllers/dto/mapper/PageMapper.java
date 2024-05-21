package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.dto.mapper;

import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.dto.response.BookResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.dto.response.PageResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Page;

import java.util.ArrayList;
import java.util.List;

import static orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.dto.mapper.BookMapper.fromBookToBookResponse;

public class PageMapper {

    public static PageResponse pageToPageResponse(Page page){
        PageResponse pageResponse = new PageResponse();
        pageResponse.setPageNumber(page.getPageNumber());
        pageResponse.setContent(page.getContent());

        BookResponse bookResponse = fromBookToBookResponse(page.getBook());
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
