package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.teacher.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.Role;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.teacher.EnglishTeacher;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.user.teacher.EnglishTeacherRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.teacher.EnglishTeacherService;

import java.util.List;

@AllArgsConstructor
@Service
public class EnglishTeacherServiceImpl implements EnglishTeacherService {

    private final EnglishTeacherRepository englishTeacherRepository;
    private final UserService userService;

    @Override
    public List<EnglishTeacher> findAll() {
        return englishTeacherRepository.findAll();
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

        if(englishTeacher.getUser() != null) {
            englishTeacher.setUser(userService.create(englishTeacher.getUser()));
        }

        return englishTeacherRepository.save(englishTeacher);
    }

    @Override
    public EnglishTeacher update(EnglishTeacher englishTeacher) {
        checkEnglishTeacherNull(englishTeacher);
        checkEnglishTeacherIdNull(englishTeacher.getId());
        checkEnglishTeacherHasTeacherRole(englishTeacher);

        EnglishTeacher foundTeacher = findById(englishTeacher.getId());
        if(!foundTeacher.getUser().equals(englishTeacher.getUser())){
            throw new IllegalArgumentException("Program tried to change user in EnglishTeacher class");
        }

        return englishTeacherRepository.save(englishTeacher);
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