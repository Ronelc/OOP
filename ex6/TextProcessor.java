package oop.ex6.main;

import java.io.File;
import java.util.ArrayList;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class to read the file and analyze him.
 */
public class TextProcessor {

	Deque<Character> bracketsStack = new ArrayDeque<>();

	/**
	 *
	 */
	public static final String emptyLine = "\\s*";

	public static final String COMMENT_ERROR = "comment is invalid.";
	public static final String RETURN_ERROR = "more than one return in the same scope.";
	public static final String ILLEGAL_ACTION_ERROR = "this action is illegal inside a method.";
	public static final String MATCH_PARAMS_ERROR = "params in method call don't match.";
	public static final String INVALID_PARAMS_ERROR = "method call params are invalid.";

	/***/
	static HashSet<String> blockNames = new HashSet<>(Arrays.asList("void", "if", "while"));

	/***/
	HashMap<List<String>, Scope> methodBulks = new HashMap<>();

	/**
	 *
	 */
	public GlobalScope globalScope = new GlobalScope();

	/***/
	HashSet<String> maybeInitializeVars = new HashSet<>();

	Pattern patternEmptyLine = Pattern.compile("\\s*\\(*\\{*\\)*}*");

	public static final String EMPTY_STR = "";

	public static final String SPACE = " ";


	/**
	 * @param filePath path name of the file
	 * @throws Exception - illegal statements
	 */
	public void processTextFile(String filePath) throws Exception {
		File file = new File(filePath);
		analyzeText(file);
	}

	/**
	 * analyzes the text
	 * @param file file to analyze
	 * @throws Exception if something is invalid or IO exception
	 */
	public void analyzeText(File file) throws Exception{
		Scanner scanner = new Scanner(file);
		Scope currScope = globalScope;
		while (scanner.hasNextLine()) {
			String currLine = scanner.nextLine();
			defineGlobalVarsAndMethods( currScope, currLine, scanner);

		}
		checkMaybeInitializeVars();
		analyzeMethods();

	}

	/**
	 * defines global vars and methods for the class
	 * @param currScope current scope
	 * @param currLine current line to check
	 * @param scanner scanner
	 * @throws Exception if something is invalid
	 */
	private void defineGlobalVarsAndMethods(Scope currScope, String currLine,Scanner scanner) throws Exception {
		String startOfLine = currLine.split(SPACE)[0];
		Matcher matchLine = Pattern.compile(MethodValidate.startOfMethod).matcher(startOfLine);
		if (VariableValidate.S_JAVA_TYPES.contains(startOfLine) || startOfLine.equals(VariableValidate.FINAL)) {
			VariableValidate.checkVariableLine(currLine, false, true, globalScope, null);
		} else if (matchLine.matches()) {
			Scope newScope = new Scope(currScope); //of type method
			int v = currLine.indexOf("v");
			String newline = currLine.substring(v);
			List<String> methodBody = getMethodBody(newline, scanner);
			MethodValidate.isMethodValid(methodBody, globalScope.getMapMethods(), newScope, globalScope);
			Method currMethod = new Method(newScope, newline);
			globalScope.addMethodToMap(currMethod);
			methodBulks.put(methodBody, newScope);
		}
		//comment check
		else if (startOfLine.contains("//")) {
			if (currLine.indexOf('/') > 0) {
				throw new SJavaError(COMMENT_ERROR);
			}
		} else if (!currLine.matches(emptyLine)) {
			maybeInitializeVars.add(currLine);
		}
	}

	/**
	 * @throws Exception - illegal statements
	 */
	private void checkMaybeInitializeVars() throws Exception {
		for (String line : maybeInitializeVars) {
			VariableValidate.checkVariableLine(line, false, true, globalScope, null);
		}
	}

	/**
	 * @throws Exception - illegal statements
	 */
	private void analyzeMethods() throws Exception {
		Scope currScope;
		for (Map.Entry<List<String>, Scope> keyValue : methodBulks.entrySet()) {
			List<String> linesList = keyValue.getKey();
			currScope = methodBulks.get(linesList);
			List<String> subLinesList = linesList.subList(1, linesList.size() - 1);
			analyzeBlock(currScope, subLinesList);
		}
	}

	/**
	 * @param currScope the method scope.
	 * @param methodBulk method to check.
	 * @throws Exception - illegal statements
	 */
	private void analyzeBlock(Scope currScope, List<String> methodBulk) throws Exception {
		boolean isReturned = false;
		for (int lineIndex = 0; lineIndex < methodBulk.size(); lineIndex++) {
			// Skip empty lines
			Matcher matcherEmptyLine = patternEmptyLine.matcher(methodBulk.get(lineIndex));
			if (matcherEmptyLine.matches()) {
				continue;
			}
			String currLine = methodBulk.get(lineIndex);
			String[] startsOfLine = currLine.split("\\s");
			int index = 0;
			while (index < startsOfLine.length && startsOfLine[index].isEmpty()) {
				index++;
			}
			String startOfLine = startsOfLine[index];
			String startOfLineForBlocks = startOfLine.split("\\(")[0];
			if (startOfLineForBlocks.equals("if") || startOfLineForBlocks.equals("while")) {
				lineIndex = analyzeConditionBlock(lineIndex, methodBulk, currScope);

			} else if (globalScope.getMapMethods().containsKey(startOfLineForBlocks)) {
				List<String> currLineParams = getMethodCallParams(currLine);
				checkParams(currLineParams, startOfLineForBlocks, currScope);
			} else {
				Matcher matcherReturnLine = Pattern.compile((MethodValidate.RGX_RETURN)).
						matcher(currLine);
				if (matcherReturnLine.matches()) {
					if (isReturned) {
						throw new SJavaError(RETURN_ERROR);
					} else {
						isReturned = true;
					}
				} else {
					if (((VariableValidate.S_JAVA_TYPES.contains(startOfLine)||
							VariableValidate.FINAL.equals(startOfLine)) ||
						 startOfLine.matches(VariableValidate.RGX_VALID_NAME)) &&
						!blockNames.contains(startOfLine)) {
						VariableValidate.checkVariableLine(currLine,
														   false, false, currScope, globalScope);
					} else {
						throw new SJavaError(ILLEGAL_ACTION_ERROR);
					}
				}
			}
		}
	}

