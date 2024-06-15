package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.student.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.Role;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.student.EnglishStudent;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.teacher.EnglishTeacher;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.user.student.EnglishStudentRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.user.teacher.EnglishTeacherRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.UserService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.student.EnglishStudentService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.teacher.EnglishTeacherService;

import java.util.List;

@AllArgsConstructor
@Service
public class EnglishStudentServiceImpl implements EnglishStudentService {

    private final EnglishStudentRepository englishStudentRepository;
    private final EnglishTeacherService englishTeacherService;
    private final UserService userService;

    @Override
    public List<EnglishStudent> findAll() {
        return englishStudentRepository.findAll();
    }

    @Override
    public EnglishStudent findById(Long id) {
        checkEnglishStudentIdNull(id);

        return englishStudentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find english student with id = " + id));
    }

    @Override
    public EnglishStudent create(EnglishStudent englishStudent) {
        checkEnglishStudentNull(englishStudent);
        checkEnglishTeacherExists(englishStudent.getTeacher());
        checkEnglishStudentHasStudentRole(englishStudent);

        if(englishStudent.getUser() != null) {
            englishStudent.setUser(userService.create(englishStudent.getUser()));
        }

        return englishStudentRepository.save(englishStudent);
    }

    @Override
    public EnglishStudent update(EnglishStudent englishStudent) {
        checkEnglishStudentNull(englishStudent);
        checkEnglishStudentIdNull(englishStudent.getId());
        checkEnglishTeacherExists(englishStudent.getTeacher());
        checkEnglishStudentHasStudentRole(englishStudent);

        EnglishStudent foundEnglishStudent = findById(englishStudent.getId());

        if(!foundEnglishStudent.getUser().equals(englishStudent.getUser())){
            throw new IllegalArgumentException("Program tried to change user in EnglishStudent class");
        }

        return englishStudentRepository.save(englishStudent);
    }

    @Override
    public void delete(EnglishStudent englishStudent) {
        checkEnglishStudentNull(englishStudent);

        englishStudentRepository.delete(englishStudent);
    }

    @Override
    public void deleteById(Long id) {
        checkEnglishStudentIdNull(id);

        englishStudentRepository.deleteById(id);
    }

    @Override
    public EnglishStudent getFirst() {
        Pageable pageable = Pageable
                .ofSize(1)
                .first();

        Page<EnglishStudent> englishStudents = englishStudentRepository.findAll(pageable);

        return englishStudents.hasContent() ? englishStudents.getContent().get(0) : null;
    }

    @Override
    public Page<EnglishStudent> findEnglishStudentsByEnglishTeacher(Long englishTeacherId, Pageable pageable) {
        EnglishTeacher englishTeacher = englishTeacherService.findById(englishTeacherId);

        return englishStudentRepository.findByTeacher(englishTeacher, pageable);
    }

    private void checkEnglishStudentNull(EnglishStudent englishStudent){
        if(englishStudent == null){
            throw new IllegalArgumentException("EnglishStudent can't be null");
        }
    }

    private void checkEnglishStudentIdNull(Long id){
        if(id == null){
            throw new IllegalArgumentException("EnglishStudent id can`t be null");
        }
    }

    private void checkEnglishTeacherExists(EnglishTeacher englishTeacher){
        if(englishTeacherService.findById(englishTeacher.getId()) == null){
            throw new IllegalArgumentException("English teacher = " + englishTeacher + " doesn't exist");
        }
    }

    private void checkEnglishStudentHasStudentRole(EnglishStudent englishStudent){
        if(!englishStudent.getUser().getRoles().contains(Role.ROLE_ENGLISH_STUDENT_USER)){
            throw new IllegalArgumentException("English student = " + englishStudent + " doesn't have an appropriate role");
        }
    }
}