package ru.i_novus.components.cdv.core.api;

import java.util.List;

/**
 *
 * @param <V> - валидируемая структура
 * @param <R> - результат валидации
 */
public interface ValidationRepository<V, R> {

    List<Validation<V, R>> getValidations(V v);
}
