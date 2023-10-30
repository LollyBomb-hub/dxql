package ru.council.dxql.executors;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.council.dxql.DXQLConfiguration;
import ru.council.dxql.enums.TargetFormat;
import ru.council.dxql.models.ConstantParameter;
import ru.council.dxql.models.QueryDefinition;
import ru.council.dxql.models.TemplateSelectDefinition;
import ru.council.metan.fullfill.json.ResultJson;
import ru.council.metan.jdbc.MetaJsonRowMapper;
import ru.council.metan.models.MetaJson;

import java.util.List;
import java.util.Map;

public class QueryExecutor extends TemplateExecutor {

    public QueryExecutor(JdbcTemplate template, DXQLConfiguration configuration) {
        super(template, configuration);
    }

    protected MetaJson executeQuery(String name, TargetFormat format) throws JsonProcessingException {
        QueryDefinition queryByName = configuration.getQueryByName(name);

        return executeQuery(queryByName, format);
    }

    protected MetaJson executeQuery(String name, Map<String, Object> inputMap, TargetFormat format) throws JsonProcessingException {
        QueryDefinition queryByName = configuration.getQueryByName(name);

        // Executing query with given parameters
        return executeQuery(queryByName, inputMap, format);
    }

    protected MetaJson executeQuery(QueryDefinition query, Map<String, Object> inputMap, TargetFormat format) throws JsonProcessingException {
        ResultJson resultJson = query.getResultSpecification().getResultJson();

        MetaJsonRowMapper rowMapper = new MetaJsonRowMapper(resultJson);

        if (query.getTemplateReference() != null && query.getSelectReference() == null) {
            // Process by template
            String templateReference = query.getTemplateReference();

            List<ConstantParameter> constantParameters = query.getConstantParameters();

            Map<String, Object> constantParametersMap = getConstantParametersMap(constantParameters);

            constantParametersMap.putAll(inputMap);

            TemplateSelectDefinition templateByName = configuration.getTemplateByName(templateReference);

            return executeSQL(templateByName.getTemplateQuery(), constantParametersMap, rowMapper);
        } else if (query.getTemplateReference() == null && query.getSelectReference() != null) {
            // Process select
            String selectReference = query.getSelectReference();

            return executeSelect(selectReference, rowMapper);
        } else {
            throw new IllegalStateException("Invalid configuration for " + query.getQueryIdentifier() + ". Either bySelect and byTemplate specified either none of them");
        }
    }

    protected MetaJson executeQuery(QueryDefinition query, TargetFormat format) throws JsonProcessingException {
        ResultJson resultJson = query.getResultSpecification().getResultJson();

        MetaJsonRowMapper rowMapper = new MetaJsonRowMapper(resultJson);

        if (query.getTemplateReference() != null && query.getSelectReference() == null) {
            // Process by template
            String templateReference = query.getTemplateReference();

            return executeTemplate(templateReference, query.getConstantParameters(), rowMapper);
        } else if (query.getTemplateReference() == null && query.getSelectReference() != null) {
            // Process select
            String selectReference = query.getSelectReference();

            return executeSelect(selectReference, rowMapper);
        } else {
            throw new IllegalStateException("Invalid configuration for " + query.getQueryIdentifier() + ". Either bySelect and byTemplate specified either none of them");
        }
    }

}
