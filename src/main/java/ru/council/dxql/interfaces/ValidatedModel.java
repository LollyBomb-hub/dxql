package ru.council.dxql.interfaces;

import ru.council.dxql.models.validation.ValidationResult;

public interface ValidatedModel {

    ValidationResult validate();

}
