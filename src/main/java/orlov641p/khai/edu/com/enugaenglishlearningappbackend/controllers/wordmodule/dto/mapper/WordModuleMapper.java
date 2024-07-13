package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.request.WordModuleRequest;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.response.WordModuleResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.user.User;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;

import java.util.List;
import java.util.stream.Collectors;

public class WordModuleMapper {
    public static Page<WordModuleResponse> convertPageTestAttemptToResponse(Page<WordModule> wordModules) {
        List<WordModuleResponse> wordModuleResponses = wordModules.getContent().stream()
                .map(WordModuleMapper::convertWordModuleToResponse)
                .collect(Collectors.toList());

        Pageable pageable = wordModules.getPageable();

        return new PageImpl<>(wordModuleResponses, pageable, wordModules.getTotalElements());
    }

    public static WordModuleResponse convertWordModuleToResponse(WordModule wordModule){
        return new WordModuleResponse(wordModule);
    }

    public static WordModule convertWordModuleRequestToWordModule(WordModuleRequest wordModuleRequest, User user){
        return WordModule.builder()
                .moduleName(wordModuleRequest.getModuleName())
                .visibility(wordModuleRequest.getVisibility())
                .user(user)
                .customPairs(wordModuleRequest.getCustomPairs())
                .build();
    }

    public static List<WordModuleResponse> convertWordModuleListToResponseList(List<WordModule> wordModules){
        return wordModules.stream().map(WordModuleMapper::convertWordModuleToResponse).toList();
    }
}
