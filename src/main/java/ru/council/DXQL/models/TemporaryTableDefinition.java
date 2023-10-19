package ru.council.dxql.models;

import lombok.*;
import ru.council.dxql.interfaces.ValidatedModel;
import ru.council.dxql.models.validation.ValidationResult;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "temporary-table")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TemporaryTableDefinition implements ValidatedModel {

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlElementWrapper(name = "variables")
    @XmlElement(name = "variable")
    private List<VariableParameter> variableParameters = new ArrayList<>();

    @XmlElement(name = "sql")
    private String sql;

    @Override
    public ValidationResult validate() {
        if (getSql().isBlank()) {
            return new ValidationResult("Temporary table query for '%s' is blank!", getName());
        }
        for (VariableParameter vp : getVariableParameters()) {
            ValidationResult vpValidation = vp.validate();
            if (!vpValidation.isValid()) {
                return new ValidationResult("Failed to validate child element of '%s' with message '%s'!", getName(), vpValidation.getMessage());
            }
        }
        return new ValidationResult();
    }
}
