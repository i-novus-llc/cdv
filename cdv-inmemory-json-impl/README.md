## Реализация для валидации произвольного json'a, где проверки описываются с помощью spel-выражений.

## Как описывать проверки
1. Проверки описываются в таблице cdv.validation(см. комментарии к таблице). В качестве lang указать `SPEL`
2. Для проверок используется Jayway JsonPath(см. [тут](https://github.com/json-path/JsonPath)) и зарегистрирована в spel функция
   ru.i_novus.components.cdv.inmemory.json.impl.JsonPathUtils.evaluate, поэтому используйте следующий
   синтаксис `#jsonPath(#data, '<json path>')`, где `jsonPath` - функция
   `ru.i_novus.components.cdv.inmemory.json.impl.JsonPathUtils.evaluate`, `data` - проверяемый json
3. Для кастомной инициализации контекста spel надо реализовать EvaluationContextInitializer и передать его в конструкторе ValidationRepositoryImpl    