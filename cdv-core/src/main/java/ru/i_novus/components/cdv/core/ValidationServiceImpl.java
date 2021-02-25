package ru.i_novus.components.cdv.core;

import ru.i_novus.components.cdv.core.api.*;
import ru.i_novus.components.cdv.core.dao.ValidationEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ValidationServiceImpl<I, V, R>  implements ValidationService<I, R> {

    private final Parser<I, V> parser;

    private final ValidationRepository<V, R> validationRepository;

    public ValidationServiceImpl(Parser<I, V> parser, ValidationRepository<V, R> validationRepository) {
        this.parser = parser;
        this.validationRepository = validationRepository;
    }

    @Override
    public List<R> validate(I input) {

        V parseResult = parser.parse(input);
        List<? extends Validation<V, R>> validations = validationRepository.getValidations(parseResult);
        return validations.stream()
                .map(validation -> validation.validate(parseResult))
                .collect(Collectors.toList());
    }

    @Override
    public R validate(I input, String language, String expression) {

        V parseResult = parser.parse(input);
        ValidationEntity entity = toValidationEntity(language, expression);

        Validation<V, R> validation = validationRepository.getValidation(parseResult, entity);
        if (validation == null)
            throw new ValidationException("Validation not allowed");

        return validation.validate(parseResult);
    }

    private ValidationEntity toValidationEntity(String language, String expression) {

        ValidationEntity entity = new ValidationEntity();
        entity.setLanguage(language);
        entity.setExpression(expression);

        return entity;
    }
}
