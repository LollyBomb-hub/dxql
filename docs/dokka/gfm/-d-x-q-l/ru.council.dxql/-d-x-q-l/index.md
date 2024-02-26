//[DXQL](../../../index.md)/[ru.council.dxql](../index.md)/[DXQL](index.md)

# DXQL

[jvm]\
open class [DXQL](index.md) : [QueryExecutor](../../ru.council.dxql.executors/-query-executor/index.md)

## Constructors

| | |
|---|---|
| [DXQL](-d-x-q-l.md) | [jvm]<br>constructor(@NonNulltemplate: @NonNullJdbcTemplate, @NonNullconfiguration: @NonNull[DXQLConfiguration](../-d-x-q-l-configuration/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [checkAndFillInput](check-and-fill-input.md) | [jvm]<br>open fun [checkAndFillInput](check-and-fill-input.md)(inputParameters: [Map](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html)&lt;[String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;, target: [Map](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html)&lt;[String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;, variableParameters: [List](https://docs.oracle.com/javase/8/docs/api/java/util/List.html)&lt;[VariableParameter](../../ru.council.dxql.models/-variable-parameter/index.md)&gt;, objectMapper: ObjectMapper) |
| [executeWholeDocument](execute-whole-document.md) | [jvm]<br>@Transactional<br>open fun [executeWholeDocument](execute-whole-document.md)(format: [TargetFormat](../../ru.council.dxql.enums/-target-format/index.md)): MetaJson<br>@Transactional<br>open fun [executeWholeDocument](execute-whole-document.md)(givenParameters: [Map](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html)&lt;[String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;, format: [TargetFormat](../../ru.council.dxql.enums/-target-format/index.md), overrideMessage: [Map](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html)&lt;[String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html)&gt;): MetaJson |
| [executeWholeDocumentForQuery](execute-whole-document-for-query.md) | [jvm]<br>@Transactional<br>open fun [executeWholeDocumentForQuery](execute-whole-document-for-query.md)(queryName: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), format: [TargetFormat](../../ru.council.dxql.enums/-target-format/index.md)): MetaJson<br>@Transactional<br>open fun [executeWholeDocumentForQuery](execute-whole-document-for-query.md)(queryName: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), givenParameters: [Map](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html)&lt;[String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;, format: [TargetFormat](../../ru.council.dxql.enums/-target-format/index.md), overrideMessage: [Map](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html)&lt;[String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html)&gt;): MetaJson |
| [processVariableParameters](process-variable-parameters.md) | [jvm]<br>open fun [processVariableParameters](process-variable-parameters.md)(inputParameters: [Map](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html)&lt;[String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;, target: [Map](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html)&lt;[String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;, variableParameters: [List](https://docs.oracle.com/javase/8/docs/api/java/util/List.html)&lt;[VariableParameter](../../ru.council.dxql.models/-variable-parameter/index.md)&gt;, overrideMessage: [Map](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html)&lt;[String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html)&gt;) |