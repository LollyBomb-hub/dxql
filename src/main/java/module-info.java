open module ru.council.dxql {
    requires lombok;
    requires transitive java.xml.bind;
    requires transitive java.desktop;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.text;
    requires spring.jdbc;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires spring.tx;
    requires metan;
    exports ru.council.dxql;
    exports ru.council.dxql.enums;
    exports ru.council.dxql.exceptions;
    exports ru.council.dxql.executors;
    exports ru.council.dxql.interfaces;
    exports ru.council.dxql.models;
    exports ru.council.dxql.models.validation;
    exports ru.council.dxql.models.constraints;
}