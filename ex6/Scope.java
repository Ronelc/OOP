package oop.ex6.main;

import java.util.ArrayList;
import java.util.List;

/**
 * an object of scope. the scope represents the scope of the global world,
 * method or if/while blocks.
 * each one has a different scope.
 */
public class Scope {
	private final Scope outerScope;
	private final ArrayList<Variable> variablesList;

	/**
	 *
	 * @param outerScope outer scope of the created scope
	 */
	Scope(Scope outerScope) {
		this.outerScope = outerScope;
		this.variablesList = new ArrayList<>();
	}

	/**
	 * default constructor
	 */
	Scope() {
		this.outerScope = null;
		this.variablesList = new ArrayList<>();
	}

	/**
	 *
	 * @return outer scope of this scope
	 */
	public Scope getOuterScope() {
		return this.outerScope;
	}

	/**
	 *
	 * @return variables list
	 */
	public List<Variable> getVariablesList() {
		return this.variablesList;
	}

	/**
	 *
	 * @param val value to add to variable list
	 */
	public void addToVariableList(Variable val) {
		this.variablesList.add(val);
	}
}