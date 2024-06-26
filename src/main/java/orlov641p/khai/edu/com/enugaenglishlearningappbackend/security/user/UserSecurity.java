package orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.enums.Visibility;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;

@AllArgsConstructor
@Component
public class UserSecurity {

    private final UserService userService;

    public User getLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userService.getUserByEmail(authentication.getName());
    }

    public boolean hasRoleAdminOrIsSelf(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }

        User user = userService.findById(id);
        return user != null && user.getUsername().equals(currentUsername);
    }

    public boolean hasRoleAdminOrIsSelfOrPublicVisibility(WordModule wordModule){
        return wordModule.getVisibility().equals(Visibility.PUBLIC) ||
                hasRoleAdminOrIsSelf(wordModule.getUser().getId());
    }

    public boolean authUserTriesToTakeTest(Long userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        User requestUser = userService.findById(userId);

        return currentUsername.equals(requestUser.getUsername());
    }
}