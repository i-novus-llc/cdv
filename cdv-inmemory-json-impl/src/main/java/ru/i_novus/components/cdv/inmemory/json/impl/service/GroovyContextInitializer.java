package ru.i_novus.components.cdv.inmemory.json.impl.service;

import groovy.lang.Binding;

/**
 * Внешняя инициализация контекста Groovy.
 */
public interface GroovyContextInitializer {

    void init(Binding evaluationContext);
}
