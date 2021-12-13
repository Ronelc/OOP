package oop.ex6.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * a class to check if variable line us valid or not.
 */

public class VariableValidate {

    /**
     * errors massages
     */
    private static final String END_ERROR = "The line doesn't end with the char - ;";
    private static final String FINAL_ERROR = "final variable did not initialized";
    private static final String CHANGE_FINAL_ERROR = "the variable is final";
    private static final String VALUE_ERROR = "value is invalid";
    private static final String UPDATE_ERROR = "update only one variable at time";
    private static final String TYPE_ERROR = "type is invalid";
    private static final String NAME_ERROR = "name is invalid";
    private static final String TAKEN_NAME_ERROR = "name is already taken";
    private static final String LINE_ERROR = "variable line is invalid.";
    private static final String ERROR_TYPE_INVALID ="No sJava type found after final" ;


    /**
     * sJavac words
     */
    private static final String BOOLEAN = "boolean";
    private static final String INT = "int";
    private static final String DOUBLE = "double";
    private static final String STR = "String";
    private static final String CHAR = "char";
    public static final String FINAL = "char";


    /**
     * array of sJavac words
     */
    public static final ArrayList<String> S_JAVA_TYPES = new ArrayList<>(Arrays.asList(BOOLEAN, INT, DOUBLE,
            CHAR, STR));

    /**
     * regex's to use in order to validate
     */

    private static final String RGX_COMMA = MethodValidate.RGX_COMMA ;
    private static final String RGX_COMMA_DOT = MethodValidate.RGX_COMMA_DOT;
    private static final String RGX_EQUALS ="=" ;
    private static final String RGX_INITIALIZE_NAME ="\\s*([a-zA-z]+[a-zA-z0-9_]*\\s*)|(\\s*_[a-zA-z0-9_])+\\s*" ;
    private static final String RGX_INT_VALUE = "\\s*-*[0-9]+\\s*";
    private static final String RGX_DOUBLE_VALUE = "\\s*-*[0-9]+\\.?[0-9]*\\s*";
    private static final String RGX_FALSE_BOOL_VALUE = "\\s*false\\s*";
    private static final String RGX_TRUE_BOOL_VALUE = "\\s*true\\s*";
    private static final String RGX_CHAR_VALUE = "\\s*'.'\\s*";
    private static final String RGX_STR_VALUE = "\\s*[\"].*[\"]\\s*";
    private static final String RGX_DELETE_SPACES = "[a-zA-Z0-9][\\s=a-zA-Z-0-9'\"\'\",]+.*;";
    public static final String RGX_VALID_NAME = "\\s*[a-zA-Z]+[0-9a-zA-Z_]*\\s*|\\s*_[a-zA-z0-9_]+\\s*";
    private static final String RGX_S_JAVA_TYPES = "\\s*String\\s*|\\s*char\\s*|\\s*int\\s*|\\s*double\\s*|\\s" +
            "*boolean\\s*";
    private static final String RGX_FINAL = "\\s*final\\s*";

    /**
     * array of booleans types
     */
    public static ArrayList<String> booleans = new ArrayList<>(
            Arrays.asList(BOOLEAN, INT, DOUBLE));


    /**
     * function that check the end of the variable line and returns the different parts of the line.
     *
     * @return a list of strings to check.
     * @throws SJavaError - illegal statements.
     */
    public static String[] getPartsOfLine(String lineToCheck) throws SJavaError {
        String newLine = TextProcessor.EMPTY_STR;
        Matcher matcher = Pattern.compile(RGX_DELETE_SPACES).matcher(lineToCheck);
        while (matcher.find()) {
            newLine += lineToCheck.substring(matcher.start(), matcher.end());
        }
        if (!newLine.endsWith(";")) {
            throw new SJavaError(END_ERROR);
        } else {
            newLine = newLine.replace(RGX_COMMA_DOT, TextProcessor.EMPTY_STR);
            newLine = newLine.replaceAll("\\s+|;", TextProcessor.SPACE);
            return newLine.split(TextProcessor.SPACE);
        }
    }

    /**
     * convert list to one string separated by spaces
     *
     * @param listToCheck a list of strings to check.
     * @param index       index to start from.
     * @return one String who start from the index;
     */
    public static String convertListToStr(String[] listToCheck, int index) {
        StringBuilder strCheck = new StringBuilder(listToCheck[index]);

        for (int i = index + 1; i < listToCheck.length; i++) {
            strCheck.append(TextProcessor.SPACE).append(listToCheck[i]);
        }

        return strCheck.toString();
    }

