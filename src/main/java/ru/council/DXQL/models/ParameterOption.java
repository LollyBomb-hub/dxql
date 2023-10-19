package ru.council.dxql.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import ru.council.dxql.interfaces.AliasedModel;
import ru.council.dxql.models.constraints.*;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Getter
@ToString
public class ParameterOption implements AliasedModel {

    @XmlAttribute(name = "alias")
    private String alias;

    @XmlAttribute(name = "value", required = true)
    private String value;

    @XmlElementWrapper(name = "minimum-constraints")
    @XmlElement(name = "mins")
    private List<TargetedMinimum> minimumConstraints;

    @XmlElementWrapper(name = "maximum-constraints")
    @XmlElement(name = "maxs")
    private List<TargetedMaximum> maximumConstraints;

    @XmlElementWrapper(name = "maximum-time-delta-constraints")
    @XmlElement(name = "maximum-time-delta")
    private List<TargetedMaximumTimeDelta> targetedMaximumTimeDeltas;

    @XmlElementWrapper(name = "minimum-time-delta-constraints")
    @XmlElement(name = "minimum-time-delta")
    private List<TargetedMinimumTimeDelta> targetedMinimumTimeDeltas;

}
