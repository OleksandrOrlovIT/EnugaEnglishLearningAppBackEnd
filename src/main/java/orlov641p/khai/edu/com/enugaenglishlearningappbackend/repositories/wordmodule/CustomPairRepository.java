package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.wordmodule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.CustomPair;

import java.util.List;

@Repository
public interface CustomPairRepository extends JpaRepository<CustomPair, Long> {
    List<CustomPair> findByWordModuleId(Long id);
}
