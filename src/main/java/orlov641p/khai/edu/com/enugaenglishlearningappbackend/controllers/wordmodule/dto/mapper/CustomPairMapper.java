package orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.mapper;

import orlov641p.khai.edu.com.enugaenglishlearningappbackend.controllers.wordmodule.dto.response.CustomPairResponse;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.CustomPair;

import java.util.List;

public class CustomPairMapper {
    public static CustomPairResponse customPairToCustomPairResponse(CustomPair customPair) {
        return new CustomPairResponse(customPair);
    }

    public static List<CustomPairResponse> customPairListToCustomPairResponseList(List<CustomPair> customPairList) {
        return customPairList.stream().map(CustomPairMapper::customPairToCustomPairResponse).toList();
    }
}
