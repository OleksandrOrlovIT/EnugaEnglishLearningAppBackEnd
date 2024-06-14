package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.wordmodule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;

@Repository
public interface WordModuleRepository extends JpaRepository<WordModule, Long> {
}
