package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.testattempt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.TestAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;

import java.util.List;
import java.util.Optional;

public interface TestAttemptRepository extends JpaRepository<TestAttempt, Long> {
    List<TestAttempt> findByUser(User user);

    Page<TestAttempt> findByUser(User user, Pageable pageable);

    @Query("SELECT ta FROM TestAttempt ta WHERE ta.user = :user AND ta.englishTest.id = :testId ORDER BY ta.successPercentage DESC")
    List<TestAttempt> findTopByUserAndEnglishTestOrderBySuccessPercentageDesc(@Param("user") User user,
                                                                              @Param("testId") Long testId);

    @Query("SELECT ta FROM TestAttempt ta WHERE ta.user = :user AND ta.englishTest.id = :testId ORDER BY ta.attemptDate DESC")
    List<TestAttempt> findNewestByUserAndEnglishTestOrderByAttemptDateDesc(@Param("user") User user, @Param("testId") Long testId);
}
