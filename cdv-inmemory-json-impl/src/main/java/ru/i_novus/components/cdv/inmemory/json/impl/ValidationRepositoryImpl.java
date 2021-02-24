package ru.i_novus.components.cdv.inmemory.json.impl;


import org.springframework.beans.BeanUtils;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import ru.i_novus.components.cdv.core.api.Validation;
import ru.i_novus.components.cdv.core.api.ValidationRepository;
import ru.i_novus.components.cdv.core.dao.ValidationDao;
import ru.i_novus.components.cdv.core.dao.ValidationEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ValidationRepositoryImpl implements ValidationRepository<String, ValidationResult> {

    private ValidationDao validationDao;

    private EvaluationContextInitializer evaluationContextInitializer;

    public ValidationRepositoryImpl(ValidationDao validationDao) {
        this(validationDao, null);
    }

    public ValidationRepositoryImpl(ValidationDao validationDao, EvaluationContextInitializer evaluationContextInitializer) {
        this.validationDao = validationDao;
        this.evaluationContextInitializer = evaluationContextInitializer;
    }

    @Override
    public List<Validation<String, ValidationResult>> getValidations(String json) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("data", json);
        context.registerFunction("jsonPath", BeanUtils.resolveSignature("evaluate", JsonPathUtils.class));
        if(evaluationContextInitializer != null) {
            evaluationContextInitializer.init(context);
        }
        return validationDao.findValidationEntityList().stream()
                .filter(validationEntity -> "SPEL".equals(validationEntity.getLanguage()))
                .map(entity -> createValidation(context, entity))
                .collect(Collectors.toList());
    }

    private SpelValidation createValidation(StandardEvaluationContext context, ValidationEntity validationEntity) {
        return new SpelValidation(
                validationEntity.getExpression(),
                validationEntity.getAttribute(),
                validationEntity.getCode(),
                validationEntity.getMessage(),
                context);
    }
}
