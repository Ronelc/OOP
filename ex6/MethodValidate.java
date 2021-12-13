package oop.ex6.main;

import java.util.*;
import java.util.regex.*;

public class MethodValidate {

	/**
	 * errors messages
	 */
	public static final String NAME_ERROR = "method name is illegal";
	public static final String DUPLICATE_NAME_ERROR ="other method is already with the method name.";
	public static final String LINE_ERROR ="method line is invalid";
	public static final String BRACKETS_ERROR ="brackets are invalid";
	public static final String RETURN_ERROR ="no legal return value.";

	/**
	 * regex's for different uses
	 */
	public static final String OPEN_BRACKET = "(";
	public static final String CLOSE_BRACKET = ")";
	public static final char CHAR_OPEN_BRACKET = '(';
	public static final char CHAR_CLOSE_BRACKET = ')';
	public static final String RGX_COMMA = ",";
	public static final String RGX_COMMA_DOT =";" ;
	public static final String RGX_VOID = "void\\s";
	public static final String RGX_BRACKET = "\\(";
	public static final String RGX_RETURN = "\\s*return\\s*;\\s*";
	public static final String RGX_VALID_NAME = "\\s*[a-zA-Z]+[a-zA-Z_0-9]*\\s*";

	public static final String startOfMethod = "\\s*void\\s*";


	/**
	 * @param method method to check.
	 * @param mapMethods map of all methods in file.
	 * @param scope the method scope.
	 * @param globalScope the global scope.
	 * @throws Exception - - illegal statements.
	 */
	public static void isMethodValid(List<String> method, HashMap<String, Method> mapMethods, Scope scope,
									 Scope globalScope) throws Exception {

		legalName(method.get(0));
		legalMethodParams(method.get(0), scope, globalScope);
		if (method.size() > 1) {
			legalReturn(method.get(method.size() - 2));
		}
		isMethodNameUnique(method.get(0), mapMethods);
	}

	/**
	 * @param method method to check.
	 * @param scope the method scope.
	 * @param globalScope the global scope.
	 * @throws Exception - illegal statements
	 */
	private static void legalMethodParams(String method, Scope scope, Scope globalScope) throws Exception {
		if (method.contains(OPEN_BRACKET) && method.contains(CLOSE_BRACKET)) {
			int indexStartParams = method.indexOf(OPEN_BRACKET);
			int indexEndParams = method.indexOf(CLOSE_BRACKET);
			String[] listParams = method.substring(indexStartParams + 1, indexEndParams).split(RGX_COMMA);
			if (listParams.length == 0 ||
				(listParams.length == 1 && listParams[0].equals(TextProcessor.EMPTY_STR))) {
				return;
			}
			String methodSub = method.substring(indexStartParams + 1, indexEndParams);
			String[] varsArray = methodSub.split(RGX_COMMA);
			for (String var : varsArray) {
				VariableValidate.checkVariableLine(var + RGX_COMMA_DOT, true, false,
												   scope, globalScope);

			}
		} else {
			throw new SJavaError(LINE_ERROR);
		}

	}


	/**
	 * @param line line to check.
	 * @throws Exception - illegal statements.
	 */
	private static void legalReturn(String line) throws Exception {
		Matcher matcher = Pattern.compile(RGX_RETURN).matcher(line);
		if (!matcher.matches()) {
			throw new SJavaError(RETURN_ERROR);
		}
	}

	/**
	 * @param methodDeclaration method line declaration
	 * @throws Exception - illegal statements.
	 */
	private static void legalName(String methodDeclaration) throws Exception {
		String[] beforeBracket = methodDeclaration.split(RGX_BRACKET);
		String[] beforeBracketWithoutSpace = beforeBracket[0].split(RGX_VOID);
		if (beforeBracketWithoutSpace.length > 1) {
			String methodName = beforeBracketWithoutSpace[beforeBracketWithoutSpace.length - 1];
			Matcher matchName = Pattern.compile(RGX_VALID_NAME).matcher(methodName);
			if (!matchName.matches()) {
				throw new SJavaError(NAME_ERROR);
			}
		} else {
			throw new SJavaError(NAME_ERROR);
		}

	}

	/**
	 * @param line line to check.
	 * @param mapMethods map of all methods in file.
	 * @throws Exception - illegal statements
	 */
	private static void isMethodNameUnique(String line, HashMap<String, Method> mapMethods) throws Exception {
		String methodName = line.split(TextProcessor.SPACE, 2)[1];
		if (mapMethods.containsKey(methodName)) {
			throw new SJavaError(DUPLICATE_NAME_ERROR);
		}


	}
}
