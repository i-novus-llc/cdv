package ru.i_novus.components.cdv.inmemory.json.impl.service;

import groovy.lang.Binding;
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

    private final String language;

    private final String description;

    private final Binding context;

    private final GroovyShell shell;

    public GroovyValidation(String expression, String field, String code, String language, String description,
                            Binding context){
        this.expression = expression;
        this.field = field;
        this.code = code;
        this.language = language;
        this.description = description;
        this.context = context;

        shell = new GroovyShell();
    }

    @Override
    public ValidationResult validate(String json) {

        Script parsed = shell.parse(expression);
        parsed.setBinding(context);
        Object value = parsed.run();
        StatusEnum status = Boolean.TRUE.equals(value) ? StatusEnum.SUCCESS : StatusEnum.ERROR;

        return new ValidationResult(field, code, language, description, status);
    }

    @Override
    public String getCode() {
        return code;
    }
}
