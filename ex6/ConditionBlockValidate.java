package oop.ex6.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class is static and validates condition blocks - like if blocks and while blocks.
 */
public class ConditionBlockValidate {
    private static final String INVALID_CONDITION_ERROR ="condition is invalid." ;
    private static final String CONDITION_TYPE_NO_BOOLEAN_ERROR = "the type of the condition is not boolean.";
    private static final String RGX_BOOLEAN_CONDITION = "\\s*int\\s*||\\s*double\\s*||\\s*boolean\\s*";
    private static final String RGX_SPLIT_BY_OR = "\\|\\|";

    /**
     * central function that checks if the block is valid
     * @param conditionBlockList list of lines of the block
     * @param scope the scope of the block
     * @param globalScope global scope
     * @throws Exception - error if invalid
     */
    static public void isConditionBlockValid(List<String> conditionBlockList, Scope scope,
                                             Scope globalScope)  throws Exception {
        checkCondition(conditionBlockList.get(0), scope, globalScope);

    }

    /**
     * check if all conditions in the block are valid
     * @param firstLine the line of the condition
     * @param scope the scope of the block
     * @param globalScope global scope
     * @throws SJavaError -error if invalid
     */
    static private void checkCondition(String firstLine, Scope scope, Scope globalScope) throws SJavaError {
        String conditions = firstLine.substring(firstLine.indexOf(MethodValidate.CHAR_OPEN_BRACKET)+1,
                                                firstLine.indexOf(MethodValidate.CHAR_CLOSE_BRACKET));
        String[] conditionsListByAnd = conditions.split("&&");
        List<String> allConditions = new ArrayList<>();
        for (String relevantConditions : conditionsListByAnd) {
            allConditions.addAll(Arrays.asList(relevantConditions.split(RGX_SPLIT_BY_OR)));
        }
        if (allConditions.contains(TextProcessor.SPACE)) {
            throw new SJavaError(INVALID_CONDITION_ERROR);
        }
        for (String condition : allConditions) {
            if (VariableValidate.checkBooleanValue(condition)){
                continue;
                // move to next condition.
            }
            if (condition.matches(VariableValidate.RGX_VALID_NAME)) {
                Scope s = scope;
                while (s.getOuterScope() != null) {
                    checkConditionHelper(condition, s);
                    s = s.getOuterScope();
                }
                if (globalScope!=null){
                    checkConditionHelper(condition, globalScope);
                }
            }
            else throw new SJavaError(INVALID_CONDITION_ERROR);
        }
    }

    /**
     * checks if condition is of right type (boolean, int, double)
     * @param condition specific condition in the condition line
     * @param scope scope of the block
     * @throws SJavaError - error of type 1 with informative message
     */
    static private void checkConditionHelper(String condition, Scope scope) throws SJavaError {
        condition = condition.replace(TextProcessor.SPACE, TextProcessor.EMPTY_STR);
        for (Variable var : scope.getVariablesList()) {
            if (var.getName().equals(condition)) {
                Matcher type =
                        Pattern.compile(RGX_BOOLEAN_CONDITION).matcher(var.getType());
                if (!type.matches() || !var.getIsInitialize()) {
                    throw new SJavaError(CONDITION_TYPE_NO_BOOLEAN_ERROR);
                }
            }
        }
    }
}
