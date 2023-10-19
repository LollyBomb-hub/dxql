package ru.council.dxql.exceptions;

public class RequiredVariableParameterNotFoundException extends RuntimeException {
    public RequiredVariableParameterNotFoundException(String message) {
        super(message);
    }
}
