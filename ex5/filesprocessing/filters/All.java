package filesprocessing.filters;

import java.io.File;
import java.util.ArrayList;

public class All {

	/** array of the filter arguments*/
	private final java.io.File[] filesArray;

	/** array to return*/
	private final ArrayList<File> returnArray;

	/** is NOT filter  */
	private final boolean isNOT;

	/**
	 *
	 * @param array array of files
	 * @param condition is NOT filter
	 */
	public All(File[] array, boolean condition) {
		filesArray = array;
		returnArray = new ArrayList<File>();
		isNOT = condition;
		filter();
	}

	/**
	 * the filter method.
	 */
	public void filter() {
		for (File file : this.filesArray) {
			if (isNOT) {
				returnArray.add(file);
			}
		}
	}

	/**
	 * @return array to return
	 */
	public ArrayList<File> getReturn(){
		return returnArray;
	}
}
