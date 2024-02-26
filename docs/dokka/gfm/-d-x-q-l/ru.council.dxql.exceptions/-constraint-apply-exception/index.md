//[DXQL](../../../index.md)/[ru.council.dxql.exceptions](../index.md)/[ConstraintApplyException](index.md)

# ConstraintApplyException

[jvm]\
open class [ConstraintApplyException](index.md) : [Exception](https://docs.oracle.com/javase/8/docs/api/java/lang/Exception.html)

## Constructors

| | |
|---|---|
| [ConstraintApplyException](-constraint-apply-exception.md) | [jvm]<br>constructor()constructor(message: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html))constructor(message: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), cause: [Throwable](https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html))constructor(cause: [Throwable](https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html))constructor(message: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html), cause: [Throwable](https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html), enableSuppression: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), writableStackTrace: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [cause](../-validation-exception/index.md#-1023347080%2FProperties%2F-1216412040) | [jvm]<br>open val [cause](../-validation-exception/index.md#-1023347080%2FProperties%2F-1216412040): [Throwable](https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html) |
| [stackTrace](../-validation-exception/index.md#1573944892%2FProperties%2F-1216412040) | [jvm]<br>open var [stackTrace](../-validation-exception/index.md#1573944892%2FProperties%2F-1216412040): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[StackTraceElement](https://docs.oracle.com/javase/8/docs/api/java/lang/StackTraceElement.html)&gt; |

## Functions

| Name | Summary |
|---|---|
| [addSuppressed](../-validation-exception/index.md#-1898257014%2FFunctions%2F-1216412040) | [jvm]<br>fun [addSuppressed](../-validation-exception/index.md#-1898257014%2FFunctions%2F-1216412040)(exception: [Throwable](https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html)) |
| [fillInStackTrace](../-validation-exception/index.md#-1207709164%2FFunctions%2F-1216412040) | [jvm]<br>open fun [fillInStackTrace](../-validation-exception/index.md#-1207709164%2FFunctions%2F-1216412040)(): [Throwable](https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html) |
| [getLocalizedMessage](../-validation-exception/index.md#-2138642817%2FFunctions%2F-1216412040) | [jvm]<br>open fun [getLocalizedMessage](../-validation-exception/index.md#-2138642817%2FFunctions%2F-1216412040)(): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [getMessage](../-validation-exception/index.md#1068546184%2FFunctions%2F-1216412040) | [jvm]<br>open fun [getMessage](../-validation-exception/index.md#1068546184%2FFunctions%2F-1216412040)(): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [getSuppressed](../-validation-exception/index.md#1678506999%2FFunctions%2F-1216412040) | [jvm]<br>fun [getSuppressed](../-validation-exception/index.md#1678506999%2FFunctions%2F-1216412040)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Throwable](https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html)&gt; |
| [initCause](../-validation-exception/index.md#-104903378%2FFunctions%2F-1216412040) | [jvm]<br>open fun [initCause](../-validation-exception/index.md#-104903378%2FFunctions%2F-1216412040)(cause: [Throwable](https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html)): [Throwable](https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html) |
| [printStackTrace](../-validation-exception/index.md#-1357294889%2FFunctions%2F-1216412040) | [jvm]<br>open fun [printStackTrace](../-validation-exception/index.md#-1357294889%2FFunctions%2F-1216412040)() |
| [toString](../-validation-exception/index.md#1869833549%2FFunctions%2F-1216412040) | [jvm]<br>open fun [toString](../-validation-exception/index.md#1869833549%2FFunctions%2F-1216412040)(): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
