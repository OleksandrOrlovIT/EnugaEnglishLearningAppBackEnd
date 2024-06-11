package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.rule.Rule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.Role;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.user.UserRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        checkUserIdNull(id);

        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id = " + id + " doesn't exist"));
    }

    @Override
    public User create(User user) {
        checkUserNull(user);

        if(user.getRoles() == null || user.getRoles().isEmpty()){
            user.setRoles(Set.of(Role.ROLE_USER_WITHOUT_SUBSCRIPTION));
        }

        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        checkUserNull(user);

        findById(user.getId());

        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        checkUserNull(user);

        userRepository.delete(user);
    }

    @Override
    public void deleteById(Long id) {
        checkUserIdNull(id);

        userRepository.deleteById(id);
    }

    @Override
    public User getFirst() {
        Pageable pageable = Pageable
                .ofSize(1)
                .first();

        Page<User> users = userRepository.findAll(pageable);

        return users.hasContent() ? users.getContent().get(0) : null;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getUserByEmail;
    }

    @Override
    public User getCurrentUser() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByEmail(email);
    }

    private void checkUserNull(User user){
        if(user == null){
            throw new IllegalArgumentException("User can't be null");
        }
    }

    private void checkUserIdNull(Long id){
        if(id == null){
            throw new IllegalArgumentException("User id can`t be null");
        }
    }
}
