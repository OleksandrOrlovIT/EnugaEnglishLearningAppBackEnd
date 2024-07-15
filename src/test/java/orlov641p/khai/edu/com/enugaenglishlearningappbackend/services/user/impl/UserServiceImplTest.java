package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.Role;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.student.EnglishStudent;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.teacher.EnglishTeacher;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.user.UserRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.user.student.EnglishStudentRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.user.teacher.EnglishTeacherRepository;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EnglishTeacherRepository englishTeacherRepository;
    @Mock
    private EnglishStudentRepository englishStudentRepository;
    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserServiceImpl userService;

    private static final long ID = 1L;
    private static final String EMAIL = "EMAIL@email.com";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";
    private static final String PASSWORD = "123";

    private User user;

    @BeforeEach
    void setUp(){
        SecurityContextHolder.setContext(securityContext);

        user = User.builder()
                .id(ID)
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .password(PASSWORD)
                .roles(Set.of(Role.ROLE_USER_WITHOUT_SUBSCRIPTION))
                .build();
    }

    @Test
    void whenFindAll_thenSuccess(){
        List<User> users = List.of(new User(), new User(), new User());

        when(userRepository.findAll()).thenReturn(users);

        assertEquals(users, userService.findAll());
    }

    @Test
    void whenFindById_thenEntityNotFound(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        var e = assertThrows(EntityNotFoundException.class, () -> userService.findById(ID));

        assertEquals("User with id = " + ID + " doesn't exist", e.getMessage());
    }

    @Test
    void whenFindById_thenSuccess(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        assertEquals(user, userService.findById(ID));
    }

    @Test
    void givenNull_whenCreate_thenException(){
        var e = assertThrows(IllegalArgumentException.class, () -> userService.create(null));

        assertEquals("User can't be null", e.getMessage());
    }

    @Test
    void whenCreate_thenSuccessAndCreatedNewRole(){
        user.setRoles(null);

        when(passwordEncoder.encode(any())).thenReturn(PASSWORD);
        when(userRepository.save(any())).thenReturn(user);

        User savedUser = userService.create(user);
        assertEquals(user, savedUser);
        assertTrue(savedUser.getRoles().contains(Role.ROLE_USER_WITHOUT_SUBSCRIPTION));
    }

    @Test
    void whenCreate_thenSuccessAndHasNewRoles(){
        user.setRoles(Set.of(Role.ROLE_USER_WITHOUT_SUBSCRIPTION, Role.ROLE_ADMIN));

        when(passwordEncoder.encode(any())).thenReturn(PASSWORD);
        when(userRepository.save(any())).thenReturn(user);

        User savedUser = userService.create(user);
        assertEquals(user, savedUser);
        assertTrue(savedUser.getRoles().contains(Role.ROLE_USER_WITHOUT_SUBSCRIPTION));
        assertTrue(savedUser.getRoles().contains(Role.ROLE_ADMIN));
    }

    @Test
    void givenNull_whenUpdate_thenException(){
        var e = assertThrows(IllegalArgumentException.class, () -> userService.update(null));
        assertEquals("User can't be null", e.getMessage());
    }

    @Test
    void givenEntityNotFound_whenUpdate_thenException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        var e = assertThrows(EntityNotFoundException.class, () -> userService.update(user));
        assertEquals("User with id = " + ID + " doesn't exist", e.getMessage());
    }

    @Test
    void givenNewPassword_whenUpdate_thenPasswordUpdated(){
        String newPassword = "NEW PASSWORD";
        String encodedPassword = "ENCODED PASSWORD";
        user.setPassword(newPassword);

        when(passwordEncoder.matches(any(), any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn(encodedPassword);
        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User updatedUser = userService.update(user);
        assertEquals(user, updatedUser);
        assertNotEquals(newPassword, updatedUser.getPassword());
        assertEquals(encodedPassword, updatedUser.getPassword());
    }

    @Test
    void givenOldPassword_whenUpdate_thenSuccess(){
        String newPassword = "NEW PASSWORD";
        String encodedPassword = "ENCODED PASSWORD";
        user.setPassword(newPassword);

        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User updatedUser = userService.update(user);
        assertEquals(user, updatedUser);
        assertNotEquals(encodedPassword, updatedUser.getPassword());
        assertEquals(newPassword, updatedUser.getPassword());
    }

    @Test
    void givenEntityNotFound_whenAddRoles_thenException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        var e = assertThrows(EntityNotFoundException.class, () -> userService.addRoles(user.getId(), null));

        assertEquals("User with id = " + ID + " doesn't exist", e.getMessage());
    }

    @Test
    void givenNullRoles_whenAddRoles_thenException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        var e = assertThrows(IllegalArgumentException.class, () -> userService.addRoles(user.getId(), null));

        assertEquals("Roles can't be null", e.getMessage());
    }

    @Test
    void givenRoles_whenDeleteRoles_thenSuccess(){
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER_WITHOUT_SUBSCRIPTION);
        user.setRoles(roles);

        roles.add(Role.ROLE_ADMIN);
        User userWithRoles = new User();
        userWithRoles.setRoles(roles);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(userWithRoles);

        assertEquals(userWithRoles.getRoles(), userService.addRoles(user.getId(), roles).getRoles());
    }

    @Test
    void givenEntityNotFound_whenDeleteRoles_thenException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        var e = assertThrows(EntityNotFoundException.class, () -> userService.deleteRoles(user.getId(), null));

        assertEquals("User with id = " + ID + " doesn't exist", e.getMessage());
    }

    @Test
    void givenNullRoles_whenDeleteRoles_thenException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        var e = assertThrows(IllegalArgumentException.class, () -> userService.deleteRoles(user.getId(), null));

        assertEquals("Roles can't be null", e.getMessage());
    }

    @Test
    void givenRoles_whenAddRoles_thenSuccess(){
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER_WITHOUT_SUBSCRIPTION);
        roles.add(Role.ROLE_ADMIN);
        user.setRoles(roles);

        roles.remove(Role.ROLE_ADMIN);
        User userWithoutRoles = new User();
        userWithoutRoles.setRoles(roles);

        roles = Set.of(Role.ROLE_ADMIN);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(userWithoutRoles);

        assertEquals(userWithoutRoles.getRoles(), userService.addRoles(user.getId(), roles).getRoles());
    }

    @Test
    void givenNull_whenDelete_thenException(){
        var e = assertThrows(IllegalArgumentException.class, () -> userService.delete(null));

        assertEquals("User can't be null", e.getMessage());
    }

    @Test
    void whenDelete_thenSuccess(){
        when(englishTeacherRepository.findByUser(any())).thenReturn(EnglishTeacher.builder().id(ID).build());
        when(englishStudentRepository.findByUser(any())).thenReturn(EnglishStudent.builder().id(ID).build());

        userService.delete(user);

        verify(userRepository, times(1)).delete(any());
    }

    @Test
    void givenNull_whenDeleteById_thenException(){
        var e = assertThrows(IllegalArgumentException.class, () -> userService.deleteById(null));

        assertEquals("User id can`t be null", e.getMessage());
    }

    @Test
    void givenEntityNotFound_whenDeleteById_thenException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        var e = assertThrows(EntityNotFoundException.class, () -> userService.deleteById(user.getId()));

        assertEquals("User with id = " + ID + " doesn't exist", e.getMessage());
    }

    @Test
    void whenDeleteById_thenSuccess(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(englishTeacherRepository.findByUser(any())).thenReturn(EnglishTeacher.builder().id(ID).build());
        when(englishStudentRepository.findByUser(any())).thenReturn(EnglishStudent.builder().id(ID).build());

        userService.deleteById(user.getId());

        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testGetFirst() {
        User user = new User();
        List<User> users = Collections.singletonList(user);
        Page<User> page = new PageImpl<>(users);

        Pageable pageable = PageRequest.of(0, 1);
        when(userRepository.findAll(pageable)).thenReturn(page);

        User result = userService.getFirst();
        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void testUserDetailsService() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(user);

        UserDetailsService userDetailsService = userService.userDetailsService();
        User result = (User) userDetailsService.loadUserByUsername(email);
        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void testUpdateUserWithoutRoles() {
        User user = new User();
        user.setId(1L);
        user.setRoles(Collections.singleton(Role.ROLE_USER_WITHOUT_SUBSCRIPTION));

        User foundUser = new User();
        foundUser.setId(1L);
        foundUser.setRoles(Collections.singleton(Role.ROLE_ADMIN));

        when(userRepository.findById(1L)).thenReturn(Optional.of(foundUser));
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.updateUserWithoutRoles(user);
        assertNotNull(result);
        assertEquals(foundUser.getRoles(), result.getRoles());
    }

    @Test
    void testGetCurrentUser() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(user);

        User result = userService.getCurrentUser();
        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void testGetUserPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> page = new PageImpl<>(Collections.singletonList(new User()));

        when(userRepository.findAll(pageable)).thenReturn(page);

        Page<User> result = userService.getUserPage(pageable);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void givenNull_whenUpgradeUserSubscription_thenException(){
        var e = assertThrows(IllegalArgumentException.class, () -> userService.upgradeUserSubscription(null));

        assertEquals("User can't be null", e.getMessage());
    }

    @Test
    void givenNullId_whenUpgradeUserSubscription_thenException(){
        user.setId(null);

        var e = assertThrows(IllegalArgumentException.class, () -> userService.upgradeUserSubscription(user));

        assertEquals("User id can`t be null", e.getMessage());
    }

    @Test
    void givenEntityNotFound_whenUpgradeUserSubscription_thenException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        var e = assertThrows(EntityNotFoundException.class, () -> userService.upgradeUserSubscription(user));

        assertEquals("User with id = " + ID + " doesn't exist", e.getMessage());
    }

    @Test
    void whenUpgradeUserSubscription_thenSuccess(){
        Set<Role> roles = Set.of(Role.ROLE_USER_WITHOUT_SUBSCRIPTION, Role.ROLE_USER_WITH_SUBSCRIPTION);
        user.setRoles(new HashSet<>(user.getRoles()));

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        assertEquals(roles, userService.upgradeUserSubscription(user).getRoles());
    }

    private void setRoleRevertMap(Long userId, Long timestamp) throws Exception {
        Field field = UserServiceImpl.class.getDeclaredField("roleRevertMap");
        field.setAccessible(true);
        Map<Long, Long> roleRevertMap = (Map<Long, Long>) field.get(userService);
        roleRevertMap.put(userId, timestamp);
    }

    @Test
    void givenDeletedId_whenRevertUserRoles_thenException() throws Exception {
        user.setRoles(new HashSet<>(user.getRoles()));
        setRoleRevertMap(ID, System.currentTimeMillis() - 1000);

        when(userRepository.findById(ID)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> userService.revertUserRoles());

        verify(userRepository).findById(ID);

        Field field = UserServiceImpl.class.getDeclaredField("roleRevertMap");
        field.setAccessible(true);
        Map<Long, Long> roleRevertMap = (Map<Long, Long>) field.get(userService);
        assertFalse(roleRevertMap.containsKey(ID));
    }

    @Test
    void whenRevertUserRoles_thenSuccess() throws Exception {
        user.setRoles(new HashSet<>(user.getRoles()));
        setRoleRevertMap(ID, System.currentTimeMillis() - 1000);

        when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.revertUserRoles();

        verify(userRepository).findById(ID);
        verify(userRepository).save(user);

        Field field = UserServiceImpl.class.getDeclaredField("roleRevertMap");
        field.setAccessible(true);
        Map<Long, Long> roleRevertMap = (Map<Long, Long>) field.get(userService);
        assertFalse(roleRevertMap.containsKey(ID));

        assertFalse(user.getRoles().contains(Role.ROLE_USER_WITH_SUBSCRIPTION));
    }
}