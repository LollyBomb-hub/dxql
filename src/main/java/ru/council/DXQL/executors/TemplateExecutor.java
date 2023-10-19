package ru.council.dxql.executors;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.council.dxql.DXQLConfiguration;
import ru.council.dxql.models.ConstantParameter;
import ru.council.dxql.models.TemplateSelectDefinition;
import ru.council.metan.jdbc.MetaJsonRowMapper;
import ru.council.metan.models.MetaJson;

import java.util.List;
import java.util.Map;

public class TemplateExecutor extends SelectExecutor {

    public TemplateExecutor(JdbcTemplate template, DXQLConfiguration configuration) {
        super(template, configuration);
    }

    protected MetaJson executeTemplate(String templateReference, List<ConstantParameter> constantParameters, MetaJsonRowMapper rowMapper) throws JsonProcessingException {
        return executeTemplate(configuration.getTemplateByName(templateReference), constantParameters, rowMapper);
    }

    protected MetaJson executeTemplate(TemplateSelectDefinition templateByReference, List<ConstantParameter> constantParameters, MetaJsonRowMapper rowMapper) throws JsonProcessingException {
        String sql = templateByReference.getTemplateQuery();

        Map<String, Object> parameters = getConstantParametersMap(constantParameters);

        return executeSQL(sql, parameters, rowMapper);
    }

}
