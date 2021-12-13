package filesprocessing;

import java.io.File;


/**
 * The class identifies which order to make creates an object of the order
 */
public class OrderMain {

	/** array of the order arguments*/
	private final String[] orderArray;

	/** array to return*/
	private File[] returnArray;

	/**
	 * order constructor
	 * @param order order string
	 * @param files files array
	 */
	public OrderMain(String order, File[] files) {
		orderArray = order.split("#");
		returnArray = files;
		orderRunner();
	}

	/**
	 * check which order print by.
	 */
	private void orderRunner() {
		switch (orderArray[0]) {
		case "abs":
			abs();
			break;
		case "type":
			type();
			break;
		case "size":
			size();
			break;
		}
	}

	/**
	 * method that create a order that sort by abs
	 */
	private void abs() {
		Sort sort;
		if (orderArray.length == 2 && orderArray[1].equals("REVERSE")){
			sort = new Sort(returnArray, "abs", true);
			returnArray = sort.getSortedArray();return;
		}
		sort = new Sort(returnArray, "abs", false);
		returnArray = sort.getSortedArray();
	}

	/**
	 * method that create a order that sort by type
	 */
	private void type() {
		Sort sort;
		if (orderArray.length == 2 && orderArray[1].equals("REVERSE")){
			sort = new Sort(returnArray, "type", true);
			returnArray = sort.getSortedArray();return;
		}
		sort = new Sort(returnArray, "type", false);
		returnArray = sort.getSortedArray();
	}

	/**
	 * method that create a order that sort by size
	 */
	private void size() {
		Sort sort;
		if (orderArray.length == 2 && orderArray[1].equals("REVERSE")){
			sort = new Sort(returnArray, "size", false);
			returnArray = sort.getSortedArray();
			return;
		}
		sort = new Sort(returnArray, "size", true);
		returnArray = sort.getSortedArray();
	}

	/**
	 * @return array to return/
	 */
	public File[] getReturnArray(){return returnArray;}
}
