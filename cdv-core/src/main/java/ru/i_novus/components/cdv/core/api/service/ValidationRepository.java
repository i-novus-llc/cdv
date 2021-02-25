package ru.i_novus.components.cdv.core.api.service;

import ru.i_novus.components.cdv.core.api.model.Validation;

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
