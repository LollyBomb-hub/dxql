package ru.council.dxql.executors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import ru.council.dxql.DXQLConfiguration;
import ru.council.dxql.models.ConstantParameter;
import ru.council.metan.jdbc.MetaJsonRowMapper;
import ru.council.metan.models.MetaJson;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SqlExecutor {

    @Getter
    protected final DXQLConfiguration configuration;
    protected final DataSourceTransactionManager dataSourceTransactionManager;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final NamedParameterJdbcTemplate namedTemplate;
    @Getter
    private final TransactionTemplate transactionTemplate;

    public SqlExecutor(JdbcTemplate template, DXQLConfiguration configuration) {
        Objects.requireNonNull(template.getDataSource());
        this.namedTemplate = new NamedParameterJdbcTemplate(template);
        this.configuration = configuration;
        dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(namedTemplate.getJdbcTemplate().getDataSource());
        transactionTemplate = new TransactionTemplate(dataSourceTransactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    protected void execute(String sql) {
        namedTemplate.execute(sql, PreparedStatement::execute);
    }

    protected void execute(String sql, Map<String, Object> params) {
        namedTemplate.execute(sql, params, PreparedStatement::execute);
    }

    private MetaJson postProcessRowMapper(MetaJsonRowMapper rowMapper) {
        return rowMapper.getJson();
    }

    protected MetaJson executeSQL(String sql, MetaJsonRowMapper rowMapper) {
        namedTemplate.query(sql, rowMapper);

        return postProcessRowMapper(rowMapper);
    }

    protected MetaJson executeSQL(String sql, Map<String, Object> params, MetaJsonRowMapper rowMapper) {
        namedTemplate.query(sql, params, rowMapper);

        return postProcessRowMapper(rowMapper);
    }

    protected Map<String, Object> getConstantParametersMap(List<ConstantParameter> constantParameters) throws JsonProcessingException {
        Map<String, Object> result = new HashMap<>();

        for (ConstantParameter parameter : constantParameters) {
            result.put(parameter.getName(), objectMapper.readValue(parameter.getValue(), parameter.getType().getTypeClass()));
        }

        return result;
    }

}
