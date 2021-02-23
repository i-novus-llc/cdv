package ru.i_novus.components.cdv.inmemory.json.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import ru.i_novus.components.cdv.core.StatusEnum;
import ru.i_novus.components.cdv.core.api.Validation;


public class SpelValidation implements Validation<String, ValidationResult> {

    private String expression;

    private String field;

    private ExpressionParser parser;

    private String code;

    private String description;

    private StandardEvaluationContext context;

    public SpelValidation(String expression, String field, String code, String description, StandardEvaluationContext context){
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
        return new ValidationResult(field, code, description, value ? StatusEnum.SUCCESS : StatusEnum.ERROR);
    }
}
