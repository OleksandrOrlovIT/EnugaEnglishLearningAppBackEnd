package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.Rule;

/**
 * The RuleRepository interface extends JpaRepository to provide methods for
 * interacting with Rule entities in the database.
 */

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {

}