    /**
     * @param isGlobal        is in global scope.
     * @param scope           the variables scope.
     * @param globalScope     the global scope.
     * @param listToCheck     a list of strings to check.
     * @param startsWithType  is the first word starts with a sJavac type.
     * @param startsWithFinal is the first word starts with "final".
     * @return [strCheck, varType, isFinal]
     * @throws SJavaError - illegal statements.
     */
    public static ArrayList<Object> checkVariableLineHelper(boolean isInitialize, boolean isGlobal,
                                                            Scope scope, Scope globalScope,
                                                            String[] listToCheck,
                                                            boolean startsWithType, boolean startsWithFinal)
                                                            throws SJavaError {
        if (listToCheck.length < 2) {
            throw new SJavaError(LINE_ERROR);
        }
        ArrayList<Object> varArray = new ArrayList<>();
        String varType = TextProcessor.EMPTY_STR, remainingString;

        if (startsWithFinal) {
            if (Pattern.compile(RGX_S_JAVA_TYPES).matcher(listToCheck[1]).matches()) {
                varType = listToCheck[1];
                remainingString = convertListToStr(listToCheck, 2);
            } else {
                throw new SJavaError(ERROR_TYPE_INVALID);
            }
        } else if (startsWithType) {
            varType = listToCheck[0];
            remainingString = convertListToStr(listToCheck, 1);
        } else { // starts with name

            if (S_JAVA_TYPES.contains(listToCheck[1]))
                throw new SJavaError(VALUE_ERROR);

            remainingString = convertListToStr(listToCheck, 0);
            String[] remainingListToCheck = remainingString.split(RGX_COMMA);

            if (remainingListToCheck.length == 1) {
                if (remainingString.contains("=")) {
                    String[] nameAndVal = remainingString.split("=");
                    String name = nameAndVal[0].replace(TextProcessor.SPACE, TextProcessor.EMPTY_STR);
                    String val = nameAndVal[1].replace(TextProcessor.SPACE, TextProcessor.EMPTY_STR);
                    updateVar(varType, name, val, isInitialize, isGlobal, scope, globalScope);
                    return varArray;
                } else {
                    throw new SJavaError(VALUE_ERROR);
                }
            } else {
                throw new SJavaError(UPDATE_ERROR);
            }
        }

        varArray.add(remainingString);
        varArray.add(varType);
        varArray.add(startsWithFinal);
        return varArray;
    }


    /**
     * @param lineToCheck - a variables line to check. the central method of VariableValidate.
     * @param isGlobal    a boolean condition.
     * @param scope       the variables scope.
     * @param globalScope the global scope.
     * @throws SJavaError - illegal statements.
     */
    public static void checkVariableLine(String lineToCheck, boolean isInitialize, boolean isGlobal,
                                         Scope scope, Scope globalScope)
            throws Exception {

        String[] listToCheck = getPartsOfLine(lineToCheck);
        boolean startsWithName = Pattern.compile(RGX_VALID_NAME).matcher(listToCheck[0]).matches();
        boolean startsWithType = Pattern.compile(RGX_S_JAVA_TYPES).matcher(listToCheck[0]).matches();
        boolean startsWithFinal = Pattern.compile(RGX_FINAL).matcher(listToCheck[0]).matches();

        if (startsWithName || startsWithType || startsWithFinal) {
            ArrayList<Object> varArray = checkVariableLineHelper(isInitialize, isGlobal, scope, globalScope,
                    listToCheck, startsWithType, startsWithFinal);

            if (varArray.size() >= 3) {
                String strCheck = (String) varArray.get(0), varType = (String) varArray.get(1);
                boolean isFinal = (boolean) varArray.get(2);
                String[] listToCheck1 = strCheck.split(RGX_COMMA);
                for (String str : listToCheck1) {
                    isStrInLineValid(str,scope,globalScope,varType,isFinal,isGlobal,isInitialize);
                }
            }
        } else {
            throw new SJavaError(TYPE_ERROR);
        }
    }

    private static void isStrInLineValid(String str, Scope scope, Scope globalScope, String varType,boolean isFinal,
                                         boolean isGlobal, boolean isInitialize) throws Exception {
        if (str.contains("=")) {
            String[] listToCheck = str.split(RGX_EQUALS);
            if (listToCheck.length < 2) {
                throw new SJavaError(VALUE_ERROR);
            } else {
                if (checkName(listToCheck[0], scope) && checkValue(varType, listToCheck[1])) {
                    createAnObj(varType, listToCheck[0], listToCheck[1], isFinal, true,
                            isGlobal, scope);
                } else {
                    checkInitialization(varType, listToCheck[0], listToCheck[1], isInitialize,
                            isGlobal, scope, globalScope);
                }
            }
        } else {
            if (isFinal) {
                throw new SJavaError(FINAL_ERROR);
            }
            if (checkName(str, scope)) {
                createAnObj(varType, str, null, false, isInitialize, isGlobal, scope);
            } else {
                throw new SJavaError(NAME_ERROR);
            }
        }
    }


