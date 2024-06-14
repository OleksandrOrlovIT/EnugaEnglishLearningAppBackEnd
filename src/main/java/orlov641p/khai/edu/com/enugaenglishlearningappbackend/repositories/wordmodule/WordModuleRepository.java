package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.wordmodule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;

import java.util.List;

@Repository
public interface WordModuleRepository extends JpaRepository<WordModule, Long> {
    List<WordModule> findByUserIdOrderByIdDesc(Long userId);

    @Query("SELECT wm FROM WordModule wm WHERE wm.visibility = 0 AND wm.user.id <> :userId")
    List<WordModule> findByVisibilityPublicAndUserIdNot(@Param("userId") Long userId);
}