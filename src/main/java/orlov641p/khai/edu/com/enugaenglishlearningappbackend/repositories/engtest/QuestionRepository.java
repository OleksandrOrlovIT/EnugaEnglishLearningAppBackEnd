package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.engtest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.Question;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByEnglishTestId(Long englishTestId);
}