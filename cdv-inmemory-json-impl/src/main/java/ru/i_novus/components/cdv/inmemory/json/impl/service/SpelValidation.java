package ru.i_novus.components.cdv.inmemory.json.impl.service;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import ru.i_novus.components.cdv.core.model.StatusEnum;
import ru.i_novus.components.cdv.core.model.ValidationException;
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

    private final StandardEvaluationContext context;

    private final ExpressionParser parser;

    public SpelValidation(String expression, String field, String code, String description,
                          StandardEvaluationContext context){
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
