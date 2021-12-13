package filesprocessing.filters;

import java.io.File;
import java.util.ArrayList;

public class Comparison {

	/** array of the filter arguments*/
	private final File[] filesArray;

	/** array to return*/
	private final ArrayList<File> returnArray;

	/** the limit search by */
	private final double limitNumber;

	/** is NOT filter  */
	private final boolean isNOT;

	/** change bytes to KB*/
	private final double KB = 1024.0;

	/**
	 * constructor
	 * @param num the limit
	 * @param array array of files
	 * @param condition is NOT filter
	 */
	public Comparison(double num, File[] array, boolean condition) {
		filesArray = array;
		limitNumber = num;
		returnArray = new ArrayList<File>();
		isNOT = condition;
		filter();
	}

	private void filter() {
		for (File file : filesArray) {
			double size = file.length() / KB;
			if ((compare(size, limitNumber)) == isNOT) {
				returnArray.add(file);
			}
		}
	}

	/**
	 * the filter method.
	 */
	public ArrayList<File> getReturn() {
		return returnArray;
	}

	/**
	 * the condition to search by. the inherit classes will override this method.
	 * @param size file size
	 * @param limit the limit number
	 * @return boolean condition
	 */
	protected boolean compare(double size, double limit){
		return true;
	}
}
