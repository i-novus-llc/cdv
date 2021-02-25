package ru.i_novus.components.cdv.inmemory.json.impl.service;

import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Внешняя инициализация контекста SpEL.
 */
public interface EvaluationContextInitializer {

    void init(StandardEvaluationContext evaluationContext);
}
