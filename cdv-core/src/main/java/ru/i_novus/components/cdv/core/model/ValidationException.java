package ru.i_novus.components.cdv.core.model;

import ru.i_novus.components.cdv.core.service.Validation;

/**
 * Исключение при валидации.
 */
public class ValidationException extends RuntimeException {

    public ValidationException(Validation validation, Throwable cause) {
        super(String.format("validation: %s failed, details: %s",validation.getCode(), cause.getMessage()));
    }
}
