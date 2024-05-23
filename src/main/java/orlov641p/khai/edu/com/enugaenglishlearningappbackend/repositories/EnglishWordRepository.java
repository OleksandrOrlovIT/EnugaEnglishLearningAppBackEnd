package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.EnglishWord;

import java.util.Optional;

@Repository
public interface EnglishWordRepository extends JpaRepository<EnglishWord, Long> {
    Optional<EnglishWord> findByWord(String word);

    boolean existsByWord(String word);
}
