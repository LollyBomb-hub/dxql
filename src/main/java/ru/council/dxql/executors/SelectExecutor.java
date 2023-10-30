package ru.council.dxql.executors;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.council.dxql.DXQLConfiguration;
import ru.council.dxql.models.SelectDefinition;
import ru.council.metan.jdbc.MetaJsonRowMapper;
import ru.council.metan.models.MetaJson;

public class SelectExecutor extends SqlExecutor {

    public SelectExecutor(JdbcTemplate template, DXQLConfiguration configuration) {
        super(template, configuration);
    }

    protected MetaJson executeSelect(String selectReference, MetaJsonRowMapper rowMapper) {
        return executeSelect(configuration.getSelectByName(selectReference), rowMapper);
    }

    protected MetaJson executeSelect(SelectDefinition selectDefinition, MetaJsonRowMapper rowMapper) {
        if (selectDefinition == null) {
            throw new IllegalStateException("Not found select");
        }
        return executeSQL(selectDefinition.getQuery(), rowMapper);
    }
}
