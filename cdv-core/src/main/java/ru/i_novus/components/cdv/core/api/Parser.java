package ru.i_novus.components.cdv.core.api;

/**
 *
 * @param <I> входная структура для парсинга
 * @param <V> выходная структура, над которой будут выполняться валидации
 */
public interface Parser<I, V> {

    V parse(I input);
}
