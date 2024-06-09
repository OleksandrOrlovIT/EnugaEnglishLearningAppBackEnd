package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.TranslationPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.UkrainianWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.UkrainianWordRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.UkrainianWordService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class UkrainianWordServiceImpl implements UkrainianWordService {

    private final UkrainianWordRepository ukrainianWordRepository;

    @Override
    public List<UkrainianWord> findAll() {
        return ukrainianWordRepository.findAll();
    }

    @Override
    public UkrainianWord findById(Long id) {
        checkUkrainianWordIdNull(id);

        UkrainianWord ukrainianWord = ukrainianWordRepository.findById(id).orElse(null);

        if(ukrainianWord == null){
            throw new EntityNotFoundException("UkrainianWord with id = " + id + " doesn't exist");
        }

        return ukrainianWord;
    }

    @Override
    public UkrainianWord create(UkrainianWord ukrainianWord) {
        checkUkrainianWordNull(ukrainianWord);

        return ukrainianWordRepository.save(ukrainianWord);
    }

    @Override
    public UkrainianWord update(UkrainianWord ukrainianWord) {
        checkUkrainianWordNull(ukrainianWord);

        findById(ukrainianWord.getId());

        return ukrainianWordRepository.save(ukrainianWord);
    }

    @Override
    public void delete(UkrainianWord ukrainianWord) {
        checkUkrainianWordNull(ukrainianWord);

        ukrainianWordRepository.delete(ukrainianWord);
    }

    @Override
    public void deleteById(Long id) {
        checkUkrainianWordIdNull(id);

        ukrainianWordRepository.deleteById(id);
    }

    @Override
    public Page<UkrainianWord> findPageUkrainianWords(Pageable pageable) {
        return ukrainianWordRepository.findAll(pageable);
    }

    @Override
    public UkrainianWord getFirst() {
        Pageable pageable = Pageable
                .ofSize(1)
                .first();

        Page<UkrainianWord> ukrainianWords = findPageUkrainianWords(pageable);

        return ukrainianWords.hasContent() ? ukrainianWords.getContent().get(0) : null;
    }

    @Override
    public UkrainianWord findByWord(String word) {
        return ukrainianWordRepository.findByWord(word).orElse(null);
    }

    @Override
    public List<UkrainianWord> findAllByWordIgnoreCase(String word) {
        return ukrainianWordRepository.findAllByWordIgnoreCase(word);
    }

    @Override
    public boolean existsByWord(String word) {
        return ukrainianWordRepository.existsByWord(word);
    }

    private void checkUkrainianWordNull(UkrainianWord ukrainianWord){
        if (ukrainianWord == null) {
            throw new IllegalArgumentException("UkrainianWord can`t be null");
        }
    }

    private void checkUkrainianWordIdNull(Long id){
        if(id == null){
            throw new IllegalArgumentException("UkrainianWord id can`t be null");
        }
    }
}
