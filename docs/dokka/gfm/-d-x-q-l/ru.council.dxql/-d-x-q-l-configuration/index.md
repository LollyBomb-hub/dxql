//[DXQL](../../../index.md)/[ru.council.dxql](../index.md)/[DXQLConfiguration](index.md)

# DXQLConfiguration

[jvm]\
open class [DXQLConfiguration](index.md) : [ValidatedModel](../../ru.council.dxql.interfaces/-validated-model/index.md)

## Constructors

| | |
|---|---|
| [DXQLConfiguration](-d-x-q-l-configuration.md) | [jvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [from](from.md) | [jvm]<br>open fun [from](from.md)(@NonNullis: @NonNull[InputStream](https://docs.oracle.com/javase/8/docs/api/java/io/InputStream.html)): [DXQLConfiguration](index.md)<br>open fun [from](from.md)(@NonNullpathToConfig: @NonNull[String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html)): [DXQLConfiguration](index.md)<br>open fun [from](from.md)(@NonNullpath: @NonNull[Path](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html)): [DXQLConfiguration](index.md) |
| [getByIdentifier](get-by-identifier.md) | [jvm]<br>open fun &lt;[T](get-by-identifier.md), [S](get-by-identifier.md)&gt; [getByIdentifier](get-by-identifier.md)(source: [List](https://docs.oracle.com/javase/8/docs/api/java/util/List.html)&lt;[T](get-by-identifier.md)&gt;, identifierChecker: ([T](get-by-identifier.md)) -&gt; [S](get-by-identifier.md), identifier: [S](get-by-identifier.md)): [T](get-by-identifier.md) |
| [getQueryByName](get-query-by-name.md) | [jvm]<br>open fun [getQueryByName](get-query-by-name.md)(name: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html)): [QueryDefinition](../../ru.council.dxql.models/-query-definition/index.md) |
| [getSelectByName](get-select-by-name.md) | [jvm]<br>open fun [getSelectByName](get-select-by-name.md)(name: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html)): [SelectDefinition](../../ru.council.dxql.models/-select-definition/index.md) |
| [getTemplateByName](get-template-by-name.md) | [jvm]<br>open fun [getTemplateByName](get-template-by-name.md)(name: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html)): [TemplateSelectDefinition](../../ru.council.dxql.models/-template-select-definition/index.md) |
| [validate](validate.md) | [jvm]<br>open fun [validate](validate.md)(): [ValidationResult](../../ru.council.dxql.models.validation/-validation-result/index.md) |
