package ru.council.dxql;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.xml.sax.SAXException;
import ru.council.dxql.interfaces.ValidatedModel;
import ru.council.dxql.models.*;
import ru.council.dxql.models.validation.ValidationResult;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

@XmlRootElement(name = "dxql")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@ToString
public class DXQLConfiguration implements ValidatedModel {

    @XmlElementWrapper(name = "global-variables")
    @XmlElement(name = "variable")
    private List<VariableParameter> variableParameters = new ArrayList<>();

    @XmlElement(name = "used-schema")
    private String schema = "public";

    @XmlElement(name = "used-locale")
    private String usedLocale = Locale.US.toString();

    @XmlElementWrapper(name = "temporary-tables")
    @XmlElement(name = "temporary-table")
    private List<TemporaryTableDefinition> temporaryTableDefinitions = new ArrayList<>();

    @XmlElementWrapper(name = "selects")
    @XmlElement(name = "select")
    private List<SelectDefinition> selectDefinitions = new ArrayList<>();

    @XmlElementWrapper(name = "templates")
    @XmlElement(name = "template-select")
    private List<TemplateSelectDefinition> templateSelectDefinitions = new ArrayList<>();

    @XmlElement(name = "query")
    private List<QueryDefinition> queryDefinitions = new ArrayList<>();

    public static DXQLConfiguration from(@NonNull InputStream is) throws JAXBException, IOException, SAXException {
        Unmarshaller jaxbUnmarshaller;
        JAXBContext jaxbContext = JAXBContext.newInstance(DXQLConfiguration.class);
        SchemaHolder holder = new SchemaHolder();
        jaxbContext.generateSchema(holder);
        ByteArrayOutputStream schemaStream = holder.getSchemaStream();
        StreamSource streamSource = new StreamSource();
        byte[] bytes = schemaStream.toByteArray();
        streamSource.setInputStream(new ByteArrayInputStream(bytes));
        schemaStream.close();
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxbUnmarshaller.setSchema(SchemaFactory.newDefaultInstance().newSchema(streamSource));
        return (DXQLConfiguration) jaxbUnmarshaller.unmarshal(is);
    }

    public static DXQLConfiguration from(@NonNull String pathToConfig) throws JAXBException, IOException, SAXException {
        return from(Path.of(pathToConfig));
    }

    public static DXQLConfiguration from(@NonNull Path path) throws JAXBException, IOException, SAXException {
        return from(new FileInputStream(path.toFile()));
    }

    public SelectDefinition getSelectByName(String name) {
        return getByIdentifier(selectDefinitions, SelectDefinition::getQueryIdentifier, name);
    }

    public TemplateSelectDefinition getTemplateByName(String name) {
        return getByIdentifier(templateSelectDefinitions, TemplateSelectDefinition::getTemplateIdentifier, name);
    }

    public QueryDefinition getQueryByName(String name) {
        return getByIdentifier(queryDefinitions, QueryDefinition::getQueryIdentifier, name);
    }

    public <T, S> T getByIdentifier(List<T> source, Function<T, S> identifierChecker, S identifier) {
        Optional<T> first = source.stream().filter(el -> identifierChecker.apply(el).equals(identifier)).findFirst();

        return first.isEmpty() ? null : first.get();
    }

    @Override
    public ValidationResult validate() {
        for (VariableParameter vp : getVariableParameters()) {
            ValidationResult vpValidation = vp.validate();
            if (!vpValidation.isValid()) {
                return new ValidationResult("Failed to validate child element of '%s' with message '%s'!", vp.getName(), vpValidation.getMessage());
            }
        }
        for (TemporaryTableDefinition ttd : getTemporaryTableDefinitions()) {
            ValidationResult validate = ttd.validate();
            if (!validate.isValid()) {
                return new ValidationResult("Failed validation of temporary table '%s'", ttd.getName(), validate.getMessage());
            }
        }
        for (TemplateSelectDefinition tsd : getTemplateSelectDefinitions()) {
            ValidationResult validate = tsd.validate();
            if (!validate.isValid()) {
                return new ValidationResult("Failed validation of template select '%s'", tsd.getTemplateIdentifier(), validate.getMessage());
            }
        }
        for (SelectDefinition sd : getSelectDefinitions()) {
            ValidationResult validate = sd.validate();
            if (!validate.isValid()) {
                return new ValidationResult("Failed validation of select '%s'", sd.getQueryIdentifier(), validate.getMessage());
            }
        }
        for (QueryDefinition qd : getQueryDefinitions()) {
            ValidationResult qdValidation = qd.validate();
            if (!qdValidation.isValid()) {
                return new ValidationResult("Failed validation of query '%s' with message '%s'", qd.getQueryIdentifier(), qdValidation.getMessage());
            }
        }
        return new ValidationResult();
    }

    @Getter
    private static class SchemaHolder extends SchemaOutputResolver {

        private final ByteArrayOutputStream schemaStream = new ByteArrayOutputStream();

        @Override
        public Result createOutput(String namespaceUri, String suggestedFileName) {
            StreamResult streamResult = new StreamResult(schemaStream);
            streamResult.setSystemId(UUID.randomUUID().toString());
            return streamResult;
        }

    }
}
