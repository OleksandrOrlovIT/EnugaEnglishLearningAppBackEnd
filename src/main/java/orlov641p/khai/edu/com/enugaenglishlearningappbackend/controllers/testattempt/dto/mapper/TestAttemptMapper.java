package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.dto.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.dto.response.TestAttemptResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.TestAttempt;

import java.util.List;
import java.util.stream.Collectors;

public class TestAttemptMapper {
    public static Page<TestAttemptResponse> convertPageTestAttemptToResponse(Page<TestAttempt> testAttempts) {
        List<TestAttemptResponse> testAttemptResponses = testAttempts.getContent().stream()
                .map(TestAttemptMapper::convertTestAttemptToResponse)
                .collect(Collectors.toList());

        Pageable pageable = testAttempts.getPageable();

        return new PageImpl<>(testAttemptResponses, pageable, testAttempts.getTotalElements());
    }

    public static TestAttemptResponse convertTestAttemptToResponse(TestAttempt testAttempt){
        return new TestAttemptResponse(testAttempt);
    }
}
