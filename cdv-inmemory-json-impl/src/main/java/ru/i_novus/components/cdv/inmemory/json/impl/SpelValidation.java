package ru.i_novus.components.cdv.inmemory.json.impl;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import ru.i_novus.components.cdv.core.StatusEnum;
import ru.i_novus.components.cdv.core.api.Validation;

/**
 * Валидация с использованием SPEL.
 */
public class SpelValidation implements Validation<String, ValidationResult> {

    private final String expression;

    private final String field;

    private final String code;

    private final String description;

    private final EvaluationContext context;

    private final ExpressionParser parser;

    public SpelValidation(String expression, String field, String code, String description,
                          EvaluationContext context){
        this.expression = expression;
        this.field = field;
        this.code = code;
        this.description = description;
        this.context = context;

        parser = new SpelExpressionParser();
    }

    @Override
    public ValidationResult validate(String json) {

        Boolean value = parser.parseExpression(expression).getValue(context, Boolean.class);
        StatusEnum status = Boolean.TRUE.equals(value) ? StatusEnum.SUCCESS : StatusEnum.ERROR;

        return new ValidationResult(field, code, description, status);
    }
}
