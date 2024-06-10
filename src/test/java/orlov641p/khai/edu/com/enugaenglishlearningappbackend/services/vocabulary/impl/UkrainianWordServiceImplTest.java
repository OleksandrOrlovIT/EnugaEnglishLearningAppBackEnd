package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.vocabulary.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.UkrainianWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.vocabulary.UkrainianWordRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UkrainianWordServiceImplTest {

    @Mock
    private UkrainianWordRepository ukrainianWordRepository;

    @InjectMocks
    private UkrainianWordServiceImpl ukrainianWordService;

    private UkrainianWord ukrainianWord;

    @BeforeEach
    void setUp() {
        ukrainianWord = new UkrainianWord();
        ukrainianWord.setId(1L);
        ukrainianWord.setWord("TEXT");
    }

    @Test
    void whenFindAll_thenCorrect() {
        when(ukrainianWordRepository.findAll()).thenReturn(List.of(new UkrainianWord(), new UkrainianWord()));

        assertEquals(2, ukrainianWordService.findAll().size());
    }


    @Test
    void givenNullId_whenFindById_thenException() {
        assertThrows(IllegalArgumentException.class, () -> ukrainianWordService.findById(null));
    }

    @Test
    void givenNonExistingId_whenFindById_thenException() {
        when(ukrainianWordRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ukrainianWordService.findById(1L));
    }

    @Test
    void givenExistingId_whenFindById_thenCorrect() {
        when(ukrainianWordRepository.findById(anyLong())).thenReturn(Optional.ofNullable(ukrainianWord));

        assertEquals(ukrainianWord, ukrainianWordService.findById(ukrainianWord.getId()));
    }

    @Test
    void givenNull_whenCreate_thenException() {
        assertThrows(IllegalArgumentException.class, () -> ukrainianWordService.create(null));
    }

    @Test
    void givenValid_whenCreate_thenCorrect() {
        assertDoesNotThrow(() -> ukrainianWordService.create(ukrainianWord));
    }

    @Test
    void givenNull_whenUpdate_thenException() {
        assertThrows(IllegalArgumentException.class, () -> ukrainianWordService.update(null));
    }

    @Test
    void givenNonExistingID_whenUpdate_thenException() {
        when(ukrainianWordRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ukrainianWordService.update(ukrainianWord));
    }

    @Test
    void givenValid_whenUpdate_thenCorrect() {
        UkrainianWord updatedWord = new UkrainianWord();
        updatedWord.setId(ukrainianWord.getId());
        updatedWord.setWord("UPDATED");

        when(ukrainianWordRepository.findById(anyLong())).thenReturn(Optional.ofNullable(ukrainianWord));
        when(ukrainianWordRepository.save(any())).thenReturn(updatedWord);

        UkrainianWord result = ukrainianWordService.update(updatedWord);
        assertNotNull(result);
        assertEquals(updatedWord.getWord(), result.getWord());
    }

    @Test
    void givenNull_whenDelete_thenException(){
        assertThrows(IllegalArgumentException.class, () -> ukrainianWordService.delete(null));
        verify(ukrainianWordRepository, times(0)).deleteById(any());
    }

    @Test
    void givenValid_whenDelete_ThenWorked(){
        ukrainianWordService.delete(ukrainianWord);

        verify(ukrainianWordRepository, times(1)).delete(any());
    }

    @Test
    void givenNullId_whenDeleteById_thenException(){
        assertThrows(IllegalArgumentException.class, () -> ukrainianWordService.deleteById(null));
        verify(ukrainianWordRepository, times(0)).deleteById(any());
    }

    @Test
    void givenValid_whenDeleteById_ThenWorked(){
        ukrainianWordService.deleteById(ukrainianWord.getId());

        verify(ukrainianWordRepository, times(1)).deleteById(any());
    }
}