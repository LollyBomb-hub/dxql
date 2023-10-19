package ru.council.dxql.models;

import lombok.*;
import ru.council.dxql.interfaces.ValidatedModel;
import ru.council.dxql.models.validation.ValidationResult;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "select")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SelectDefinition implements ValidatedModel {

    @XmlAttribute(name = "name", required = true)
    private String queryIdentifier;

    @XmlValue
    private String query;

    @Override
    public ValidationResult validate() {
        if (getQuery().isBlank()) {
            return new ValidationResult("Select query '%s' is blank!", getQueryIdentifier());
        }
        return new ValidationResult();
    }
}
