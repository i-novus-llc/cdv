# cdv

Configurable data validation library

## Предназначение

Библиотека предназначена для настраиваемых валидаций произвольной структуры данных. Проверки описываются в таблице
cdv.validation На данный момент существуют реализации:

1. Валидация произвольного json'a, где проверки описываются с помощью spel(Spring Expression Language)-выражений.
   Подробнее [тут](../cdv/cdv-inmemory-json-impl/README.md)

## Описание модулей

1. cdv-core - движок валидаций и базовые интерфейсы модуля
2. cdv-inmemory-json-impl - реализация для произвольного json'a, где проверки описываются с помощью spel-выражений

## Как использовать

1. Добавить в pom.xml maven-зависимость одной из реализаций модуля
2. Подключить к liquibase скрипт 001-init.xml, если не используется liquibase, то выполнить скрипт иным способом
   скопировав схему из 001-init.xml
3. Создать spring-bean ValidationServiceImpl в зависимости от выбранной реализации. Подробнее в конкретных реализациях   