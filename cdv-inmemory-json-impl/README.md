## Реализация для валидации произвольного json'a, где проверки описываются с помощью spel-выражений.

## Как описывать проверки
  Проверки описываются в таблице cdv.validation (см. комментарии к таблице).
  lang может быть `SPEL` или `GROOVY`.
   
## Проверки SpEL
1. В качестве lang указать `SPEL`.
2. Проверяемый json в виде строки доступен как `#data`.   
2. В проверках можно использовать функцию `#jsonPath` (см. ниже).
3. Для кастомной инициализации контекста SpEL надо реализовать EvaluationContextInitializer и передать его в конструкторе SpelValidationRepository.
   
### Функция #jsonPath
Функция является обёрткой Jayway JsonPath (см. [тут](https://github.com/json-path/JsonPath)).
Синтаксис:
`#jsonPath(#data, '<json path>')`,
где `jsonPath` - функция `ru.i_novus.components.cdv.inmemory.json.impl.util.JsonPathUtils.evaluate`,
`data` - проверяемый json.

## Проверки Groovy
1. В качестве lang указать `GROOVY`.
2. Проверяемый json в виде строки доступен как `"$data"`.   
3. Для разбора строки в json можно использовать JsonSlurper.
4. Для кастомной инициализации контекста Groovy надо реализовать GroovyContextInitializer и передать его в конструкторе GroovyValidationRepository.

### Разбор строки в json
Пример:
```groovy
import groovy.json.JsonSlurper
def jsonSlurper = new JsonSlurper()
def data = jsonSlurper.parseText("$data")
return (data?.store?.id != null)
```
