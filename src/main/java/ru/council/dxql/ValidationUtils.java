package ru.council.dxql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.apache.commons.text.StringSubstitutor;
import ru.council.dxql.enums.Type;
import ru.council.dxql.exceptions.ConstraintApplyException;
import ru.council.dxql.models.ParameterOption;
import ru.council.dxql.models.VariableParameter;
import ru.council.dxql.models.constraints.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ValidationUtils {

    public static void validateInputParametersWithConstraints(
            @NonNull List<VariableParameter> variableParameters,
            @NonNull Map<String, Object> inputParameters,
            @NonNull ObjectMapper objectMapper,
            Map<String, String> overrideMessage
    ) throws ConstraintApplyException, JsonProcessingException {
        Map<String, VariableParameter> nameToVariableParameter = new HashMap<>();
        variableParameters.forEach(
                el -> nameToVariableParameter.put(el.getName(), el)
        );
        for (VariableParameter vp : variableParameters) {
            Object passedValue = inputParameters.get(vp.getName());
            Type type = vp.getType();
            if (vp.isSelect()) {
                List<ParameterOption> options = vp.getOptions();
                if (options != null) {
                    for (ParameterOption option : options) {
                        if (Objects.equals(option.getValue(), passedValue)) {
                            List<TargetedMinimum> minimumConstraints = option.getMinimumConstraints();
                            if (minimumConstraints != null) {
                                for (TargetedMinimum targetedMinimum : minimumConstraints) {
                                    String targetParameterValue = targetedMinimum.getTargetParameterName();
                                    validateMinimum(
                                            nameToVariableParameter.get(targetParameterValue),
                                            inputParameters.get(targetParameterValue),
                                            objectMapper,
                                            targetedMinimum.getMinimum(),
                                            targetedMinimum.isInclusive()
                                    );
                                }
                            }
                            List<TargetedMaximum> maximumConstraints = option.getMaximumConstraints();
                            if (maximumConstraints != null) {
                                for (TargetedMaximum targetedMaximum : maximumConstraints) {
                                    String targetParameterName = targetedMaximum.getTargetParameterName();
                                    validateMaximum(
                                            nameToVariableParameter.get(targetParameterName),
                                            inputParameters.get(targetParameterName),
                                            objectMapper,
                                            targetedMaximum.getMaximum(),
                                            targetedMaximum.isInclusive()
                                    );
                                }
                            }
                            List<TargetedMaximumTimeDelta> timeDeltaConstraints = option.getTargetedMaximumTimeDeltas();
                            if (timeDeltaConstraints != null) {
                                for (TargetedMaximumTimeDelta mtd : timeDeltaConstraints) {
                                    String firstParam = mtd.getNameOfTimeIntervalBeginningParameter();
                                    String secondParam = mtd.getNameOfTimeIntervalEndingParameter();
                                    VariableParameter firstVP = nameToVariableParameter.get(firstParam);
                                    if (firstVP == null) {
                                        throw new ConstraintApplyException("Could not find 'firstTimeDeltaParameterName' variable parameter!");
                                    }
                                    VariableParameter secondVP = nameToVariableParameter.get(secondParam);
                                    if (secondVP == null) {
                                        throw new ConstraintApplyException("Could not find 'secondTimeDeltaParameterName' variable parameter!");
                                    }
                                    if (!validateMaximumTimeDelta(
                                            inputParameters.get(firstParam),
                                            inputParameters.get(secondParam),
                                            firstVP.getType(),
                                            secondVP.getType(),
                                            mtd.getDeltaInMilliseconds(),
                                            mtd.isInclusive()
                                    )) {
                                        if (mtd.getOverrideMessage() != null && overrideMessage != null && overrideMessage.containsKey(mtd.getOverrideMessage())) {
                                            throw new ConstraintApplyException(StringSubstitutor.replace(overrideMessage.get(mtd.getOverrideMessage()), inputParameters));
                                        }
                                        if (mtd.getMessage() != null) {
                                            throw new ConstraintApplyException(StringSubstitutor.replace(mtd.getMessage(), inputParameters));
                                        }
                                        throw new ConstraintApplyException("Given params violates maximum time delta constraint!");
                                    }
                                }
                            }
                            List<TargetedMinimumTimeDelta> targetedMinimumTimeDeltas = option.getTargetedMinimumTimeDeltas();
                            if (targetedMinimumTimeDeltas != null) {
                                for (TargetedMinimumTimeDelta mtd: targetedMinimumTimeDeltas) {
                                    String firstParam = mtd.getNameOfTimeIntervalBeginningParameter();
                                    String secondParam = mtd.getNameOfTimeIntervalEndingParameter();
                                    VariableParameter firstVP = nameToVariableParameter.get(firstParam);
                                    if (firstVP == null) {
                                        throw new ConstraintApplyException("Could not find 'firstTimeDeltaParameterName' variable parameter!");
                                    }
                                    VariableParameter secondVP = nameToVariableParameter.get(secondParam);
                                    if (secondVP == null) {
                                        throw new ConstraintApplyException("Could not find 'secondTimeDeltaParameterName' variable parameter!");
                                    }
                                    if (!validateMinimumTimeDelta(
                                            inputParameters.get(firstParam),
                                            inputParameters.get(secondParam),
                                            firstVP.getType(),
                                            secondVP.getType(),
                                            mtd.getDeltaInMilliseconds(),
                                            mtd.isInclusive()
                                    )) {
                                        if (mtd.getOverrideMessage() != null && overrideMessage != null && overrideMessage.containsKey(mtd.getOverrideMessage())) {
                                            throw new ConstraintApplyException(StringSubstitutor.replace(overrideMessage.get(mtd.getOverrideMessage()), inputParameters));
                                        }
                                        if (mtd.getMessage() != null) {
                                            throw new ConstraintApplyException(StringSubstitutor.replace(mtd.getMessage(), inputParameters));
                                        }
                                        throw new ConstraintApplyException("Given params violates minimum time delta constraint!");
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            } else {
                validateMinimum(vp, passedValue, objectMapper);
                validateMaximum(vp, passedValue, objectMapper);
                List<MaximumTimeDelta> maximumTimeDeltaConstraints = vp.getMaximumTimeDeltas();
                if (maximumTimeDeltaConstraints != null) {
                    for (MaximumTimeDelta mtd : maximumTimeDeltaConstraints) {
                        String beginningParameter = mtd.getNameOfTimeIntervalPairParameter();
                        VariableParameter variableParameter = nameToVariableParameter.get(beginningParameter);
                        if (variableParameter == null) {
                            throw new ConstraintApplyException("Could not find 'with' variable parameter!");
                        }
                        Type pairedType = variableParameter.getType();
                        Object pair = inputParameters.get(beginningParameter);
                        if (!validateMaximumTimeDelta(passedValue, pair, type, pairedType, mtd)) {
                            if (mtd.getOverrideMessage() != null && overrideMessage != null && overrideMessage.containsKey(mtd.getOverrideMessage())) {
                                throw new ConstraintApplyException(StringSubstitutor.replace(overrideMessage.get(mtd.getOverrideMessage()), inputParameters));
                            }
                            if (mtd.getMessage() != null) {
                                throw new ConstraintApplyException(StringSubstitutor.replace(mtd.getMessage(), inputParameters));
                            }
                            throw new ConstraintApplyException("Given params violates maximum time delta constraint!");
                        }
                    }
                }
                List<MinimumTimeDelta> minimumTimeDeltas = vp.getMinimumTimeDeltas();
                if (minimumTimeDeltas != null) {
                    for (MinimumTimeDelta mtd: minimumTimeDeltas) {
                        String pairedParam = mtd.getNameOfTimeIntervalPairParameter();
                        VariableParameter variableParameter = nameToVariableParameter.get(pairedParam);
                        if (variableParameter == null) {
                            throw new ConstraintApplyException("Could not find 'with' variable parameter!");
                        }
                        Type pairedType = variableParameter.getType();
                        Object pair = inputParameters.get(pairedParam);
                        if (!validateMinimumTimeDelta(passedValue, pair, type, pairedType, mtd)) {
                            if (mtd.getOverrideMessage() != null && overrideMessage != null && overrideMessage.containsKey(mtd.getOverrideMessage())) {
                                throw new ConstraintApplyException(StringSubstitutor.replace(overrideMessage.get(mtd.getOverrideMessage()), inputParameters));
                            }
                            if (mtd.getMessage() != null) {
                                throw new ConstraintApplyException(StringSubstitutor.replace(mtd.getMessage(), inputParameters));
                            }
                            throw new ConstraintApplyException("Given params violates minimum time delta constraint!");
                        }
                    }
                }
            }
        }
    }

    public static void validateMinimum(@NonNull VariableParameter vp, Object passedValue, ObjectMapper objectMapper) throws ConstraintApplyException, JsonProcessingException {
        Minimum minimumConstraint = vp.getMinimum();
        if (minimumConstraint != null) {
            validateMinimum(vp, passedValue, objectMapper, minimumConstraint.getMinimum(), minimumConstraint.isInclusive());
        }
    }

    public static void validateMinimum(@NonNull VariableParameter vp, Object passedValue, ObjectMapper objectMapper, String minimum, boolean inclusive) throws ConstraintApplyException, JsonProcessingException {
        Type type = vp.getType();
        type.validateMinimum(minimum, passedValue, objectMapper, inclusive);
    }

    public static void validateMaximum(@NonNull VariableParameter vp, Object passedValue, ObjectMapper objectMapper) throws ConstraintApplyException, JsonProcessingException {
        Maximum maximumConstraints = vp.getMaximum();
        if (maximumConstraints != null) {
            validateMaximum(vp, passedValue, objectMapper, maximumConstraints.getMaximum(), maximumConstraints.isInclusive());
        }
    }

    public static void validateMaximum(@NonNull VariableParameter vp, Object passedValue, ObjectMapper objectMapper, String maximum, boolean inclusive) throws ConstraintApplyException, JsonProcessingException {
        Type type = vp.getType();
        type.validateMaximum(maximum, passedValue, objectMapper, inclusive);
    }

    public static boolean validateMaximumTimeDelta(
            Object firstPairedParam,
            Object secondPairedParam,
            @NonNull Type firstPairedParamType,
            @NonNull Type secondPairedParamType,
            MaximumTimeDelta maximumTimeDelta
    ) throws ConstraintApplyException {
        long deltaInMilliseconds = maximumTimeDelta.getDeltaInMilliseconds();
        boolean inclusive = maximumTimeDelta.isInclusive();
        return validateMaximumTimeDelta(firstPairedParam, secondPairedParam, firstPairedParamType, secondPairedParamType, deltaInMilliseconds, inclusive);
    }

    private static boolean validateMaximumTimeDelta(Object firstPairedParam, Object secondPairedParam, Type firstPairedParamType, Type secondPairedParamType, long deltaInMilliseconds, boolean inclusive) throws ConstraintApplyException {
        Long fromMillis = TypeUtils.determineMillis(firstPairedParam, firstPairedParamType);
        Long tillMillis = TypeUtils.determineMillis(secondPairedParam, secondPairedParamType);
        if (inclusive) {
            return (tillMillis - fromMillis <= deltaInMilliseconds);
        }
        return (tillMillis - fromMillis < deltaInMilliseconds);
    }

    public static boolean validateMinimumTimeDelta(
            Object firstPairedParam,
            Object secondPairedParam,
            @NonNull Type firstPairedParamType,
            @NonNull Type secondPairedParamType,
            MinimumTimeDelta minimumTimeDelta
    ) throws ConstraintApplyException {
        long deltaInMilliseconds = minimumTimeDelta.getDeltaInMilliseconds();
        boolean inclusive = minimumTimeDelta.isInclusive();
        return validateMinimumTimeDelta(firstPairedParam, secondPairedParam, firstPairedParamType, secondPairedParamType, deltaInMilliseconds, inclusive);
    }

    private static boolean validateMinimumTimeDelta(Object firstPairedParam, Object secondPairedParam, Type firstPairedParamType, Type secondPairedParamType, long deltaInMilliseconds, boolean inclusive) throws ConstraintApplyException {
        Long fromMillis = TypeUtils.determineMillis(firstPairedParam, firstPairedParamType);
        Long tillMillis = TypeUtils.determineMillis(secondPairedParam, secondPairedParamType);
        if (inclusive) {
            return (tillMillis - fromMillis >= deltaInMilliseconds);
        }
        return (tillMillis - fromMillis > deltaInMilliseconds);
    }
}