    /**
     * @param nameToCheck variable name.
     * @param scope       the variables scope.
     * @return true if name is valid.
     * @throws SJavaError - illegal statements.
     */
    public static boolean checkName(String nameToCheck, Scope scope) throws SJavaError {
        Matcher validNameMatcher = Pattern.compile(RGX_VALID_NAME).matcher(nameToCheck);
        if (validNameMatcher.matches()) {
            String name = nameToCheck.replace(TextProcessor.SPACE, TextProcessor.EMPTY_STR);
            for (Variable var : scope.getVariablesList()) {
                if (name.equals(var.getName())) {
                    if (!var.getIsGlobal()) {
                        throw new SJavaError(TAKEN_NAME_ERROR);
                    }
                }
            }
            return true;
        } else {
            throw new SJavaError(NAME_ERROR);
        }
    }


    /**
     * @param type        variable type.
     * @param nameToCheck variable name.
     * @param value       variable value.
     * @param isGlobal    boolean condition.
     * @param scope       the variables scope.
     * @param globalScope the global scope.
     * @throws SJavaError - illegal statements
     */
    public static void updateVar(String type, String nameToCheck, String value, boolean isInitialize,
                                 boolean isGlobal, Scope scope, Scope globalScope) throws SJavaError {
        Scope currentScope = scope;
        String name = nameToCheck.replace(TextProcessor.SPACE, TextProcessor.EMPTY_STR);
        for (Variable var : currentScope.getVariablesList()) {
            if (name.equals(var.getName())) {
                if (!var.getIsFinal()) {
                    var.setVal(value);
                    return;
                } else {
                    throw new SJavaError(CHANGE_FINAL_ERROR);
                }
            }
            if (currentScope.getOuterScope() != null) {
                currentScope = currentScope.getOuterScope();
            }
        }
        for (Variable globalVar : globalScope.getVariablesList()) {
            if (name.equals(globalVar.getName())) {
                if (globalVar.getIsFinal()) {
                    throw new SJavaError(CHANGE_FINAL_ERROR);
                } else {
                    if (checkValue(globalVar.getType(), value)) {
                        createAnObj(globalVar.getType(), name, value,
                                globalVar.getIsFinal(), true, true, scope);
                    } else {
                        checkInitialization(type, name, value, isInitialize,
                                isGlobal, scope, globalScope);
                    }
                    return;
                }
            }
        }
        throw new SJavaError(NAME_ERROR + " or " + VALUE_ERROR);
    }


    /**
     * @param type       variable type.
     * @param valToCheck variable value.
     * @return true if value is valid, false otherwise.
     */
    public static boolean checkValue(String type, String valToCheck) {
        switch (type) {
            case INT:
                return checkIntValue(valToCheck);
            case DOUBLE:
                return checkDoubleValue(valToCheck);
            case BOOLEAN:
                return checkBooleanValue(valToCheck);
            case CHAR:
                return checkCharValue(valToCheck);
            case STR:
                return checkStrValue(valToCheck);
        }
        return false;
    }

    /**
     * creates a variable object
     *
     * @param type         variable type.
     * @param name         variable name.
     * @param val          variable value.
     * @param isFinal      boolean condition for final variable.
     * @param isInitialize boolean condition for Initialize variable.
     * @param isGlobal     boolean condition for global variable.
     * @param scope        the variables scope.
     */
    public static void createAnObj(String type, String name, String val, boolean isFinal,
                                   boolean isInitialize,
                                   boolean isGlobal, Scope scope) {
        name = name.replace(TextProcessor.SPACE, TextProcessor.EMPTY_STR);
        Variable varObj = new Variable(type, name, val, isFinal, isInitialize, isGlobal, scope);
        scope.addToVariableList(varObj);
    }


