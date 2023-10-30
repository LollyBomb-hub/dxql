package ru.council.dxql;

import lombok.NonNull;
import ru.council.dxql.enums.Type;
import ru.council.dxql.exceptions.ConstraintApplyException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;

public class TypeUtils {

    public static long determineMillis(Object value, @NonNull Type typeOfGivenValue) throws ConstraintApplyException {
        if (value == null) {
            throw new ConstraintApplyException("Passed value is null!");
        }
        switch (typeOfGivenValue) {
            case Date:
            case DateWithTime:
                if (value instanceof Timestamp) {
                    Timestamp fromTimestamp = (Timestamp) value;
                    return fromTimestamp.getTime();
                } else if (value instanceof Instant) {
                    return ((Instant) value).toEpochMilli();
                } else if (value instanceof Temporal) {
                    return ((Temporal) value).get(ChronoField.INSTANT_SECONDS);
                } else {
                    Class<?> valueClass = value.getClass();
                    try {
                        Method toInstant = valueClass.getMethod("toInstant");
                        Object invoke = toInstant.invoke(value);
                        if (invoke instanceof Instant) {
                            return ((Instant) invoke).toEpochMilli();
                        } else {
                            throw new ConstraintApplyException("Could not apply time delta constraint to value of type " + value.getClass() + "!");
                        }
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        throw new ConstraintApplyException("Could not apply time delta constraint to value of type " + value.getClass() + "!");
                    }
                }
            case Time:
                if (value instanceof Time) {
                    return ((Time) value).getTime();
                } else {
                    throw new ConstraintApplyException("Could not apply time delta constraint to value of type " + value.getClass() + "!");
                }
            case LocalDate:
                if (value instanceof Date) {
                    return ((Date) value).getTime();
                } else {
                    throw new ConstraintApplyException("Could not apply time delta constraint to value of type " + value.getClass() + "!");
                }
            default:
                throw new ConstraintApplyException("Unsupported type of passed parameter!");
        }
    }

}
