//[DXQL](../../../index.md)/[ru.council.dxql](../index.md)/[ValidationUtils](index.md)

# ValidationUtils

[jvm]\
open class [ValidationUtils](index.md)

## Constructors

| | |
|---|---|
| [ValidationUtils](-validation-utils.md) | [jvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [validateInputParametersWithConstraints](validate-input-parameters-with-constraints.md) | [jvm]<br>open fun [validateInputParametersWithConstraints](validate-input-parameters-with-constraints.md)(@NonNullvariableParameters: @NonNull[List](https://docs.oracle.com/javase/8/docs/api/java/util/List.html)&lt;[VariableParameter](../../ru.council.dxql.models/-variable-parameter/index.md)&gt;, @NonNullinputParameters: @NonNull[Map](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html)&lt;[String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;, @NonNullobjectMapper: @NonNullObjectMapper, overrideMessage: [Map](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html)&lt;[String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html)&gt;) |
| [validateMaximum](validate-maximum.md) | [jvm]<br>open fun [validateMaximum](validate-maximum.md)(@NonNullvp: @NonNull[VariableParameter](../../ru.council.dxql.models/-variable-parameter/index.md), passedValue: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), objectMapper: ObjectMapper)<br>open fun [validateMaximum](validate-maximum.md)(@NonNullvp: @NonNull[VariableParameter](../../ru.council.dxql.models/-variable-parameter/index.md), passedValue: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), objectMapper: ObjectMapper, maximum: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), inclusive: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [validateMaximumTimeDelta](validate-maximum-time-delta.md) | [jvm]<br>open fun [validateMaximumTimeDelta](validate-maximum-time-delta.md)(firstPairedParam: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), secondPairedParam: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), @NonNullfirstPairedParamType: @NonNull[Type](../../ru.council.dxql.enums/-type/index.md), @NonNullsecondPairedParamType: @NonNull[Type](../../ru.council.dxql.enums/-type/index.md), maximumTimeDelta: [MaximumTimeDelta](../../ru.council.dxql.models.constraints/-maximum-time-delta/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [validateMinimum](validate-minimum.md) | [jvm]<br>open fun [validateMinimum](validate-minimum.md)(@NonNullvp: @NonNull[VariableParameter](../../ru.council.dxql.models/-variable-parameter/index.md), passedValue: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), objectMapper: ObjectMapper)<br>open fun [validateMinimum](validate-minimum.md)(@NonNullvp: @NonNull[VariableParameter](../../ru.council.dxql.models/-variable-parameter/index.md), passedValue: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), objectMapper: ObjectMapper, minimum: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), inclusive: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [validateMinimumTimeDelta](validate-minimum-time-delta.md) | [jvm]<br>open fun [validateMinimumTimeDelta](validate-minimum-time-delta.md)(firstPairedParam: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), secondPairedParam: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), @NonNullfirstPairedParamType: @NonNull[Type](../../ru.council.dxql.enums/-type/index.md), @NonNullsecondPairedParamType: @NonNull[Type](../../ru.council.dxql.enums/-type/index.md), minimumTimeDelta: [MinimumTimeDelta](../../ru.council.dxql.models.constraints/-minimum-time-delta/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
