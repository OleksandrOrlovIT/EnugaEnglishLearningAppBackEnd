package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.UkrainianWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.UkrainianWordRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.UkrainianWordService;

import java.util.List;

@AllArgsConstructor
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
