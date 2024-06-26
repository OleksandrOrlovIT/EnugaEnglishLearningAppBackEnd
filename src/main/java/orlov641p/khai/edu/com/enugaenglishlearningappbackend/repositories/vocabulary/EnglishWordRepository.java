package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.vocabulary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.vocabulary.EnglishWord;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnglishWordRepository extends JpaRepository<EnglishWord, Long> {
    Optional<EnglishWord> findByWord(String word);

    boolean existsByWord(String word);

    List<EnglishWord> findAllByWordIgnoreCase(String word);
}
