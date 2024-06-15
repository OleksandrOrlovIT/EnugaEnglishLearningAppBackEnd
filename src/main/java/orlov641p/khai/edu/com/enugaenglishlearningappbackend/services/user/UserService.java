package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.CrudService;

public interface UserService extends CrudService<User, Long> {
    UserDetailsService userDetailsService();

    User getCurrentUser();

    User getUserByEmail(String email);

    User updateUserWithoutRoles(User user);

    User upgradeUserSubscription(User user);

    Page<User> getUserPage(Pageable pageable);
}
