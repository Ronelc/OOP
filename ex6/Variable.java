package oop.ex6.main;

/**
 * a class to create an variable object.
 */
public class Variable {

	/** a variable type */
	private final String type;

	/** a variable name */
	private final String name;

	/** a variable value */
	private String val;

	/** boolean condition for final variable */
	private final boolean isFinal;

	/** boolean condition for Initialize variable. */
	private boolean isInitialize;

	/** boolean condition for global variable */
	private boolean isGlobal;

	/** the variable scope */
	private Scope scope;

	/**
	 *
	 * @param type a variable type
	 * @param name a variable name
	 * @param val a variable value
	 * @param isFinal boolean condition for final variable
	 * @param isInitialize  boolean condition for Initialize variable.
	 * @param isGlobal boolean condition for global variable
	 * @param scope the variable scope
	 */
	public Variable(String type, String name, String val, boolean isFinal, boolean isInitialize,
					boolean isGlobal, Scope scope) {
		this.type = type;
		this.name = name;
		this.val = val;
		this.isFinal = isFinal;
		this.isInitialize = isInitialize;
		this.isGlobal = isGlobal;
		this.scope = scope;
	}


	/**
	 * @return variable name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return variable type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return true if variable is final.
	 */
	public boolean getIsFinal() {
		return isFinal;
	}

	/**
	 * @return true if variable is Initialize.
	 */
	public boolean getIsInitialize() {
		return isInitialize;
	}

	/**
	 * @return variable value
	 */
	public String getVal(){
		return val;
	}

	/**
	 * change variable value
	 */
	public void setVal(String value) {
		val = value;
	}

	/**
	 * @return true if variable is global.
	 */
	public boolean getIsGlobal() {
		return isGlobal;
	}
}