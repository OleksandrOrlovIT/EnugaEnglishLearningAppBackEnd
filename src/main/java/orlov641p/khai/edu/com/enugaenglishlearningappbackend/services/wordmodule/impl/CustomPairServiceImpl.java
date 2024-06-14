package orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.CustomPair;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.models.wordmodule.WordModule;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.wordmodule.CustomPairRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.repositories.wordmodule.WordModuleRepository;
import orlov641p.khai.edu.com.enugaenglishlearningappbackend.services.wordmodule.CustomPairService;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
@Transactional
public class CustomPairServiceImpl implements CustomPairService {

    private final CustomPairRepository customPairRepository;
    private final WordModuleRepository wordModuleRepository;

    @Override
    public List<CustomPair> findAll() {
        return customPairRepository.findAll();
    }

    @Override
    public CustomPair findById(Long id) {
        Objects.requireNonNull(id, "Custom Pair id cannot be null");
        return customPairRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Custom Pair with id = " + id + " doesn't exist"));
    }

    @Override
    public CustomPair create(CustomPair customPair) {
        validateCustomPairForCreation(customPair);
        validateWordModuleExistence(customPair.getWordModule());
        return customPairRepository.save(customPair);
    }

    @Override
    public CustomPair update(CustomPair customPair) {
        validateCustomPairForUpdate(customPair);
        validateWordModuleExistence(customPair.getWordModule());
        return customPairRepository.save(customPair);
    }

    @Override
    public void delete(CustomPair customPair) {
        Objects.requireNonNull(customPair, "Custom pair cannot be null");
        customPairRepository.delete(customPair);
    }

    @Override
    public void deleteById(Long id) {
        Objects.requireNonNull(id, "Custom pair id cannot be null");
        customPairRepository.deleteById(id);
    }

    @Override
    public CustomPair getFirst() {
        Pageable pageable = Pageable
                .ofSize(1)
                .first();

        Page<CustomPair> customPairs = customPairRepository.findAll(pageable);

        return customPairs.hasContent() ? customPairs.getContent().get(0) : null;
    }

    @Override
    public List<CustomPair> getCustomPairsByWordModuleId(Long id) {
        Objects.requireNonNull(id, "Word Module id cannot be null");
        return customPairRepository.findByWordModuleId(id);
    }

    @Override
    public List<CustomPair> createAll(List<CustomPair> customPairs) {
        return customPairRepository.saveAll(customPairs);
    }

    private void validateCustomPairForCreation(CustomPair customPair) {
        Objects.requireNonNull(customPair, "CustomPair cannot be null");
        if (customPair.getId() != null) {
            throw new IllegalArgumentException("CustomPair ID should not be provided for creation");
        }
        validateWordModuleAssociation(customPair.getWordModule());
    }

    private void validateCustomPairForUpdate(CustomPair customPair) {
        Objects.requireNonNull(customPair, "CustomPair cannot be null");
        Objects.requireNonNull(customPair.getId(), "CustomPair ID must be provided for update");
        if (!customPairRepository.existsById(customPair.getId())) {
            throw new IllegalArgumentException("CustomPair with id = " + customPair.getId() + " does not exist");
        }
        validateWordModuleAssociation(customPair.getWordModule());
        validateWordModuleConsistency(customPair);
    }

    private void validateWordModuleAssociation(WordModule wordModule) {
        if (wordModule == null) {
            throw new IllegalArgumentException("CustomPair must have an associated Word Module");
        }
    }

    private void validateWordModuleExistence(WordModule wordModule) {
        Objects.requireNonNull(wordModule, "Word Module cannot be null");
        wordModuleRepository.findById(wordModule.getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Word Module with id = " + wordModule.getId() + " doesn't exist"));
    }

    private void validateWordModuleConsistency(CustomPair customPair) {
        CustomPair foundCustomPair = findById(customPair.getId());
        if (!foundCustomPair.getWordModule().equals(customPair.getWordModule())) {
            throw new IllegalArgumentException("Custom Pair found by id = " + foundCustomPair +
                    " have different Word Module = " + foundCustomPair.getWordModule() +
                    "\nWith passed custom pair = " + customPair + " and its word module = " + customPair.getWordModule());
        }
    }
}