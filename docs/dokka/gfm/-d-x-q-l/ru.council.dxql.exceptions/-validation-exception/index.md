//[DXQL](../../../index.md)/[ru.council.dxql.exceptions](../index.md)/[ValidationException](index.md)

# ValidationException

[jvm]\
open class [ValidationException](index.md) : [RuntimeException](https://docs.oracle.com/javase/8/docs/api/java/lang/RuntimeException.html)

## Constructors

| | |
|---|---|
| [ValidationException](-validation-exception.md) | [jvm]<br>constructor(message: [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html)) |

## Properties

| Name | Summary |
|---|---|
| [cause](index.md#-1023347080%2FProperties%2F-1216412040) | [jvm]<br>open val [cause](index.md#-1023347080%2FProperties%2F-1216412040): [Throwable](https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html) |
| [stackTrace](index.md#1573944892%2FProperties%2F-1216412040) | [jvm]<br>open var [stackTrace](index.md#1573944892%2FProperties%2F-1216412040): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[StackTraceElement](https://docs.oracle.com/javase/8/docs/api/java/lang/StackTraceElement.html)&gt; |

## Functions

| Name | Summary |
|---|---|
| [addSuppressed](index.md#-1898257014%2FFunctions%2F-1216412040) | [jvm]<br>fun [addSuppressed](index.md#-1898257014%2FFunctions%2F-1216412040)(exception: [Throwable](https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html)) |
| [fillInStackTrace](index.md#-1207709164%2FFunctions%2F-1216412040) | [jvm]<br>open fun [fillInStackTrace](index.md#-1207709164%2FFunctions%2F-1216412040)(): [Throwable](https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html) |
| [getLocalizedMessage](index.md#-2138642817%2FFunctions%2F-1216412040) | [jvm]<br>open fun [getLocalizedMessage](index.md#-2138642817%2FFunctions%2F-1216412040)(): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [getMessage](index.md#1068546184%2FFunctions%2F-1216412040) | [jvm]<br>open fun [getMessage](index.md#1068546184%2FFunctions%2F-1216412040)(): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
| [getSuppressed](index.md#1678506999%2FFunctions%2F-1216412040) | [jvm]<br>fun [getSuppressed](index.md#1678506999%2FFunctions%2F-1216412040)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Throwable](https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html)&gt; |
| [initCause](index.md#-104903378%2FFunctions%2F-1216412040) | [jvm]<br>open fun [initCause](index.md#-104903378%2FFunctions%2F-1216412040)(cause: [Throwable](https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html)): [Throwable](https://docs.oracle.com/javase/8/docs/api/java/lang/Throwable.html) |
| [printStackTrace](index.md#-1357294889%2FFunctions%2F-1216412040) | [jvm]<br>open fun [printStackTrace](index.md#-1357294889%2FFunctions%2F-1216412040)() |
| [toString](index.md#1869833549%2FFunctions%2F-1216412040) | [jvm]<br>open fun [toString](index.md#1869833549%2FFunctions%2F-1216412040)(): [String](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html) |
