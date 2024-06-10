package orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
