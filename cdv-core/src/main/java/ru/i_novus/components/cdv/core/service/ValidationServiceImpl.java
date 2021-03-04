package ru.i_novus.components.cdv.core.service;

import ru.i_novus.components.cdv.core.model.ValidationException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationServiceImpl<I, V, R>  implements ValidationService<I, R> {

    private final Parser<I, V> parser;

    private final Collection<ValidationRepository<V, R>> validationRepositories;

    public ValidationServiceImpl(Parser<I, V> parser, ValidationRepository<V, R> validationRepository) {
        this(parser, Collections.singletonList(validationRepository));
    }

    public ValidationServiceImpl(Parser<I, V> parser, Collection<ValidationRepository<V, R>> validationRepositories) {
        this.parser = parser;
        this.validationRepositories = validationRepositories;
    }

    @Override
    public List<R> validate(I input) {

        V parseResult = parser.parse(input);
        List<? extends Validation<V, R>> validations = validationRepositories.stream()
                .flatMap(validation -> validation.getValidations(parseResult).stream())
                .collect(Collectors.toList());
        return validations.stream()
                .map(validation -> {
                    try {
                        return validation.validate(parseResult);
                    } catch (Exception e) {
                        throw new ValidationException(validation, e);
                    }
                })
                .collect(Collectors.toList());
    }
}
