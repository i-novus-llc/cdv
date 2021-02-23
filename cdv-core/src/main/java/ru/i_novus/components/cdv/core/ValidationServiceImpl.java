package ru.i_novus.components.cdv.core;

import ru.i_novus.components.cdv.core.api.Parser;
import ru.i_novus.components.cdv.core.api.Validation;
import ru.i_novus.components.cdv.core.api.ValidationRepository;
import ru.i_novus.components.cdv.core.api.ValidationService;

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
}
