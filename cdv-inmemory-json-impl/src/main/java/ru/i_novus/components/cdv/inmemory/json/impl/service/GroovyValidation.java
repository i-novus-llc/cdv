package ru.i_novus.components.cdv.inmemory.json.impl.service;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import ru.i_novus.components.cdv.core.model.StatusEnum;
import ru.i_novus.components.cdv.core.service.Validation;
import ru.i_novus.components.cdv.inmemory.json.impl.model.ValidationResult;

/**
 * Валидация с использованием Groovy DSL.
 */
public class GroovyValidation implements Validation<String, ValidationResult> {

    private final String expression;

    private final String field;

    private final String code;

    private final String description;

    private final GroovyShell shell;

    public GroovyValidation(String expression, String field, String code, String description, GroovyShell shell) {
        this.expression = expression;
        this.field = field;
        this.code = code;
        this.description = description;
        this.shell = shell;
    }

    @Override
    public ValidationResult validate(String json) {

        Script parsed = shell.parse(expression);
        Object value = parsed.run();
        StatusEnum status = Boolean.TRUE.equals(value) ? StatusEnum.SUCCESS : StatusEnum.ERROR;

        return new ValidationResult(field, code, description, status);
    }

    @Override
    public String getCode() {
        return code;
    }
}
