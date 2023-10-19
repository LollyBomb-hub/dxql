package ru.council.dxql.models;

import lombok.*;
import ru.council.metan.fullfill.json.ResultJson;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "result")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Result {

    @XmlElement(name = "json")
    private ResultJson resultJson;

}
