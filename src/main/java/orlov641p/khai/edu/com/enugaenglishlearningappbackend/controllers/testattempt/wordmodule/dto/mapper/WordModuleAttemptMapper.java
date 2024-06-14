package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.testattempt.wordmodule.dto.response.WordModuleAttemptResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.testattempt.wordmodule.WordModuleAttempt;

import java.util.List;
import java.util.stream.Collectors;

public class WordModuleAttemptMapper {
    public static Page<WordModuleAttemptResponse> convertPageWordModuleAttemptToResponse(Page<WordModuleAttempt> wordModuleAttempts) {
        List<WordModuleAttemptResponse> testAttemptResponses = wordModuleAttempts.getContent().stream()
                .map(WordModuleAttemptMapper::convertWordModuleAttemptToResponse)
                .collect(Collectors.toList());

        Pageable pageable = wordModuleAttempts.getPageable();

        return new PageImpl<>(testAttemptResponses, pageable, wordModuleAttempts.getTotalElements());
    }

    public static WordModuleAttemptResponse convertWordModuleAttemptToResponse(WordModuleAttempt wordModuleAttempt){
        return new WordModuleAttemptResponse(wordModuleAttempt);
    }
}