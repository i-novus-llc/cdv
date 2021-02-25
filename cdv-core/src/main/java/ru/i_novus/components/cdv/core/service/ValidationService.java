package ru.i_novus.components.cdv.core.service;

import java.util.List;

/**
 * Основной сервис, с помощью которого запускается
 * проверка над структурой I и результатом проверки будет структура R.
 * <p/>
 * @param <I> входная структура данных для проверки
 * @param <R> выходная структура данных для проверки
 */
public interface ValidationService<I, R> {

    List<R> validate(I input);
}
