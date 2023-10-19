package ru.council.dxql.models.constraints;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Getter
@ToString
public class TargetedMinimum extends MessagedConstraint {
    @XmlAttribute(name = "target")
    private String targetParameterName;
    @XmlAttribute(name = "inclusive")
    private boolean inclusive;
    @XmlElement(name = "minimum")
    private String minimum;
}
