package ru.i_novus.components.cdv.core.service;

/**
 * Валидация.
 * 
 * @param <V> структура для валидации
 * @param <R> результат валидации
 */
public interface Validation<V, R> {

    R validate(V v);
}
