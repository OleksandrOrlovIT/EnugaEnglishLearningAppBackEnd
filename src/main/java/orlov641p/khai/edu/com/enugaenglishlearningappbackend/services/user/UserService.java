package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.Role;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.CrudService;

import java.util.Set;

public interface UserService extends CrudService<User, Long> {
    UserDetailsService userDetailsService();

    User getCurrentUser();

    User getUserByEmail(String email);

    User updateUserWithoutRoles(User user);

    User upgradeUserSubscription(User user);

    Page<User> getUserPage(Pageable pageable);

    User addRoles(Long userId, Set<Role> roles);

    User deleteRoles(Long userId, Set<Role> roles);
}
