package ru.council.dxql.exceptions;

public class ConstraintApplyException extends Exception {

    public ConstraintApplyException() {
    }

    public ConstraintApplyException(String message) {
        super(message);
    }

    public ConstraintApplyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConstraintApplyException(Throwable cause) {
        super(cause);
    }

    public ConstraintApplyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
