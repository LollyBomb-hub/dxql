package ru.council.dxql.models;


import lombok.*;
import ru.council.dxql.interfaces.ValidatedModel;
import ru.council.dxql.models.validation.ValidationResult;

import javax.xml.bind.annotation.*;


@XmlRootElement(name = "template-select")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TemplateSelectDefinition implements ValidatedModel {

    @XmlAttribute(name = "name")
    private String templateIdentifier;

    @XmlValue
    private String templateQuery;

    @Override
    public ValidationResult validate() {
        if (getTemplateQuery().isBlank()) {
            return new ValidationResult("Template query '%s' is blank!", getTemplateIdentifier());
        }
        return new ValidationResult();
    }
}
