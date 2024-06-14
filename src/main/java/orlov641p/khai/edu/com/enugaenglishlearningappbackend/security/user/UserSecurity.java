package orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;

@AllArgsConstructor
@Component
public class UserSecurity {

    private final UserService userService;

    public boolean hasRoleAdminOrIsSelf(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }

        User user = userService.findById(id);
        return user != null && user.getUsername().equals(currentUsername);
    }
}