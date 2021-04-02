package ru.i_novus.components.cdv.inmemory.json.impl.service;

import groovy.lang.Binding;
import org.codehaus.groovy.control.CompilerConfiguration;

/**
 * Внешняя инициализация контекста Groovy.
 */
public interface GroovyContextInitializer {

    void init(Binding binding, CompilerConfiguration configuration);
}
