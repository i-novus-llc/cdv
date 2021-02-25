package ru.i_novus.components.cdv.inmemory.json.impl.service;

import org.springframework.beans.BeanUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import ru.i_novus.components.cdv.core.api.model.Validation;
import ru.i_novus.components.cdv.core.api.service.ValidationRepository;
import ru.i_novus.components.cdv.core.impl.dao.ValidationDao;
import ru.i_novus.components.cdv.core.impl.dao.ValidationEntity;
import ru.i_novus.components.cdv.inmemory.json.impl.util.JsonPathUtils;
import ru.i_novus.components.cdv.inmemory.json.impl.model.SpelValidation;
import ru.i_novus.components.cdv.inmemory.json.impl.model.ValidationResult;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class ValidationRepositoryImpl implements ValidationRepository<String, ValidationResult> {

    private static final String ALLOWED_LANGUAGE = "SPEL";

    private final ValidationDao validationDao;

    private final EvaluationContextInitializer evaluationContextInitializer;

    public ValidationRepositoryImpl(ValidationDao validationDao) {
        this(validationDao, null);
    }

    public ValidationRepositoryImpl(ValidationDao validationDao, EvaluationContextInitializer evaluationContextInitializer) {
        this.validationDao = validationDao;
        this.evaluationContextInitializer = evaluationContextInitializer;
    }

    @Override
    public List<Validation<String, ValidationResult>> getValidations(String json) {

        EvaluationContext context = createEvaluationContext(json);

        return validationDao.findValidationEntityList().stream()
                .filter(this::allowValidation)
                .map(entity -> createValidation(context, entity))
                .collect(Collectors.toList());
    }

    private EvaluationContext createEvaluationContext(String json) {

        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("data", json);
        context.registerFunction("jsonPath",
                requireNonNull(BeanUtils.resolveSignature("evaluate", JsonPathUtils.class))
        );

        if(evaluationContextInitializer != null) {
            evaluationContextInitializer.init(context);
        }

        return context;
    }

    private boolean allowValidation(ValidationEntity validationEntity) {

        return ALLOWED_LANGUAGE.equals(validationEntity.getLanguage());
    }

    private SpelValidation createValidation(EvaluationContext context, ValidationEntity validationEntity) {
        return new SpelValidation(
                validationEntity.getExpression(),
                validationEntity.getAttribute(),
                validationEntity.getCode(),
                validationEntity.getMessage(),
                context);
    }
}
