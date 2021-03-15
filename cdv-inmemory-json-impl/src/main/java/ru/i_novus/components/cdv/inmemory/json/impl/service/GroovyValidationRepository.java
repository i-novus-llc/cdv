package ru.i_novus.components.cdv.inmemory.json.impl.service;

import groovy.lang.Binding;
import org.springframework.beans.BeanUtils;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import ru.i_novus.components.cdv.core.dao.ValidationDao;
import ru.i_novus.components.cdv.core.dao.ValidationEntity;
import ru.i_novus.components.cdv.core.service.Validation;
import ru.i_novus.components.cdv.core.service.ValidationRepository;
import ru.i_novus.components.cdv.inmemory.json.impl.model.ValidationResult;
import ru.i_novus.components.cdv.inmemory.json.impl.util.JsonPathUtils;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class GroovyValidationRepository implements ValidationRepository<String, ValidationResult> {

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
    public String getAllowedLanguage() {
        return "GROOVY";
    }

    @Override
    public List<Validation<String, ValidationResult>> getValidations(String json) {

        Binding context = createEvaluationContext(json);

        return validationDao.findValidationEntityList().stream()
                .filter(this::allowValidation)
                .map(entity -> createValidation(context, entity))
                .collect(Collectors.toList());
    }

    private Binding createEvaluationContext(String json) {

        Binding context = new Binding();
        context.setProperty("data", json);

        if (evaluationContextInitializer != null) {
            evaluationContextInitializer.init(context);
        }

        return context;
    }

    private boolean allowValidation(ValidationEntity validationEntity) {

        return getAllowedLanguage().equals(validationEntity.getLanguage());
    }

    private GroovyValidation createValidation(Binding context, ValidationEntity validationEntity) {
        return new GroovyValidation(
                validationEntity.getExpression(),
                validationEntity.getAttribute(),
                validationEntity.getCode(),
                validationEntity.getMessage(),
                context);
    }
}
