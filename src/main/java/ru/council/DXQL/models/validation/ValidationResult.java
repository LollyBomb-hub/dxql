package ru.council.dxql.models.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ValidationResult {

    private boolean isValid = true;
    private String message = null;

    public ValidationResult(String fmt, String... params) {
        isValid = false;
        message = String.format(fmt, params);
    }

}
