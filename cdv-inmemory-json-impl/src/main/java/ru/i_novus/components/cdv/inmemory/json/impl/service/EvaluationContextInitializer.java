package ru.i_novus.components.cdv.inmemory.json.impl.service;

import org.springframework.expression.EvaluationContext;

/**
 * Внешняя инициализация контекста SpEL.
 */
public interface EvaluationContextInitializer {

    void init(EvaluationContext evaluationContext);
}
