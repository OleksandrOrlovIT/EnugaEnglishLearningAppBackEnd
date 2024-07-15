package orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.wordmodule;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.enums.Visibility;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.security.user.UserSecurity;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule.WordModuleService;

@AllArgsConstructor
@Component
public class WordModuleSecurity {

    private final UserSecurity userSecurity;
    private final WordModuleService wordModuleService;

    public boolean IsLoggedUserAdminOrWordModuleOwner(Long id){
        User user = userSecurity.getLoggedUser();

        return userSecurity.hasRoleAdminOrIsSelf(user.getId()) || wordModuleService.findById(id).getUser().equals(user);
    }

    public boolean hasRoleAdminOrIsSelfOrPublicVisibilityByWordModuleId(Long id) {
        WordModule wordModule = wordModuleService.findById(id);
        return hasRoleAdminOrIsSelfOrPublicVisibility(wordModule);
    }

    public boolean hasRoleAdminOrIsSelfOrPublicVisibility(WordModule wordModule){
        return wordModule.getVisibility().equals(Visibility.PUBLIC) ||
                userSecurity.hasRoleAdminOrIsSelf(wordModule.getUser().getId());
    }

    public boolean isAuthUserTriesToTakeWordModule(Long id){
        return userSecurity.isPassedUserIdMatchesLoggedUser(id);
    }

    public boolean IsPublicWordModuleOrAdminOrOwner(Long userId, Long wordModuleId){
        return wordModuleService.findById(wordModuleId).getVisibility().equals(Visibility.PUBLIC) ||
                userSecurity.hasRoleAdminOrIsSelf(userId);
    }
}
