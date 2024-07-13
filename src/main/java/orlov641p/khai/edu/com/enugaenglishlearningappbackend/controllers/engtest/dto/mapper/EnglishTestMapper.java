package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.engtest.dto.mapper;

import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.engtest.dto.response.EnglishTestResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.engtest.EnglishTest;

import java.util.ArrayList;
import java.util.List;

public class EnglishTestMapper {

    public static EnglishTestResponse englishTestToResponse(EnglishTest engTest) {
        return new EnglishTestResponse(engTest);
    }

    public static List<EnglishTestResponse> englishTestListToResponseList(List<EnglishTest> englishTests){
        List<EnglishTestResponse> englishTestResponseList = new ArrayList<>();

        for(EnglishTest englishTest : englishTests){
            englishTestResponseList.add(englishTestToResponse(englishTest));
        }

        return englishTestResponseList;
    }
}
