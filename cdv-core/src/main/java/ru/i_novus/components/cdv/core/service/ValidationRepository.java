package ru.i_novus.components.cdv.core.service;

import java.util.List;

/**
 * Репозиторий валидации.
 *
 * @param <V> валидируемая структура
 * @param <R> результат валидации
 */
public interface ValidationRepository<V, R> {

    List<Validation<V, R>> getValidations(V v);
}
