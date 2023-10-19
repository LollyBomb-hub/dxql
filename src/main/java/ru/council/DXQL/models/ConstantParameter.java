package ru.council.dxql.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.council.dxql.enums.Type;
import ru.council.dxql.interfaces.ValidatedModel;
import ru.council.dxql.models.validation.ValidationResult;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "const-parameter")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ConstantParameter implements ValidatedModel {

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlAttribute(name = "type", required = true)
    private Type type;

    @XmlAttribute(name = "value", required = true)
    private String value = "";

    @Override
    public ValidationResult validate() {
        return new ValidationResult();
    }
}
