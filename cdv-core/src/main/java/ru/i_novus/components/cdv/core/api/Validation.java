package ru.i_novus.components.cdv.core.api;

/**
 *
 * @param <V> структура для валидации
 * @param <R> Результат валидации
 */
public interface Validation<V, R> {

    R validate(V v);
}
