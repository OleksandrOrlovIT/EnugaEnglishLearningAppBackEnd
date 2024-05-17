package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.TranslationPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.UkrainianWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.TranslationPairRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TranslationPairServiceImplTest {
    @Mock
    private TranslationPairRepository translationPairRepository;

    @InjectMocks
    private TranslationPairServiceImpl translationPairService;

    private TranslationPair translationPair;

    private UkrainianWord ukrainianWord;
    private EnglishWord englishWord;

    @BeforeEach
    void setUp() {
        englishWord = new EnglishWord();
        englishWord.setId(1L);
        englishWord.setWord("ENGLISHTEXT");

        ukrainianWord = new UkrainianWord();
        ukrainianWord.setId(1L);
        ukrainianWord.setWord("UKRAINIANTEXT");

        translationPair = new TranslationPair();
        translationPair.setId(1L);
        translationPair.setUkrainianWord(ukrainianWord);
        translationPair.setEnglishWord(englishWord);
    }

    @Test
    void whenFindAll_thenCorrect() {
        when(translationPairRepository.findAll()).thenReturn(List.of(new TranslationPair(), new TranslationPair()));

        assertEquals(2, translationPairService.findAll().size());
    }


    @Test
    void givenNullId_whenFindById_thenException() {
        assertThrows(IllegalArgumentException.class, () -> translationPairService.findById(null));
    }

    @Test
    void givenNonExistingId_whenFindById_thenException() {
        when(translationPairRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> translationPairService.findById(1L));
    }

    @Test
    void givenExistingId_whenFindById_thenCorrect() {
        when(translationPairRepository.findById(anyLong())).thenReturn(Optional.ofNullable(translationPair));

        assertEquals(translationPair, translationPairService.findById(ukrainianWord.getId()));
    }

    @Test
    void givenNull_whenCreate_thenException() {
        assertThrows(IllegalArgumentException.class, () -> translationPairService.create(null));
    }

    @Test
    void givenValid_whenCreate_thenCorrect() {
        assertDoesNotThrow(() -> translationPairService.create(translationPair));
    }

    @Test
    void givenNull_whenUpdate_thenException() {
        assertThrows(IllegalArgumentException.class, () -> translationPairService.update(null));
    }

    @Test
    void givenNonExistingID_whenUpdate_thenException() {
        when(translationPairRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> translationPairService.update(translationPair));
    }

    @Test
    void givenValid_whenUpdate_thenCorrect() {
        TranslationPair updatedPair = new TranslationPair();
        updatedPair.setId(translationPair.getId());

        when(translationPairRepository.findById(anyLong())).thenReturn(Optional.ofNullable(translationPair));
        when(translationPairRepository.save(any())).thenReturn(updatedPair);

        TranslationPair result = translationPairService.update(updatedPair);
        assertNotNull(result);
    }

    @Test
    void givenNull_whenDelete_thenException(){
        assertThrows(IllegalArgumentException.class, () -> translationPairService.delete(null));
        verify(translationPairRepository, times(0)).deleteById(any());
    }

    @Test
    void givenValid_whenDelete_ThenWorked(){
        translationPairService.delete(translationPair);

        verify(translationPairRepository, times(1)).delete(any());
    }

    @Test
    void givenNullId_whenDeleteById_thenException(){
        assertThrows(IllegalArgumentException.class, () -> translationPairService.deleteById(null));
        verify(translationPairRepository, times(0)).deleteById(any());
    }

    @Test
    void givenValid_whenDeleteById_ThenWorked(){
        translationPairService.deleteById(ukrainianWord.getId());

        verify(translationPairRepository, times(1)).deleteById(any());
    }
}