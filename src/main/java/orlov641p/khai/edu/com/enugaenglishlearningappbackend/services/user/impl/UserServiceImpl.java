package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.rule.Rule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.Role;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.student.EnglishStudent;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.teacher.EnglishTeacher;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.user.UserRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.user.student.EnglishStudentRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.user.teacher.EnglishTeacherRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EnglishTeacherRepository englishTeacherRepository;
    private final EnglishStudentRepository englishStudentRepository;

    private final Map<Long, Long> roleRevertMap = new ConcurrentHashMap<>();

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

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of(Role.ROLE_USER_WITHOUT_SUBSCRIPTION));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        checkUserNull(user);

        User foundUser = findById(user.getId());

        if (!passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(foundUser.getPassword());
        }

        return userRepository.save(user);
    }

    @Override
    public User addRoles(Long userId, Set<Role> roles) {
        User user = findById(userId);

        user.getRoles().addAll(roles);

        return userRepository.save(user);
    }

    @Override
    public User deleteRoles(Long userId, Set<Role> roles) {
        User user = findById(userId);

        user.getRoles().removeAll(roles);

        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        checkUserNull(user);

        deleteFromOtherTables(user);

        userRepository.delete(user);
    }

    @Override
    public void deleteById(Long id) {
        checkUserIdNull(id);

        User user = findById(id);

        deleteFromOtherTables(user);

        userRepository.deleteById(id);
    }

    private void deleteFromOtherTables(User user){
        EnglishTeacher englishTeacher = englishTeacherRepository.findByUser(user);

        if(englishTeacher != null){
            englishTeacherRepository.deleteById(englishTeacher.getId());
        }

        EnglishStudent englishStudent = englishStudentRepository.findByUser(user);

        if(englishStudent != null){
            englishStudentRepository.deleteById(englishStudent.getId());
        }
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
    public User updateUserWithoutRoles(User user) {
        User foundUser = findById(user.getId());

        user.setRoles(foundUser.getRoles());

        return update(user);
    }

    @Override
    public User getCurrentUser() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByEmail(email);
    }

    @Override
    public Page<User> getUserPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User upgradeUserSubscription(User user) {
        checkUserNull(user);
        checkUserIdNull(user.getId());
        User foundUser = findById(user.getId());

        foundUser.getRoles().add(Role.ROLE_USER_WITH_SUBSCRIPTION);
        userRepository.save(user);

        roleRevertMap.put(foundUser.getId(), System.currentTimeMillis() + 300000);

        return foundUser;
    }

    @Scheduled(fixedRate = 300000)
    protected void revertUserRoles() {
        long currentTime = System.currentTimeMillis();
        for (Map.Entry<Long, Long> entry : roleRevertMap.entrySet()) {
            if (currentTime >= entry.getValue()) {
                revertUserWithSubscriptionRole(entry.getKey());
                roleRevertMap.remove(entry.getKey());
            }
        }
    }

    protected void revertUserWithSubscriptionRole(Long userId) {
        log.info("Reverting subscription from user with id = {}", userId);
        User user = findById(userId);
        user.getRoles().remove(Role.ROLE_USER_WITH_SUBSCRIPTION);
        userRepository.save(user);
    }

    private void checkUserNull(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User can't be null");
        }
    }

    private void checkUserIdNull(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("User id can`t be null");
        }
    }
}
