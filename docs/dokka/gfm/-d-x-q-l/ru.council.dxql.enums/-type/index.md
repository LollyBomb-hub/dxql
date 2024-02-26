//[DXQL](../../../index.md)/[ru.council.dxql.enums](../index.md)/[Type](index.md)

# Type

[jvm]\
enum [Type](index.md)

## Entries

| | |
|---|---|
| [Null](-null/index.md) | [jvm]<br>[Null](-null/index.md) |
| [Float](-float/index.md) | [jvm]<br>[Float](-float/index.md) |
| [Double](-double/index.md) | [jvm]<br>[Double](-double/index.md) |
| [Int](-int/index.md) | [jvm]<br>[Int](-int/index.md) |
| [Long](-long/index.md) | [jvm]<br>[Long](-long/index.md) |
| [String](-string/index.md) | [jvm]<br>[String](-string/index.md) |
| [LocalDate](-local-date/index.md) | [jvm]<br>[LocalDate](-local-date/index.md) |
| [DateWithTime](-date-with-time/index.md) | [jvm]<br>[DateWithTime](-date-with-time/index.md) |
| [Date](-date/index.md) | [jvm]<br>[Date](-date/index.md) |
| [Time](-time/index.md) | [jvm]<br>[Time](-time/index.md) |
| [TimeInterval](-time-interval/index.md) | [jvm]<br>[TimeInterval](-time-interval/index.md) |
| [Array](-array/index.md) | [jvm]<br>[Array](-array/index.md) |

## Functions

| Name | Summary |
|---|---|
| [getTypeByClass](get-type-by-class.md) | [jvm]<br>open fun [getTypeByClass](get-type-by-class.md)(clazz: [Class](https://docs.oracle.com/javase/8/docs/api/java/lang/Class.html)&lt;out [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;): [Type](index.md) |
| [validateMaximum](validate-maximum.md) | [jvm]<br>open fun [validateMaximum](validate-maximum.md)(minValue: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), passedValue: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), objectMapper: ObjectMapper, inclusive: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [validateMinimum](validate-minimum.md) | [jvm]<br>open fun [validateMinimum](validate-minimum.md)(minValue: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), passedValue: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), objectMapper: ObjectMapper, inclusive: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [valueOf](value-of.md) | [jvm]<br>open fun [valueOf](value-of.md)(name: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html)): [Type](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [jvm]<br>open fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Type](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. This method may be used to iterate over the constants. |
