package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.student.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.engtest.dto.request.TestAttemptPage;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.request.WordModuleAttemptPage;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.engtest.TestAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.wordmodule.WordModuleAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.student.EnglishStudent;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.testattempt.engtest.TestAttemptService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.testattempt.wordmodule.WordModuleAttemptService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.student.EnglishStudentService;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.student.EnglishStudentStatService;

@AllArgsConstructor
@Service
public class EnglishStudentStatServiceImpl implements EnglishStudentStatService {

    private final EnglishStudentService englishStudentService;
    private final TestAttemptService testAttemptService;
    private final WordModuleAttemptService wordModuleAttemptService;

    @Override
    public Page<TestAttempt> getEnglishTestAttempts(Long englishStudentId, Pageable pageable) {
        EnglishStudent englishStudent = englishStudentService.findById(englishStudentId);

        TestAttemptPage testAttemptPage =
                new TestAttemptPage(englishStudent.getUser().getId(), pageable.getPageSize(), pageable.getPageNumber());

        return testAttemptService.findLastTestAttemptsPageByUserSortedByDate(testAttemptPage);
    }

    @Override
    public Page<WordModuleAttempt> getPublicWordModulesAttempts(Long englishStudentId, Pageable pageable) {
        EnglishStudent englishStudent = englishStudentService.findById(englishStudentId);

        WordModuleAttemptPage wordModuleAttemptPage =
                new WordModuleAttemptPage(englishStudent.getUser().getId(),
                        pageable.getPageSize(), pageable.getPageNumber());

        return wordModuleAttemptService.findLastPublicWordModuleAttemptsPageByUserSortedByDate(wordModuleAttemptPage);
    }
}
