package ru.council.dxql.models;

import lombok.*;
import ru.council.metan.merger.Merger;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "merge")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MergerConfiguration {

    @XmlAttribute(name = "with", required = true)
    private String with;

    @XmlElement(name = "merger", required = true)
    private Merger merger;

}
