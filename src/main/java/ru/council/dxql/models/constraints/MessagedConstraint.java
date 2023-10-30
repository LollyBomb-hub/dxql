package ru.council.dxql.models.constraints;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Getter
@ToString
public class MessagedConstraint {
    @XmlAttribute(name = "override-constraint-failure-message")
    private String overrideMessage;
    @XmlElement(name = "on-constraint-failure-message")
    private String message;
}
