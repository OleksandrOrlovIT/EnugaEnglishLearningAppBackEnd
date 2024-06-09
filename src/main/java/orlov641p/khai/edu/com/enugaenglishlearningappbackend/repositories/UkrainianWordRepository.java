package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishWord;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.UkrainianWord;

import java.util.List;
import java.util.Optional;

@Repository
public interface UkrainianWordRepository extends JpaRepository<UkrainianWord, Long> {

    Optional<UkrainianWord> findByWord(String word);

    boolean existsByWord(String word);

    List<UkrainianWord> findAllByWordIgnoreCase(String word);
}
