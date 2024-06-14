package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.testattempt.wordmodule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.wordmodule.WordModuleAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;

import java.util.List;

public interface WordModuleAttemptRepository extends JpaRepository<WordModuleAttempt, Long> {
    List<WordModuleAttempt> findByUser(User user);

    Page<WordModuleAttempt> findByUser(User user, Pageable pageable);

    @Query("SELECT wma FROM WordModuleAttempt wma WHERE wma.user = :user AND wma.wordModule.id = :wordModuleId ORDER BY wma.successPercentage DESC")
    List<WordModuleAttempt> findTopByUserAndWordModuleOrderBySuccessPercentageDesc(@Param("user") User user,
                                                                                   @Param("wordModuleId") Long wordModuleId);

    @Query("SELECT wma FROM WordModuleAttempt wma WHERE wma.user = :user AND wma.wordModule.id = :wordModuleId ORDER BY wma.attemptDate DESC")
    List<WordModuleAttempt> findNewestByUserAndWordModuleOrderByAttemptDateDesc(@Param("user") User user,
                                                                                @Param("wordModuleId") Long wordModuleId);
}
