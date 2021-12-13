package filesprocessing.filters;

import java.io.File;
import java.util.ArrayList;

public class Str {

	/** array of the filter arguments*/
	private final java.io.File[] filesArray;

	/** array to return*/
	private final ArrayList<File> returnArray;

	/** string to compare with */
	private final String str;

	/** is NOT filter  */
	private final boolean isNOT;

	/**
	 * constructor
	 * @param str string to compare with
	 * @param array array of files
	 * @param condition is NOT filter
	 */
	public Str(String str, File[] array, boolean condition) {
		this.filesArray = array;
		this.str = str;
		this.returnArray = new ArrayList<File>();
		this.isNOT = condition;
		filter();
	}

	/**
	 * the filter method.
	 */
	public void filter() {
		for (File file : filesArray) {
			if (getCondition()) {
				returnArray.add(file);
			}
		}
	}

	/**
	 * @return the string to compare with
	 */
	protected String getStr() {
		return str;
	}

	/**
	 * @return is NOT filter
	 */
	protected boolean getCondition() {
		return isNOT;
	}

	/**
	 * @return array files
	 */
	protected java.io.File[] getFilesArray() {
		return filesArray;
	}

	/**
	 * @return array to return
	 */
	public ArrayList<File> getReturn() {
		return returnArray;
	}
}


