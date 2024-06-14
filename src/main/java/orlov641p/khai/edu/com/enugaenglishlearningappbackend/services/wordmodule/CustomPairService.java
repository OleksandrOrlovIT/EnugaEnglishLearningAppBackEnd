package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule;

import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.CustomPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.CrudService;

import java.util.List;

public interface CustomPairService extends CrudService<CustomPair, Long> {
    List<CustomPair> getCustomPairsByWordModuleId(Long id);

    List<CustomPair> createAll(List<CustomPair> customPairs);
}