    /**
     * @param type        - variable type.
     * @param varName     variable name.
     * @param valToCheck  - another variable that we need to check is value
     * @param isGlobal    boolean condition for global variable.
     * @param scope       - scope the scope of the variable.
     * @param globalScope the global scope.
     * @throws SJavaError - illegal statements
     */
    public static void checkInitialization(String type, String varName, String valToCheck,
                                           boolean isInitialize, Boolean isGlobal,
                                           Scope scope, Scope globalScope) throws SJavaError {
        Scope currentScope = scope;
        Matcher name = Pattern.compile(RGX_INITIALIZE_NAME)
                .matcher(valToCheck);
        if (name.matches()) {
            while (true) {
                if (currentScope.getVariablesList() != null) {
                    for (Variable var : currentScope.getVariablesList()) {
                        valToCheck = valToCheck.replace(TextProcessor.SPACE, TextProcessor.EMPTY_STR);
                        if (valToCheck.equals(var.getName())) {
                            checkInitializationHelper(type, varName, var, isInitialize, isGlobal, scope);
                            return;
                        }
                        if (currentScope.getOuterScope() != null) {
                            currentScope = currentScope.getOuterScope();
                        }
                        if (globalScope != null) {
                            currentScope = globalScope;
                        }
                    }
                }
                if (currentScope.getOuterScope() != null) {
                    currentScope = currentScope.getOuterScope();
                } else {
                    throw new SJavaError(NAME_ERROR + " or " + VALUE_ERROR);
                }
            }
        } else {
            throw new SJavaError(NAME_ERROR + " or " + VALUE_ERROR);
        }
    }

    /**
     * @param type     variable type.
     * @param varName  variable name.
     * @param var      a variable.
     * @param isGlobal boolean condition for global variable.
     * @param scope    - scope the scope of the variable.
     * @throws SJavaError - illegal statements
     */
    public static void checkInitializationHelper(String type, String varName, Variable var,
                                                 boolean isInitialize, boolean isGlobal,
                                                 Scope scope) throws SJavaError {
        if (type.equals(DOUBLE)) {
            if ((var.getType().equals(INT) || (var.getType().equals(DOUBLE))) && var.getIsInitialize()) {
                createAnObj(type, varName, var.getVal(), var.getIsFinal(),
                        var.getIsInitialize(), isGlobal, scope);
                return;
            }
        }
        if (type.equals(BOOLEAN)) {
            if (booleans.contains(var.getType()) && var.getIsInitialize()) {
                createAnObj(type, varName, var.getVal(), var.getIsFinal(), var.getIsInitialize(),
                        isGlobal, scope);
                return;
            }
        }
        if (type.equals(var.getType()) && (var.getIsInitialize()) || isInitialize) {
            createAnObj(type, varName, var.getVal(), var.getIsFinal(), var.getIsInitialize(),
                    isGlobal, scope);
            return;
        }
        throw new SJavaError(NAME_ERROR + " or " + VALUE_ERROR);
    }

    /**
     * @param valToCheck variable value to check.
     * @return true if value is valid, false otherwise.
     */
    private static boolean checkIntValue(String valToCheck) {
        Matcher isInt = Pattern.compile(RGX_INT_VALUE).matcher(valToCheck);
        return isInt.matches();
    }

    /**
     * @param valToCheck variable value to check.
     * @return true if value is valid, false otherwise.
     */
    private static boolean checkDoubleValue(String valToCheck) {
        Matcher isDouble = Pattern.compile(RGX_DOUBLE_VALUE).matcher(valToCheck);
        return isDouble.matches();
    }

    /**
     * @param valToCheck variable value to check.
     * @return true if value is valid, false otherwise.
     */
    public static boolean checkBooleanValue(String valToCheck) {
        Matcher isBoolean0 = Pattern.compile(RGX_DOUBLE_VALUE).matcher(valToCheck);
        Matcher isBoolean1 = Pattern.compile(RGX_FALSE_BOOL_VALUE).matcher(valToCheck);
        Matcher isBoolean2 = Pattern.compile(RGX_TRUE_BOOL_VALUE).matcher(valToCheck);

        return isBoolean0.matches() || isBoolean1.matches() || isBoolean2.matches();
    }

    /**
     * @param valToCheck variable value to check.
     * @return true if value is valid, false otherwise.
     */
    private static boolean checkCharValue(String valToCheck) {
        Matcher isChar = Pattern.compile(RGX_CHAR_VALUE).matcher(valToCheck);
        return isChar.matches();
    }

    /**
     * @param valToCheck variable value to check.
     * @return true if value is valid, false otherwise.
     */
    private static boolean checkStrValue(String valToCheck) {
        Matcher isStr = Pattern.compile(RGX_STR_VALUE).matcher(valToCheck);
        return isStr.matches();
    }
}