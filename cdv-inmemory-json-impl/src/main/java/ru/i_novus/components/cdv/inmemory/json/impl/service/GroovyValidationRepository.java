package ru.i_novus.components.cdv.inmemory.json.impl.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.util.StringUtils;
import ru.i_novus.components.cdv.core.dao.ValidationDao;
import ru.i_novus.components.cdv.core.dao.ValidationEntity;
import ru.i_novus.components.cdv.core.service.Validation;
import ru.i_novus.components.cdv.core.service.ValidationRepository;
import ru.i_novus.components.cdv.inmemory.json.impl.model.ValidationResult;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Репозиторий валидации с использованием Groovy.
 */
public class GroovyValidationRepository implements ValidationRepository<String, ValidationResult> {

    private static final String ALLOWED_LANGUAGE = "GROOVY";
    private static final String JSON_DATA_PROP_NAME = "data";

    private static final ObjectMapper jsonMapper = new ObjectMapper();

    private final ValidationDao validationDao;

    private final GroovyContextInitializer evaluationContextInitializer;

    public GroovyValidationRepository(ValidationDao validationDao) {
        this(validationDao, null);
    }

    public GroovyValidationRepository(ValidationDao validationDao,
                                      GroovyContextInitializer evaluationContextInitializer) {
        this.validationDao = validationDao;
        this.evaluationContextInitializer = evaluationContextInitializer;
    }

    @Override
    public List<Validation<String, ValidationResult>> getValidations(String json) {

        GroovyShell shell = createGroovyShell(json);

        return validationDao.findValidationEntityList().stream()
                .filter(this::allowValidation)
                .map(entity -> createValidation(shell, entity))
                .collect(Collectors.toList());
    }

    private GroovyShell createGroovyShell(String json) {

        Binding binding = new Binding();
        binding.setProperty(JSON_DATA_PROP_NAME, stringToData(json));

        CompilerConfiguration configuration = new CompilerConfiguration();

        if (evaluationContextInitializer != null) {
            evaluationContextInitializer.init(binding, configuration);
        }

        return new GroovyShell(binding, configuration);
    }

    @SuppressWarnings("unchecked")
    private static Map<String, ?> stringToData(String json) {

        if (StringUtils.isEmpty(json))
            return null;

        try {
            jsonMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
            return jsonMapper.readValue(json, Map.class);

        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("Error parsing json:%n%s", json), e);
        }
    }

    private boolean allowValidation(ValidationEntity validationEntity) {

        return ALLOWED_LANGUAGE.equals(validationEntity.getLanguage());
    }

    private GroovyValidation createValidation(GroovyShell shell,
                                              ValidationEntity validationEntity) {
        return new GroovyValidation(
                validationEntity.getExpression(),
                validationEntity.getAttribute(),
                validationEntity.getCode(),
                validationEntity.getMessage(),
                shell);
    }
}
