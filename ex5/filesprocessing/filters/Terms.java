package filesprocessing.filters;

import java.io.File;
import java.util.ArrayList;

public class Terms {

	/** array of the filter arguments*/
	private final java.io.File[] filesArray;

	/** array to return*/
	private final ArrayList<File> returnArray;

	/** the filter condition  */
	private final boolean bol;

	/** is NOT filter  */
	private final boolean isNOT;

	/***
	 * constructor
	 * @param term the filter condition
	 * @param array array of files
	 * @param condition is NOT filter
	 */
	public Terms(boolean term, File[] array, boolean condition) {
		filesArray = array;
		bol = term;
		returnArray = new ArrayList<File>();
		isNOT = condition;
		filter();
	}

	/**
	 * the filter method.
	 */
	public void filter() {
		for (File file : filesArray) {
			if (isNOT) {
				returnArray.add(file);
			}
		}
	}

	/**
	 * @return the filter condition
	 */
	protected boolean getBol() {
		return bol;
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