	private int analyzeConditionBlock(int lineIndex, List<String> methodBulk, Scope currScope)
			throws Exception {
		List<String> conditionBody = getConditionBody(methodBulk, lineIndex);
		lineIndex += conditionBody.size() - 1;
		Scope newScope = new Scope(currScope); //of type ifWhile
		ConditionBlockValidate.isConditionBlockValid(conditionBody, newScope, globalScope);
		List<String> contentBody = conditionBody.subList(1, conditionBody.size() - 1);
		analyzeBlock(newScope, contentBody);
		return lineIndex;
	}

	/**
	 * @param line line of method parameters that is called
	 * @return list of method params
	 */
	private static List<String> getMethodCallParams(String line) {
		int indexStartParams = line.indexOf(MethodValidate.CHAR_OPEN_BRACKET);
		int indexEndParams = line.indexOf(MethodValidate.CHAR_CLOSE_BRACKET);
		if (indexStartParams + 1 == indexEndParams) {
			return new ArrayList<>();
		}
		String[] listParams = line.substring(indexStartParams + 1, indexEndParams).split(",");
		return Arrays.asList(listParams);
	}

	/**
	 * checks if the params of calling method are accurate according to params def.
	 * @param currLineParams params of current line we are checking
	 */
	private void checkParams(List<String> currLineParams, String methodName, Scope scope) throws Exception {
		List<String> originalMethodParams = globalScope.getMapMethods().get(methodName).getMethodParams();
		if (currLineParams.size() != originalMethodParams.size()) {
			throw new SJavaError(MATCH_PARAMS_ERROR);
		}
		for (int i = 0; i < currLineParams.size(); i++) {
			String varType = checkIfVar(scope, currLineParams.get(i));
			if (varType.isEmpty()) {
				if (!(VariableValidate.checkValue(originalMethodParams.get(i), currLineParams.get(i))) ||
					originalMethodParams.size() != currLineParams.size()) {
					throw new SJavaError(INVALID_PARAMS_ERROR);
				}
			} else if (!varType.equals(originalMethodParams.get(i))) {
				throw new SJavaError(INVALID_PARAMS_ERROR);
			}


		}
	}

	/**
	 * @param scope the current scope of the variable
	 * @param nameToCheck the name we check if is a variable
	 * @return variable type
	 */
	private String checkIfVar(Scope scope, String nameToCheck) {
		Matcher name1 = Pattern.compile(VariableValidate.RGX_VALID_NAME).matcher(nameToCheck);
		if (name1.matches()) {
			nameToCheck = nameToCheck.replace(SPACE, EMPTY_STR);
			Scope currentScope;
			currentScope = scope;
			while (currentScope != null) {
				String possibleVar = checkIfVarInCurrScope(currentScope, nameToCheck);
				if (!possibleVar.isEmpty()) {
					return possibleVar;
				}
				currentScope = currentScope.getOuterScope();
			}

			return checkIfVarInCurrScope(globalScope, nameToCheck);

		}
		return EMPTY_STR;
	}

	private String checkIfVarInCurrScope(Scope scope, String nameToCheck) {
		for (Variable var : scope.getVariablesList()) {
			if (nameToCheck.equals(var.getName())) {
				return var.getType();
			}
		}
		return EMPTY_STR;
	}


	/**
	 * @param methodBulk method bulk the condition is in
	 * @param lineIndex index of where the condition starts
	 * @return condition body by list of lines
	 * @throws SJavaError - illegal statements
	 */
	private List<String> getConditionBody(List<String> methodBulk, int lineIndex) throws SJavaError {
		List<String> conditionLines = new ArrayList<>();
		conditionLines.add(methodBulk.get(lineIndex));
		searchBracketsInLine(methodBulk.get(lineIndex));
		int counter = lineIndex;
		while (counter < methodBulk.size() && !bracketsStack.isEmpty()) {
			counter++;
			searchBracketsInLine(methodBulk.get(counter));
			conditionLines.add(methodBulk.get(counter));
		}
		if (!bracketsStack.isEmpty()) {
			throw new SJavaError(MethodValidate.BRACKETS_ERROR);
		}
		return conditionLines;
	}

	/**
	 * @param currLine the line we need to check.
	 * @param scanner text reader.
	 * @return list of lines the method has
	 * @throws Exception - illegal statements
	 */
	private List<String> getMethodBody(String currLine, Scanner scanner) throws Exception {
		List<String> methodLines = new ArrayList<>();
		methodLines.add(currLine);
		searchBracketsInLine(currLine);
		while (scanner.hasNextLine() && !bracketsStack.isEmpty()) {
			String line = scanner.nextLine();
			searchBracketsInLine(line);
			methodLines.add(line);
		}
		if (!bracketsStack.isEmpty()) {
			throw new SJavaError(MethodValidate.BRACKETS_ERROR);
		}
		return methodLines;
	}

	/**
	 * looks for brackets in line
	 * @param line the certain line to search in
	 */
	private void searchBracketsInLine(String line) {
		for (char currChar : line.toCharArray()) {
			if (currChar == '{') {
				bracketsStack.add(currChar);
			}
			if (currChar == '}') {
				bracketsStack.pop();
			}
		}
	}

}

