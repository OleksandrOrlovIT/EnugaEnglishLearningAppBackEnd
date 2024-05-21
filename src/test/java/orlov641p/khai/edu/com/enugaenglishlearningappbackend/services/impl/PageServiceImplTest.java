package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Page;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.PageRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PageServiceImplTest {

    @Mock
    private PageRepository pageRepository;

    @InjectMocks
    private PageServiceImpl pageService;

    private Page page;

    @BeforeEach
    void setUp() {
        page = new Page();
        page.setId(1L);
        page.setPageNumber(1);
    }

    @Test
    void whenFindAll_thenCorrect() {
        when(pageRepository.findAll()).thenReturn(List.of(new Page(), new Page()));

        assertEquals(2, pageService.findAll().size());
    }


    @Test
    void givenNullId_whenFindById_thenException() {
        assertThrows(IllegalArgumentException.class, () -> pageService.findById(null));
    }

    @Test
    void givenNonExistingId_whenFindById_thenException() {
        when(pageRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> pageService.findById(1L));
    }

    @Test
    void givenExistingId_whenFindById_thenCorrect() {
        when(pageRepository.findById(anyLong())).thenReturn(Optional.ofNullable(page));

        assertEquals(page, pageService.findById(page.getId()));
    }

    @Test
    void givenNull_whenCreate_thenException() {
        assertThrows(IllegalArgumentException.class, () -> pageService.create(null));
    }

    @Test
    void givenValid_whenCreate_thenCorrect() {
        assertDoesNotThrow(() -> pageService.create(page));
    }

    @Test
    void givenNull_whenUpdate_thenException() {
        assertThrows(IllegalArgumentException.class, () -> pageService.update(null));
    }

    @Test
    void givenNonExistingID_whenUpdate_thenException() {
        when(pageRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> pageService.update(page));
    }

    @Test
    void givenValid_whenUpdate_thenCorrect() {
        Page updatedPage = new Page();
        updatedPage.setId(page.getId());
        updatedPage.setPageNumber(page.getPageNumber() + 1);

        when(pageRepository.findById(anyLong())).thenReturn(Optional.ofNullable(page));
        when(pageRepository.save(any())).thenReturn(updatedPage);

        Page result = pageService.update(updatedPage);
        assertNotNull(result);
        assertEquals(updatedPage.getPageNumber(), result.getPageNumber());
    }

    @Test
    void givenNull_whenDelete_thenException(){
        assertThrows(IllegalArgumentException.class, () -> pageService.delete(null));
        verify(pageRepository, times(0)).deleteById(any());
    }

    @Test
    void givenValid_whenDelete_ThenWorked(){
        pageService.delete(page);

        verify(pageRepository, times(1)).delete(any());
    }

    @Test
    void givenNullId_whenDeleteById_thenException(){
        assertThrows(IllegalArgumentException.class, () -> pageService.deleteById(null));
        verify(pageRepository, times(0)).deleteById(any());
    }

    @Test
    void givenValid_whenDeleteById_ThenWorked(){
        pageService.deleteById(page.getId());

        verify(pageRepository, times(1)).deleteById(any());
    }
}