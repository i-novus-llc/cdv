package ru.i_novus.components.cdv.inmemory.json.impl;

import org.springframework.expression.EvaluationContext;

/**
 * Внешняя инициализация контекста SPEL.
 */
public interface EvaluationContextInitializer {

    void init(EvaluationContext evaluationContext);

}
