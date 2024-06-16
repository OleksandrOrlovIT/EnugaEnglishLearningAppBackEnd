package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.teacher.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.teacher.dto.request.EnglishTeacherCreateRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.user.teacher.dto.request.EnglishTeacherUpdateRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.Role;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.teacher.EnglishTeacher;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.user.student.EnglishStudentRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.user.teacher.EnglishTeacherRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.teacher.EnglishTeacherService;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class EnglishTeacherServiceImpl implements EnglishTeacherService {

    private final EnglishTeacherRepository englishTeacherRepository;
    private final UserService userService;
    private final EnglishStudentRepository englishStudentRepository;

    @Override
    public List<EnglishTeacher> findAll() {
        return englishTeacherRepository.findAll();
    }

    @Override
    public Page<EnglishTeacher> getEnglishTeacherPage(Pageable pageable) {
        return englishTeacherRepository.findAll(pageable);
    }

    @Override
    public EnglishTeacher findById(Long id) {
        checkEnglishTeacherIdNull(id);

        return englishTeacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find english teacher with id = " + id));
    }

    @Override
    public EnglishTeacher create(EnglishTeacher englishTeacher) {
        checkEnglishTeacherNull(englishTeacher);
        checkEnglishTeacherHasTeacherRole(englishTeacher);

        EnglishTeacher savedTeacher = englishTeacherRepository.save(englishTeacher);
        User user = savedTeacher.getUser();

        if(!user.getRoles().contains(Role.ROLE_ENGLISH_STUDENT_USER)
                || !user.getRoles().contains(Role.ROLE_ENGLISH_TEACHER_USER)){
            userService.addRoles(user.getId(), Set.of(Role.ROLE_ENGLISH_STUDENT_USER,
                    Role.ROLE_ENGLISH_TEACHER_USER, Role.ROLE_USER_WITH_SUBSCRIPTION));
        }

        return savedTeacher;
    }

    @Override
    public EnglishTeacher createFromRequest(EnglishTeacherCreateRequest englishTeacherCreateRequest) {
        User user = checkRequiredUserIsAlreadyTeacherOrStudent(englishTeacherCreateRequest.getUserId());

        EnglishTeacher englishTeacher = EnglishTeacher
                .builder()
                .user(user)
                .build();

        return create(englishTeacher);
    }

    @Override
    public EnglishTeacher update(EnglishTeacher englishTeacher) {
        checkEnglishTeacherNull(englishTeacher);
        checkEnglishTeacherIdNull(englishTeacher.getId());
        checkEnglishTeacherHasTeacherRole(englishTeacher);

        EnglishTeacher foundTeacher = findById(englishTeacher.getId());
        if(!foundTeacher.getUser().getId().equals(englishTeacher.getUser().getId())){
            User oldUser = foundTeacher.getUser();

            userService.deleteRoles(oldUser.getId(), Set.of(Role.ROLE_USER_WITH_SUBSCRIPTION,
                    Role.ROLE_ENGLISH_STUDENT_USER, Role.ROLE_ENGLISH_TEACHER_USER));

            User newUser = englishTeacher.getUser();

            userService.addRoles(newUser.getId(), Set.of(Role.ROLE_USER_WITH_SUBSCRIPTION,
                    Role.ROLE_ENGLISH_STUDENT_USER, Role.ROLE_ENGLISH_TEACHER_USER));
        }

        return englishTeacherRepository.save(englishTeacher);
    }

    @Override
    public EnglishTeacher updateFromRequest(EnglishTeacherUpdateRequest englishTeacherUpdateRequest) {
        User user = checkRequiredUserIsAlreadyTeacherOrStudent(englishTeacherUpdateRequest.getUserId());

        EnglishTeacher foundTeacher = findById(englishTeacherUpdateRequest.getEnglishTeacherId());

        foundTeacher.setUser(user);

        return update(foundTeacher);
    }

    private User checkRequiredUserIsAlreadyTeacherOrStudent(Long userId){
        User user = userService.findById(userId);

        if(findByUser(user) != null){
            throw new EntityExistsException("User already exists with id = " + user.getId());
        }

        if(englishStudentRepository.findByUser(user) != null){
            throw new IllegalArgumentException("User already exists as a student with id = " + user.getId());
        }

        user.getRoles().add(Role.ROLE_USER_WITH_SUBSCRIPTION);
        user.getRoles().add(Role.ROLE_ENGLISH_STUDENT_USER);
        user.getRoles().add(Role.ROLE_ENGLISH_TEACHER_USER);

        return userService.update(user);
    }

    @Override
    public void delete(EnglishTeacher englishTeacher) {
        checkEnglishTeacherNull(englishTeacher);

        englishTeacherRepository.delete(englishTeacher);
    }

    @Override
    public void deleteById(Long id) {
        checkEnglishTeacherIdNull(id);

        englishTeacherRepository.deleteById(id);
    }

    @Override
    public EnglishTeacher getFirst() {
        Pageable pageable = Pageable
                .ofSize(1)
                .first();

        Page<EnglishTeacher> englishTeachers = englishTeacherRepository.findAll(pageable);

        return englishTeachers.hasContent() ? englishTeachers.getContent().get(0) : null;
    }

    @Override
    public EnglishTeacher findByUser(User user) {
        return englishTeacherRepository.findByUser(user);
    }

    @Override
    public EnglishTeacher findByUserId(Long userId) {
        return findByUser(userService.findById(userId));
    }

    private void checkEnglishTeacherNull(EnglishTeacher englishTeacher){
        if(englishTeacher == null){
            throw new IllegalArgumentException("EnglishTeacher can't be null");
        }
    }

    private void checkEnglishTeacherIdNull(Long id){
        if(id == null){
            throw new IllegalArgumentException("EnglishTeacher id can`t be null");
        }
    }

    private void checkEnglishTeacherHasTeacherRole(EnglishTeacher englishTeacher){
        if(!englishTeacher.getUser().getRoles().contains(Role.ROLE_ENGLISH_TEACHER_USER)){
            throw new IllegalArgumentException("English teacher = " + englishTeacher + " doesn't have an appropriate role");
        }
    }
}