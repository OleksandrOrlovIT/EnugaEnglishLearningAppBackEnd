package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.vocabulary.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.EnglishWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.vocabulary.EnglishWordRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.vocabulary.EnglishWordService;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class EnglishWordServiceImpl implements EnglishWordService {

    private final EnglishWordRepository englishWordRepository;

    @Override
    public List<EnglishWord> findAll() {
        return englishWordRepository.findAll();
    }

    @Override
    public EnglishWord findById(Long id) {
        checkEnglishWordIdNull(id);

        EnglishWord englishWord = englishWordRepository.findById(id).orElse(null);

        if(englishWord == null){
            throw new EntityNotFoundException("EnglishWord with id = " + id + " doesn't exist");
        }

        return englishWord;
    }

    @Override
    public EnglishWord create(EnglishWord englishWord) {
        checkEnglishWordNull(englishWord);

        return englishWordRepository.save(englishWord);
    }

    @Override
    public EnglishWord update(EnglishWord englishWord) {
        checkEnglishWordNull(englishWord);

        findById(englishWord.getId());

        return englishWordRepository.save(englishWord);
    }

    @Override
    public void delete(EnglishWord englishWord) {
        checkEnglishWordNull(englishWord);

        englishWordRepository.delete(englishWord);
    }

    @Override
    public void deleteById(Long id) {
        checkEnglishWordIdNull(id);

        englishWordRepository.deleteById(id);
    }

    @Override
    public EnglishWord getFirst() {
        Pageable pageable = Pageable
                .ofSize(1)
                .first();

        Page<EnglishWord> englishWords = englishWordRepository.findAll(pageable);

        return englishWords.hasContent() ? englishWords.getContent().get(0) : null;
    }

    @Override
    public Page<EnglishWord> findPageEnglishWords(Pageable pageable) {
        return englishWordRepository.findAll(pageable);
    }

    @Override
    public EnglishWord findByWord(String word) {
        return englishWordRepository.findByWord(word).orElse(null);
    }

    @Override
    public boolean existsByWord(String word) {
        return englishWordRepository.existsByWord(word);
    }

    @Override
    public List<EnglishWord> findAllByWordIgnoreCase(String word) {
        return englishWordRepository.findAllByWordIgnoreCase(word);
    }

    private void checkEnglishWordNull(EnglishWord englishWord){
        if (englishWord == null) {
            throw new IllegalArgumentException("EnglishWord can`t be null");
        }
    }

    private void checkEnglishWordIdNull(Long id){
        if(id == null){
            throw new IllegalArgumentException("EnglishWord id can`t be null");
        }
    }
}
