
package filesprocessing;

import java.io.File;
import java.util.ArrayList;

/**
 * The class identifies what filter to read and happens to the method that will produce it.
 */
public class FiltersMain {

	/** array of the filter arguments*/
	private final String[] filterArray;

	/** files array */
	private final File[] filesArray;

	/** array to return*/
	private ArrayList<File> returnArray;

	/** NOT filter*/
	private final String NOT = "NOT";

	/** YES for hidden/writable/executable */
	private final String YES = "YES";

	/**
	 * filters constructor
	 * @param filter filters string
	 * @param files files array
	 */
	public FiltersMain(String filter, File[] files) {
		filterArray = filter.split("#");
		filesArray = files;
		filterRunner();
	}

	/**
	 * check which filter sort by.
	 */
	private void filterRunner() {
		switch (filterArray[0]) {
		case "greater_than":
			greater();
			break;
		case "between":
			between();
			break;
		case "smaller_than":
			smaller();
			break;
		case "file":
			file();
			break;
		case "contains":
			contains();
			break;
		case "prefix":
			prefix();
			break;
		case "suffix":
			suffix();
			break;
		case "writable":
			writable();
			break;
		case "executable":
			executable();
			break;
		case "hidden":
			hidden();
			break;
		case "all":
			all();
			break;
		}
	}

	/**
	 * method that create a filter that filtering by greater_then
	 */
	private void greater() {
		double limitNumber = Double.parseDouble(filterArray[1]);
		filesprocessing.filters.GreaterThan filter;
		if (filterArray.length == 3 && filterArray[2].equals(NOT)) {
			filter = new filesprocessing.filters.GreaterThan(limitNumber, filesArray, false);
		} else {
			filter = new filesprocessing.filters.GreaterThan(limitNumber, filesArray, true);
		}
		returnArray = filter.getReturn();
	}

	/**
	 * method that create a filter that filtering by between
	 */
	private void between() {
		double downLimit = Double.parseDouble(filterArray[1]);
		double upLimit = Double.parseDouble(filterArray[2]);
		filesprocessing.filters.Between filter;
		if (filterArray.length == 4 && filterArray[3].equals(NOT)) {
			filter = new filesprocessing.filters.Between(downLimit, upLimit, filesArray, false);
		} else {
			filter = new filesprocessing.filters.Between(downLimit, upLimit, filesArray, true);
		}
		returnArray = filter.getReturn();
	}

	/**
	 * method that create a filter that filtering by smaller_then
	 */
	private void smaller() {
		double limitNumber = Double.parseDouble(filterArray[1]);
		filesprocessing.filters.SmallerThan filter;
		if (filterArray.length == 3 && filterArray[2].equals(NOT)) {
			filter = new filesprocessing.filters.SmallerThan(limitNumber, filesArray, false);
		} else {
			filter = new filesprocessing.filters.SmallerThan(limitNumber, filesArray, true);
		}
		returnArray = filter.getReturn();
	}

	/**
	 * method that create a filter that filtering by file
	 */
	private void file() {
		String name = filterArray[1];
		filesprocessing.filters.FileFilter filter;
		if (filterArray.length == 3 && filterArray[2].equals(NOT)) {
			filter = new filesprocessing.filters.FileFilter(name, filesArray, false);
		} else {
			filter = new filesprocessing.filters.FileFilter(name, filesArray, true);
		}
		returnArray = filter.getReturn();
	}

	/**
	 * method that create a filter that filtering by contains
	 */
	private void contains() {
		String str = filterArray[1];
		filesprocessing.filters.Contains filter;
		if (filterArray.length == 3 && filterArray[2].equals(NOT)) {
			filter = new filesprocessing.filters.Contains(str, filesArray, false);
		} else {
			filter = new filesprocessing.filters.Contains(str, filesArray, true);
		}
		returnArray = filter.getReturn();
	}

	/**
	 * method that create a filter that filtering by prefix
	 */
	private void prefix() {
		String str = filterArray[1];
		filesprocessing.filters.Prefix filter;
		if (filterArray.length == 3 && filterArray[2].equals(NOT)) {
			filter = new filesprocessing.filters.Prefix(str, filesArray, false);
		} else {
			filter = new filesprocessing.filters.Prefix(str, filesArray, true);
		}
		returnArray = filter.getReturn();
	}

	/**
	 * method that create a filter that filtering by suffix
	 */
	private void suffix() {
		String str = filterArray[1];
		filesprocessing.filters.Suffix filter;
		if (filterArray.length == 3 && filterArray[2].equals(NOT)) {
			filter = new filesprocessing.filters.Suffix(str, filesArray, false);
		} else {
			filter = new filesprocessing.filters.Suffix(str, filesArray, true);
		}
		returnArray = filter.getReturn();
	}

	/**
	 * method that create a filter that filtering by writable
	 */
	private void writable() {
		String yesOrNo = filterArray[1];
		boolean condition = false;
		if (yesOrNo.equals(YES)) {
			condition = true;
		}
		filesprocessing.filters.Writable filter;
		if (filterArray.length == 3 && filterArray[2].equals(NOT)) {
			filter = new filesprocessing.filters.Writable(condition, filesArray, false);
		} else {
			filter = new filesprocessing.filters.Writable(condition, filesArray, true);
		}
		returnArray = filter.getReturn();
	}

	/**
	 * method that create a filter that filtering by executable
	 */
	private void executable() {
		String yesOrNo = filterArray[1];
		boolean condition = false;
		if (yesOrNo.equals(YES)) {
			condition = true;
		}
		filesprocessing.filters.Executable filter;
		if (filterArray.length == 3 && filterArray[2].equals(NOT)) {
			filter = new filesprocessing.filters.Executable(condition, filesArray, false);
		} else {
			filter = new filesprocessing.filters.Executable(condition, filesArray, true);
		}
		returnArray = filter.getReturn();
	}

	/**
	 * method that create a filter that filtering by hidden
	 */
	private void hidden() {
		String yesOrNo = filterArray[1];
		boolean condition = false;
		if (yesOrNo.equals(YES)) {
			condition = true;
		}
		filesprocessing.filters.Hidden filter;
		if (filterArray.length == 3 && filterArray[2].equals(NOT)) {
			filter = new filesprocessing.filters.Hidden(condition, filesArray, false);
		} else {
			filter = new filesprocessing.filters.Hidden(condition, filesArray, true);
		}
		returnArray = filter.getReturn();
	}

	/**
	 * method that create a filter that filtering by all
	 */
	private void all() {
		filesprocessing.filters.All filter;
		if (filterArray.length == 2 && filterArray[1].equals(NOT)) {
			filter = new filesprocessing.filters.All(filesArray, false);
		} else {
			filter = new filesprocessing.filters.All(filesArray, true);
		}
		returnArray = filter.getReturn();
	}

	/**
	 * @return array to return/
	 */
	public ArrayList<File> getReturn() {
		return returnArray;
	}


}
