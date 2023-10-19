package ru.council.dxql.models;

import lombok.*;
import ru.council.dxql.interfaces.ValidatedModel;
import ru.council.dxql.models.validation.ValidationResult;
import ru.council.metan.merger.Merger;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "query")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QueryDefinition implements ValidatedModel {

    @XmlAttribute(name = "name", required = true)
    private String queryIdentifier;

    @XmlAttribute(name = "byTemplate")
    private String templateReference;

    @XmlAttribute(name = "bySelect")
    private String selectReference;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "const-parameter")
    private List<ConstantParameter> constantParameters = new ArrayList<>();

    @XmlElementWrapper(name = "variables")
    @XmlElement(name = "variable")
    private List<VariableParameter> variableParameters = new ArrayList<>();

    @XmlElement(name = "result", required = true)
    private Result resultSpecification;

    @XmlElementWrapper(name = "mergers")
    @XmlElement(name = "merge")
    private List<MergerConfiguration> mergers = new ArrayList<>();

    @Override
    public ValidationResult validate() {
        if (templateReference == null && selectReference == null) {
            return new ValidationResult("No reference specified for query definition '%s'!", getQueryIdentifier());
        }
        if (selectReference != null) {
            if (templateReference != null) {
                return new ValidationResult("Both template reference and select reference are specified for '%s'", getQueryIdentifier());
            }
            if (getConstantParameters().size() != 0 || getVariableParameters().size() != 0)
                return new ValidationResult("Cannot specify any type parameters for non-template query '%s'!", getQueryIdentifier());
        } else {
            for (ConstantParameter cp : getConstantParameters()) {
                ValidationResult cpValidation = cp.validate();
                if (!cpValidation.isValid()) {
                    return new ValidationResult("Failed to validate child element of '%s' with message '%s'!", getQueryIdentifier(), cpValidation.getMessage());
                }
            }
            for (VariableParameter vp : getVariableParameters()) {
                ValidationResult vpValidation = vp.validate();
                if (!vpValidation.isValid()) {
                    return new ValidationResult("Failed to validate child element of '%s' with message '%s'!", getQueryIdentifier(), vpValidation.getMessage());
                }
            }
        }
        return new ValidationResult();
    }

    public Merger getMergerByWithParameter(String with) {
        for (MergerConfiguration mergerConfiguration: mergers) {
            if (mergerConfiguration.getWith().equals(with)) {
                return mergerConfiguration.getMerger();
            }
        }
        return null;
    }
}
