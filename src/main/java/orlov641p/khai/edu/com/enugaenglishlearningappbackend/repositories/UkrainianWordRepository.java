package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.UkrainianWord;

@Repository
public interface UkrainianWordRepository extends JpaRepository<UkrainianWord, Long> {
}
