package filesprocessing.filters;
import java.util.ArrayList;
import java.io.File;

/**
 * Class creates a filter object File size is between (inclusive) the given numbers (in k-bytes)
 */
public class Between {

	/** array of files */
	private final File[] filesArray;

	/** array to return*/
	private final ArrayList<File> returnArray;

	/** the up limit */
	private final double upLimit;

	/** the down limit */
	private final double downLimit;

	/** is NOT filter  */
	private final boolean isNOT;

	/** change bytes to KB*/
	private final double KB = 1024.0;

	/**
	 * constructor
	 * @param down the down limit
	 * @param up the up limit
	 * @param array array of files
	 * @param condition is NOT filter
	 */
	public Between(double down, double up, File[] array, boolean condition) {
		filesArray = array;
		downLimit = down;
		upLimit = up;
		isNOT = condition;
		returnArray = new ArrayList<File>();
		filter();
	}

	/**
	 * the filter method.
	 */
	private void filter() {
		for (File file : filesArray) {
			double size = file.length() / KB;
			if ((size >= downLimit && size <= upLimit) == isNOT){
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


