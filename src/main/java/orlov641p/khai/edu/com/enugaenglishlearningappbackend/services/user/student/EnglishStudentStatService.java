package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.user.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.engtest.TestAttempt;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;

public interface EnglishStudentStatService {
    Page<TestAttempt> getEnglishTestAttempts(Long englishStudentId, Pageable pageable);

    Page<WordModule> getWordModules(Long englishStudentId, Pageable pageable);
}
