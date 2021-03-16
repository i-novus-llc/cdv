package ru.i_novus.components.cdv.inmemory.json.impl.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.springframework.util.StringUtils;
import ru.i_novus.components.cdv.core.dao.ValidationDao;
import ru.i_novus.components.cdv.core.dao.ValidationEntity;
import ru.i_novus.components.cdv.core.service.Validation;
import ru.i_novus.components.cdv.core.service.ValidationRepository;
import ru.i_novus.components.cdv.inmemory.json.impl.model.ValidationResult;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Репозиторий валидации с использованием Groovy.
 */
public class GroovyValidationRepository implements ValidationRepository<String, ValidationResult> {

    private static final String ALLOWED_LANGUAGE = "GROOVY";

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

        GroovyShell shell = new GroovyShell();
        Binding context = createEvaluationContext(json);

        return validationDao.findValidationEntityList().stream()
                .filter(this::allowValidation)
                .map(entity -> createValidation(shell, context, entity))
                .collect(Collectors.toList());
    }

    private Binding createEvaluationContext(String json) {

        Binding context = new Binding();
        context.setProperty("data", stringToData(json));

        if (evaluationContextInitializer != null) {
            evaluationContextInitializer.init(context);
        }

        return context;
    }

    private static JsonNode stringToData(String json) {

        if (StringUtils.isEmpty(json))
            return null;

        try {
            return jsonMapper.readTree(json);

        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("Error parsing json:%n%s", json), e);
        }
    }

    private boolean allowValidation(ValidationEntity validationEntity) {

        return ALLOWED_LANGUAGE.equals(validationEntity.getLanguage());
    }

    private GroovyValidation createValidation(GroovyShell shell, Binding context,
                                              ValidationEntity validationEntity) {
        return new GroovyValidation(
                validationEntity.getExpression(),
                validationEntity.getAttribute(),
                validationEntity.getCode(),
                validationEntity.getMessage(),
                shell,
                context);
    }
}
