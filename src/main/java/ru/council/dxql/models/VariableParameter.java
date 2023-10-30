package ru.council.dxql.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import ru.council.dxql.enums.Type;
import ru.council.dxql.interfaces.AliasedModel;
import ru.council.dxql.interfaces.ValidatedModel;
import ru.council.dxql.models.constraints.Maximum;
import ru.council.dxql.models.constraints.MaximumTimeDelta;
import ru.council.dxql.models.constraints.Minimum;
import ru.council.dxql.models.constraints.MinimumTimeDelta;
import ru.council.dxql.models.validation.ValidationResult;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Getter
@ToString
public class VariableParameter implements ValidatedModel, AliasedModel {

    @XmlAttribute(name = "alias")
    private String alias;

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlAttribute(name = "type", required = true)
    private Type type;

    @XmlAttribute(name = "isRequired", required = true)
    private boolean required;

    @XmlAttribute(name = "referenceTable")
    private String referenceTable;

    @XmlAttribute(name = "referenceColumnName")
    private String referenceColumnName;

    @XmlAttribute(name = "default")
    private String defaultValue = null;

    @XmlAttribute(name = "placeholder")
    private String placeholder = null;

    @XmlElement(name = "option")
    private List<ParameterOption> options;

    @XmlElement(name = "min")
    private Minimum minimum;

    @XmlElement(name = "max")
    private Maximum maximum;

    @XmlElementWrapper(name = "maximum-time-delta-constraints")
    @XmlElement(name = "maximum-time-delta")
    private List<MaximumTimeDelta> maximumTimeDeltas;

    @XmlElementWrapper(name = "minimum-time-delta-constraints")
    @XmlElement(name = "minimum-time-delta")
    private List<MinimumTimeDelta> minimumTimeDeltas;

    @Override
    public ValidationResult validate() {
        if (!isRequired() && defaultValue == null) {
            log.warn("Default value is null for field with name {}!", name);
            return new ValidationResult("Default value is null for field with name '%s'!", name);
        }
        return new ValidationResult();
    }

    public boolean isPlaceholderPresent() {
        return placeholder != null;
    }

    public boolean isTableReference() {
        return referenceTable != null;
    }

    public String getReferenceColumnName() {
        return Objects.requireNonNullElse(referenceColumnName, "id");
    }

    public boolean isSelect() {
        return options != null;
    }
}
