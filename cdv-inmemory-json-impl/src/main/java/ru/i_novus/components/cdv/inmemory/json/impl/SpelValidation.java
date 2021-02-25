package ru.i_novus.components.cdv.inmemory.json.impl;

import org.springframework.expression.*;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import ru.i_novus.components.cdv.core.StatusEnum;
import ru.i_novus.components.cdv.core.api.Validation;
import ru.i_novus.components.cdv.core.api.ValidationException;

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

        Expression parsed = parse();
        Boolean value = getValue(parsed);
        StatusEnum status = Boolean.TRUE.equals(value) ? StatusEnum.SUCCESS : StatusEnum.ERROR;

        return new ValidationResult(field, code, description, status);
    }

    private Expression parse() {
        try {
            return parser.parseExpression(expression);

        } catch (ParseException e) {
            throw new ValidationException(e);
        }
    }

    private Boolean getValue(Expression parsed) {
        try {
            return parsed.getValue(context, Boolean.class);

        } catch (SpelEvaluationException e) {
            throw new ValidationException(e);
        }
    }
}
