package ru.council.dxql.enums;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.council.dxql.exceptions.ConstraintApplyException;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import java.sql.Array;

@XmlEnum
@Getter
@RequiredArgsConstructor
public enum Type {

    @XmlEnumValue("null") Null(java.lang.Object.class),
    @XmlEnumValue("float") Float(Float.class),
    @XmlEnumValue("double") Double(Double.class),
    @XmlEnumValue("integer") Int(Integer.class),
    @XmlEnumValue("long") Long(Long.class),
    @XmlEnumValue("string") String(String.class),
    @XmlEnumValue("local_date") LocalDate(java.sql.Date.class),
    @XmlEnumValue("date_with_time") DateWithTime(java.sql.Timestamp.class),
    @XmlEnumValue("date") Date(java.sql.Timestamp.class),
    @XmlEnumValue("time") Time(java.sql.Time.class),
    @XmlEnumValue("time_interval") TimeInterval(String.class),
    @XmlEnumValue("array") Array(Array.class);

    private final Class<?> typeClass;

    public static Type getTypeByClass(Class<?> clazz) {
        for (Type t : values()) {
            if (t.getTypeClass().equals(clazz)) {
                return t;
            }
        }
        return null;
    }

    public boolean validateMinimum(String minValue, Object passedValue, ObjectMapper objectMapper, boolean inclusive) throws ConstraintApplyException, JsonProcessingException {
        switch (this) {
            case Float:
            case Double:
                java.lang.Double aDouble = objectMapper.readValue(minValue, java.lang.Double.class);
                if (aDouble == null) {
                    throw new ConstraintApplyException("Supplied min value was null!");
                }
                if (passedValue instanceof java.lang.Double) {
                    if (inclusive) {
                        return (java.lang.Double) passedValue >= aDouble;
                    }
                    return (java.lang.Double) passedValue > aDouble;
                } else if (passedValue instanceof java.lang.Float) {
                    if (inclusive) {
                        return (java.lang.Float) passedValue >= aDouble;
                    }
                    return (java.lang.Float) passedValue > aDouble;
                } else {
                    throw new ConstraintApplyException("Passed parameter value is not of needed type!");
                }
            case Int:
            case Long:
                java.lang.Long aLong = objectMapper.readValue(minValue, java.lang.Long.class);
                if (aLong == null) {
                    throw new ConstraintApplyException("Supplied min value was null!");
                }
                if (passedValue instanceof java.lang.Long) {
                    if (inclusive) {
                        return (java.lang.Long) passedValue >= aLong;
                    }
                    return (java.lang.Long) passedValue > aLong;
                } else if (passedValue instanceof java.lang.Integer) {
                    if (inclusive) {
                        return (java.lang.Integer) passedValue >= aLong;
                    }
                    return (java.lang.Integer) passedValue > aLong;
                } else {
                    throw new ConstraintApplyException("Passed parameter value is not of needed type!");
                }
            default:
                throw new ConstraintApplyException("Unsupported type of parameter!");
        }
    }

    public boolean validateMaximum(String minValue, Object passedValue, ObjectMapper objectMapper, boolean inclusive) throws JsonProcessingException, ConstraintApplyException {
        switch (this) {
            case Float:
            case Double:
                java.lang.Double aDouble = objectMapper.readValue(minValue, java.lang.Double.class);
                if (aDouble == null) {
                    throw new ConstraintApplyException("Supplied min value was null!");
                }
                if (passedValue instanceof java.lang.Double) {
                    if (inclusive) {
                        return (java.lang.Double) passedValue <= aDouble;
                    }
                    return (java.lang.Double) passedValue < aDouble;
                } else if (passedValue instanceof java.lang.Float) {
                    if (inclusive) {
                        return (java.lang.Float) passedValue <= aDouble;
                    }
                    return (java.lang.Float) passedValue < aDouble;
                } else {
                    throw new ConstraintApplyException("Passed parameter value is not of needed type!");
                }
            case Int:
            case Long:
                java.lang.Long aLong = objectMapper.readValue(minValue, java.lang.Long.class);
                if (aLong == null) {
                    throw new ConstraintApplyException("Supplied min value was null!");
                }
                if (passedValue instanceof java.lang.Long) {
                    if (inclusive) {
                        return (java.lang.Long) passedValue <= aLong;
                    }
                    return (java.lang.Long) passedValue < aLong;
                } else if (passedValue instanceof java.lang.Integer) {
                    if (inclusive) {
                        return (java.lang.Integer) passedValue <= aLong;
                    }
                    return (java.lang.Integer) passedValue < aLong;
                } else {
                    throw new ConstraintApplyException("Passed parameter value is not of needed type!");
                }
            default:
                throw new ConstraintApplyException("Unsupported type of parameter!");
        }
    }

}
