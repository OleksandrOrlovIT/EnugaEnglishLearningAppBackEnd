package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.engtest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;

@Repository
public interface EnglishTestRepository extends JpaRepository<EnglishTest, Long> {

}
