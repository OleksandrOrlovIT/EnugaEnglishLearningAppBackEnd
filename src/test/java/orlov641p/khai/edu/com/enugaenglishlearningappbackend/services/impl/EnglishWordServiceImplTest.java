package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.EnglishWordRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class EnglishWordServiceImplTest {
    @Mock
    private EnglishWordRepository englishWordRepository;

    @InjectMocks
    private EnglishWordServiceImpl englishWordService;

    private EnglishWord englishWord;

    @BeforeEach
    void setUp() {
        englishWord = new EnglishWord();
        englishWord.setId(1L);
        englishWord.setWord("TEXT");
    }

    @Test
    void whenFindAll_thenCorrect() {
        when(englishWordRepository.findAll()).thenReturn(List.of(new EnglishWord(), new EnglishWord()));

        assertEquals(2, englishWordService.findAll().size());
    }


    @Test
    void givenNullId_whenFindById_thenException() {
        assertThrows(IllegalArgumentException.class, () -> englishWordService.findById(null));
    }

    @Test
    void givenNonExistingId_whenFindById_thenException() {
        when(englishWordRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> englishWordService.findById(1L));
    }

    @Test
    void givenExistingId_whenFindById_thenCorrect() {
        when(englishWordRepository.findById(anyLong())).thenReturn(Optional.ofNullable(englishWord));

        assertEquals(englishWord, englishWordService.findById(englishWord.getId()));
    }

    @Test
    void givenNull_whenCreate_thenException() {
        assertThrows(IllegalArgumentException.class, () -> englishWordService.create(null));
    }

    @Test
    void givenValid_whenCreate_thenCorrect() {
        assertDoesNotThrow(() -> englishWordService.create(englishWord));
    }

    @Test
    void givenNull_whenUpdate_thenException() {
        assertThrows(IllegalArgumentException.class, () -> englishWordService.update(null));
    }

    @Test
    void givenNonExistingID_whenUpdate_thenException() {
        when(englishWordRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> englishWordService.update(englishWord));
    }

    @Test
    void givenValid_whenUpdate_thenCorrect() {
        EnglishWord updatedWord = new EnglishWord();
        updatedWord.setId(englishWord.getId());
        updatedWord.setWord("UPDATED");

        when(englishWordRepository.findById(anyLong())).thenReturn(Optional.ofNullable(englishWord));
        when(englishWordRepository.save(any())).thenReturn(updatedWord);

        EnglishWord result = englishWordService.update(updatedWord);
        assertNotNull(result);
        assertEquals(updatedWord.getWord(), result.getWord());
    }

    @Test
    void givenNull_whenDelete_thenException(){
        assertThrows(IllegalArgumentException.class, () -> englishWordService.delete(null));
        verify(englishWordRepository, times(0)).deleteById(any());
    }

    @Test
    void givenValid_whenDelete_ThenWorked(){
        englishWordService.delete(englishWord);

        verify(englishWordRepository, times(1)).delete(any());
    }

    @Test
    void givenNullId_whenDeleteById_thenException(){
        assertThrows(IllegalArgumentException.class, () -> englishWordService.deleteById(null));
        verify(englishWordRepository, times(0)).deleteById(any());
    }

    @Test
    void givenValid_whenDeleteById_ThenWorked(){
        englishWordService.deleteById(englishWord.getId());

        verify(englishWordRepository, times(1)).deleteById(any());
    }
}