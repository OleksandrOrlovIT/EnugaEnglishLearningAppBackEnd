package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.EnglishWordRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.EnglishWordService;

import java.util.List;

@AllArgsConstructor
@Service
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