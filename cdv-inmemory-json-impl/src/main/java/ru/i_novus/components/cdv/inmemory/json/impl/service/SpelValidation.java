package ru.i_novus.components.cdv.inmemory.json.impl.service;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import ru.i_novus.components.cdv.core.model.StatusEnum;
import ru.i_novus.components.cdv.core.service.Validation;
import ru.i_novus.components.cdv.inmemory.json.impl.model.ValidationResult;

/**
 * Валидация с использованием SpEL.
 */
public class SpelValidation implements Validation<String, ValidationResult> {

    private final String expression;

    private final String field;

    private final String code;

    private final String description;

    private final ExpressionParser parser;

    private final StandardEvaluationContext context;

    public SpelValidation(String expression, String field, String code, String description,
                          ExpressionParser parser, StandardEvaluationContext context){
        this.expression = expression;
        this.field = field;
        this.code = code;
        this.description = description;

        this.parser = parser;
        this.context = context;
    }

    @Override
    public ValidationResult validate(String json) {

        Expression parsed = parser.parseExpression(expression);
        Boolean value = parsed.getValue(context, Boolean.class);
        StatusEnum status = Boolean.TRUE.equals(value) ? StatusEnum.SUCCESS : StatusEnum.ERROR;

        return new ValidationResult(field, code, description, status);
    }

    @Override
    public String getCode() {
        return code;
    }
}
