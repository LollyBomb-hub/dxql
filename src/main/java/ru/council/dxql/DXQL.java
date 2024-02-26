package ru.council.dxql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import ru.council.dxql.enums.TargetFormat;
import ru.council.dxql.enums.Type;
import ru.council.dxql.exceptions.*;
import ru.council.dxql.executors.QueryExecutor;
import ru.council.dxql.models.*;
import ru.council.metan.enums.JsonElementType;
import ru.council.metan.merger.JsonMerger;
import ru.council.metan.merger.Merger;
import ru.council.metan.models.MetaJson;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * <p>
 *     Класс DXQL. Реализует основную логику DXQL.
 * </p>
 *
 * @author a.pesterev
 */
@Slf4j
@Getter
@Setter
@ToString
@SuppressWarnings("unused")
public class DXQL extends QueryExecutor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * <p>
     *     Конструктор DXQL. Инициализирует объект DXQL и используется для генерации данных по конфигурации, а также осуществляет проверки поступающих данных
     * </p>
     *
     * @param template template used to execute sql queries
     * @param configuration configuration of sql queries
     */
    public DXQL(@NonNull JdbcTemplate template, @NonNull DXQLConfiguration configuration) {
        super(template, configuration);
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static void checkAndFillInput(Map<String, Object> inputParameters, Map<String, Object> target, List<VariableParameter> variableParameters, ObjectMapper objectMapper) {
        for (VariableParameter vb : variableParameters) {
            if (!inputParameters.containsKey(vb.getName())) {
                if (vb.isRequired()) {
                    throw new RequiredVariableParameterNotFoundException("Variable parameter " + vb.getName() + " not provided in input parameters");
                } else {
                    if (vb.getDefaultValue() == null) {
                        if (vb.getType().equals(Type.Null)) {
                            target.put(vb.getName(), null);
                        } else {
                            throw new ValidationException("No default value can be used to resolve non-required parameter!");
                        }
                    } else {
                        try {
                            switch (vb.getType()) {
                                case String:
                                    target.put(vb.getName(), vb.getDefaultValue());
                                    break;
                                case TimeInterval:
                                    boolean notSet = true;
                                    List<ParameterOption> options = vb.getOptions();
                                    if (options != null) {
                                        for (ParameterOption option : options) {
                                            if (Objects.equals(option.getValue(), vb.getDefaultValue())) {
                                                target.put(vb.getName(), vb.getDefaultValue());
                                                notSet = false;
                                                break;
                                            }
                                        }
                                        if (notSet) {
                                            throw new IllegalStateException("Wrong default value specified!");
                                        }
                                    }
                                    break;
                                default:
                                    String defaultValue = vb.getDefaultValue();
                                    if (!(defaultValue.startsWith("\"") && defaultValue.endsWith("\""))) {
                                        defaultValue = "\"" + defaultValue + "\"";
                                    }
                                    Object readWithObjectMapper = objectMapper.readValue(defaultValue, vb.getType().getTypeClass());
                                    target.put(vb.getName(), readWithObjectMapper);
                                    break;
                            }
                        } catch (JsonProcessingException e) {
                            throw new IllegalCallerException("Could not deserialize String into given type class for variable parameter " + vb.getName(), e);
                        }
                    }
                }
            } else {
                Object providedValue = inputParameters.get(vb.getName());
                if (vb.getType().equals(Type.Null) && providedValue != null) {
                    throw new IllegalStateException("Null type value must be null!");
                } else {
                    if (providedValue != null) {
                        if (!providedValue.getClass().equals(vb.getType().getTypeClass())) {
                            throw new RequiredVariableParameterIsOfInvalidType("Variable parameter " + vb.getName() + " provided in input parameters is of invalid type " + providedValue.getClass());
                        }
                        if (Objects.equals(vb.getType(), Type.TimeInterval)) {
                            List<ParameterOption> options = vb.getOptions();
                            if (options != null) {
                                boolean notFound = true;
                                for (ParameterOption option : options) {
                                    if (Objects.equals(option.getValue(), providedValue)) {
                                        notFound = false;
                                        break;
                                    }
                                }
                                if (notFound) {
                                    throw new IllegalStateException("Wrong value passed!");
                                }
                            }
                        }
                    }
                }
                target.put(vb.getName(), providedValue);
            }
        }
    }

    /**
     * @param format формат результата
     * @return json, описанный в конфигурации
     */
    @Transactional
    public MetaJson executeWholeDocument(TargetFormat format) {
        List<QueryDefinition> queryDefinitions = preface();
        if (queryDefinitions == null) return null;

        for (QueryDefinition qd : queryDefinitions) {
            if (qd.getVariableParameters() != null && !qd.getVariableParameters().isEmpty()) {
                throw new ProcessingException("Attempt to call query pipeline that uses variable parameters without parameters");
            }
        }

        return getTransactionTemplate().execute(
                status -> {
                    String sql = "SET schema '" + configuration.getSchema() + "';";
                    execute(sql);
                    sql = "SET lc_time = '" + configuration.getUsedLocale() + "';";
                    execute(sql);
                    for (TemporaryTableDefinition ttd : configuration.getTemporaryTableDefinitions()) {
                        sql = "CREATE TEMPORARY TABLE " + ttd.getName() + " ON COMMIT DROP AS " + ttd.getSql();
                        execute(sql);
                    }

                    Map<QueryDefinition, MetaJson> resultsOfExecutingQueries;
                    try {
                        resultsOfExecutingQueries = executeQueries(queryDefinitions, format);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Could not process json", e);
                    }

                    return getResultMetaJsonFromExecutedQueriesAndRelations(queryDefinitions, resultsOfExecutingQueries, format);
                }
        );
    }

    /**
     * <p>
     *     Выполняет все запросы из конфигурации и возвращает json с результатами отдельного запроса
     * </p>
     * @param queryName название интересующего запроса из конфигурации
     * @param format формат результата
     * @return json, описанный в конфигурации
     */
    @Transactional
    public MetaJson executeWholeDocumentForQuery(String queryName, TargetFormat format) {
        return executeWholeDocument(format).resolve(queryName);
    }

    /**
     * <p>
     *     Выполняет все запросы из конфигурации и возвращает json.
     * </p>
     * @param givenParameters параметры для выполнения конфигурации
     * @param format формат результата
     * @param overrideMessage ассоциативный словарь с переопределениями текстовых сообщений об ошибках
     * @return результат выполнения в формате json
     * @throws ConstraintApplyException ошибка границ входных параметров
     * @throws JsonProcessingException ошибка обработки json
     */
    @Transactional
    public MetaJson executeWholeDocument(Map<String, Object> givenParameters, TargetFormat format, Map<String, String> overrideMessage) throws ConstraintApplyException, JsonProcessingException {
        List<QueryDefinition> queryDefinitions = preface();
        if (queryDefinitions == null) return null;

        log.info("Got params: {}", givenParameters);

        Map<String, Object> globalParameters = new HashMap<>();
        processVariableParameters(givenParameters, globalParameters, configuration.getVariableParameters(), overrideMessage);
        Map<QueryDefinition, Map<String, Object>> queryParams = new HashMap<>();
        for (QueryDefinition qd : queryDefinitions) {
            Map<String, Object> currentQueryParams = new HashMap<>(globalParameters);
            processVariableParameters(givenParameters, currentQueryParams, qd.getVariableParameters(), overrideMessage);
            queryParams.put(qd, currentQueryParams);
        }
        Map<TemporaryTableDefinition, Map<String, Object>> ttdParams = new HashMap<>();
        for (TemporaryTableDefinition ttd : configuration.getTemporaryTableDefinitions()) {
            Map<String, Object> temporaryTableParams = new HashMap<>(globalParameters);
            processVariableParameters(givenParameters, temporaryTableParams, ttd.getVariableParameters(), overrideMessage);
            ttdParams.put(ttd, temporaryTableParams);
        }

        log.info("Got global params: {}", globalParameters);

        TransactionTemplate transactionTemplate = getTransactionTemplate();

        return transactionTemplate.execute(
                status -> {
                    String sql;
                    if (configuration.getSchema().startsWith("${") && configuration.getSchema().endsWith("}")) {
                        sql = "SET schema '" + globalParameters.get(configuration.getSchema().substring(2, configuration.getSchema().length() - 1)) + "';";
                        execute(sql);
                    } else {
                        sql = "SET schema '" + configuration.getSchema() + "';";
                        execute(sql);
                    }
                    if (configuration.getUsedLocale().startsWith("${") && configuration.getUsedLocale().endsWith("}")) {
                        sql = "SET lc_time = '" + globalParameters.get(configuration.getUsedLocale().substring(2, configuration.getUsedLocale().length() - 1)) + "';";
                        execute(sql);
                    } else {
                        sql = "SET lc_time = '" + configuration.getUsedLocale() + "';";
                        execute(sql);
                    }
                    for (TemporaryTableDefinition ttd : configuration.getTemporaryTableDefinitions()) {
                        Map<String, Object> temporaryTableParams = ttdParams.get(ttd);
                        sql = "CREATE TEMPORARY TABLE " + ttd.getName() + " ON COMMIT DROP AS " + ttd.getSql();
                        execute(sql, temporaryTableParams);
                    }

                    Map<QueryDefinition, MetaJson> resultsOfExecutingQueries;
                    try {
                        resultsOfExecutingQueries = executeQueries(queryDefinitions, queryParams, format);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Could not process json", e);
                    }

                    return getResultMetaJsonFromExecutedQueriesAndRelations(queryDefinitions, resultsOfExecutingQueries, format);
                }
        );
    }

    @Transactional
    public MetaJson executeWholeDocumentForQuery(String queryName, Map<String, Object> givenParameters, TargetFormat format, Map<String, String> overrideMessage) throws ConstraintApplyException, JsonProcessingException {
        return executeWholeDocument(givenParameters, format, overrideMessage).resolve(queryName);
    }

    private List<QueryDefinition> preface() {
        log.debug("Getting all queries to execute");

        List<QueryDefinition> queryDefinitions = configuration.getQueryDefinitions();

        if (queryDefinitions == null || queryDefinitions.isEmpty()) {
            log.warn("Empty configuration!");
            return null;
        }

        if (log.isDebugEnabled()) {
            for (int indexOfQuery = 0; indexOfQuery < queryDefinitions.size(); indexOfQuery++) {
                log.debug("Query #{}: {}", indexOfQuery + 1, queryDefinitions.get(indexOfQuery).getQueryIdentifier());
            }
        }
        return queryDefinitions;
    }

    public void processVariableParameters(Map<String, Object> inputParameters, Map<String, Object> target, List<VariableParameter> variableParameters, Map<String, String> overrideMessage) throws ConstraintApplyException, JsonProcessingException {
        checkAndFillInput(inputParameters, target, variableParameters, objectMapper);
        ValidationUtils.validateInputParametersWithConstraints(
                variableParameters,
                target,
                objectMapper,
                overrideMessage
        );
    }

    private MetaJson getResultMetaJsonFromExecutedQueriesAndRelations(List<QueryDefinition> queryDefinitions, Map<QueryDefinition, MetaJson> resultsOfExecutingQueries, TargetFormat format) {
        Map<QueryDefinition, List<QueryDefinition>> queryRelationsMap = getQueryRelationsMap(queryDefinitions, format);
        Optional<QueryDefinition> firstParentModifier = queryDefinitions.stream().filter(el -> !queryRelationsMap.containsKey(el)).findFirst();

        if (firstParentModifier.isEmpty()) {
            throw new IllegalComponentStateException("Cannot find any root level result-json configurations!");
        }

        MetaJson result = new MetaJson(JsonElementType.Node);

        for (QueryDefinition qd : resultsOfExecutingQueries.keySet()) {
            MetaJson queried = resultsOfExecutingQueries.get(qd);
            if (queried != null) {
                result.put(qd.getQueryIdentifier(), queried);
            }
        }

        mergeResultsByRelations(queryDefinitions, resultsOfExecutingQueries, queryRelationsMap);

        return result;
    }

    private Map<QueryDefinition, List<QueryDefinition>> getQueryRelationsMap(List<QueryDefinition> queryDefinitions, TargetFormat format) {
        Map<QueryDefinition, List<QueryDefinition>> queryRelationsMap = new HashMap<>();

        if (Objects.requireNonNull(format) == TargetFormat.Json) {
            for (QueryDefinition currentObservableQuery : queryDefinitions) {
                for (MergerConfiguration merge : currentObservableQuery.getMergers()) {
                    String into = merge.getWith();

                    QueryDefinition parentQuery = configuration.getQueryByName(into);

                    if (parentQuery != null) {
                        if (queryRelationsMap.containsKey(parentQuery)) {
                            if (queryRelationsMap.get(parentQuery).contains(currentObservableQuery)) {
                                throw new IllegalStateException("Parent depends on child => cyclic dependency! [ parent: " + into + ", child: " + currentObservableQuery.getQueryIdentifier() + "]");
                            }
                        }
                        if (!queryRelationsMap.containsKey(currentObservableQuery)) {
                            queryRelationsMap.put(currentObservableQuery, new ArrayList<>());
                        }
                        log.debug("Current query {} -> parent {}", currentObservableQuery.getQueryIdentifier(), parentQuery.getQueryIdentifier());
                        queryRelationsMap.get(currentObservableQuery).add(parentQuery);
                    } else {
                        throw new NoSuchElementException("Not found parent with name " + into);
                    }
                }
            }
        } else {
            throw new FormatNotSupportedException("This format not supported yet");
        }

        return queryRelationsMap;
    }

    private Map<QueryDefinition, MetaJson> executeQueries(List<QueryDefinition> queries, TargetFormat format) throws JsonProcessingException {
        Map<QueryDefinition, MetaJson> result = new HashMap<>();
        for (QueryDefinition query : queries) {
            MetaJson queryResult = executeQuery(query, format);
            result.put(query, queryResult);
        }
        return result;
    }

    private Map<QueryDefinition, MetaJson> executeQueries(List<QueryDefinition> queries, Map<QueryDefinition, Map<String, Object>> queryParams, TargetFormat format) throws JsonProcessingException {
        Map<QueryDefinition, MetaJson> result = new HashMap<>();
        for (QueryDefinition query : queries) {
            MetaJson queryResult = executeQuery(query, queryParams.get(query), format);
            result.put(query, queryResult);
        }
        return result;
    }

    private void mergeResultsByRelations(List<QueryDefinition> queryDefinitions, Map<QueryDefinition, MetaJson> resultsOfExecutingQueries, Map<QueryDefinition, List<QueryDefinition>> queryRelationsMap) {
        log.debug("Query relations map: {}", queryRelationsMap);
        for (QueryDefinition query : queryDefinitions) {

            if (queryRelationsMap.containsKey(query) && queryRelationsMap.get(query) != null && !queryRelationsMap.get(query).isEmpty()) {
                for (QueryDefinition parentQuery : queryRelationsMap.get(query)) {
                    MetaJson parentJson = resultsOfExecutingQueries.get(parentQuery);
                    Merger mergerByWithParameter = query.getMergerByWithParameter(parentQuery.getQueryIdentifier());
                    MetaJson queryJson = resultsOfExecutingQueries.get(query);

                    if (mergerByWithParameter != null && parentJson != null && queryJson != null) {
                        log.debug("Merging query '{}' into query '{}'", query.getQueryIdentifier(), parentQuery.getQueryIdentifier());

                        JsonMerger.mergeJsons(mergerByWithParameter, parentJson, queryJson);
                    }
                }
            }
        }
    }

}