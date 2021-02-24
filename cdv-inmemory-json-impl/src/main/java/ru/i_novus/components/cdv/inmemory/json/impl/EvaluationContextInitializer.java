package ru.i_novus.components.cdv.inmemory.json.impl;

import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Внешняя инициализация контекста SPEL
 */
public interface EvaluationContextInitializer {

    void init(StandardEvaluationContext evaluationContext);

}
