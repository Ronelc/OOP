package filesprocessing;

import java.util.ArrayList;
import java.util.Arrays;

public class Warnings {

	/** array of the filter arguments*/
	private final String[] filtersArray;

	/** array of the orders arguments*/
	private final String[] ordersArray;

	/** the line of filter*/
	private final int filterLine;

	/** the line of order */
	private final int orderLine;

	/** the filter */
	private String filter;

	/** the order */
	private String order;

	/** Warning message */
	private final String MSG = "Warning in line ";

	/** the default order*/
	private final String DEFAULTORDER = "abs";

	/** the default filter*/
	private final String DEFAULTFILTER = "all";

	/** array of all orders */
	ArrayList<String> allOrders = new ArrayList<String>(Arrays.asList("abs", "type", "size"));

	/** array of all filters */
	ArrayList<String> allFilters = new ArrayList<String>(Arrays.asList("greater_than", "between",
																	   "smaller_than", "file", "contains",
																	   "prefix", "suffix", "writable",
																	   "executable", "hidden", "all"));

		ArrayList<String> strFilters = new ArrayList<String>(Arrays.asList("file", "contains",
																				"prefix", "suffix"));

	/** array of greater/smaller */
	ArrayList<String> comparisonFilters = new ArrayList<String>(Arrays.asList("greater_than", "smaller_than"));

	/** array of all writable/executable/hidden */
	ArrayList<String> termsFilters = new ArrayList<String>(Arrays.asList("writable", "executable", "hidden"));

	/** array of YES/NO */
	ArrayList<String> conditions = new ArrayList<String>(Arrays.asList("YES", "NO"));


	/**
	 * The class creates an object A containing the name of the filter and the name of their order and
	 * location in the file
	 */
	public Warnings(String filters, String orders, int filterL, int orderL) {
		this.filtersArray = filters.split("#");
		this.ordersArray = orders.split("#");
		this.filterLine = filterL + 1;
		this.orderLine = orderL + 1;
		this.filter = filters;
		this.order = orders;
	}

	/**
	 * method that check the order
	 * @throws Error1 error message
	 */
	public void checkOrder() throws Error1 {
		if (!allOrders.contains(ordersArray[0])) {
			order = DEFAULTORDER;
			throw new Error1(MSG + orderLine);
		}
	}

	/**
	 * method that check the filter
	 * @throws Error1 error message
	 */
	public void checkFilter() throws Error1 {
		int size = filtersArray.length;
		if ((size == 3 || size == 2) && termsFilters.contains(filtersArray[0])) {
			if (!conditions.contains(filtersArray[1])) {
				filter = DEFAULTFILTER;
				throw new Error1(MSG + filterLine);
			}
		}
		if ((size == 3 && comparisonFilters.contains(filtersArray[0]))
				   || size == 2 && comparisonFilters.contains(filtersArray[0])) {
			double num1 = Double.parseDouble(filtersArray[1]);
			if (num1 < 0) {
				filter = DEFAULTFILTER;
				throw new Error1(MSG + filterLine);
			}
		}
		if ((size == 3 && filtersArray[0].equals("between")) || (size == 4)) {
			double num1 = Double.parseDouble(filtersArray[1]);
			double num2 = Double.parseDouble(filtersArray[2]);
			if ((num1 < 0 || num2 < 0) || num1 > num2) {
				filter = DEFAULTFILTER;
				throw new Error1(MSG + filterLine);
			}
		}
		if (size == 1 && !filtersArray[0].equals("all")) {
			if (strFilters.contains(filtersArray[0])){
				filter = DEFAULTFILTER;
				return;
			}
			filter = DEFAULTFILTER;
			throw new Error1(MSG + filterLine);
		}
		else if (filtersArray[0].equals("greater_than") || filtersArray[0].equals("smaller_than")) {
			if (size > 3 || (size == 3 && !(filtersArray[2].equals("NOT")))) {
				filter = DEFAULTFILTER;
				throw new Error1(MSG + filterLine);
			}
		}
		if (!allFilters.contains(filtersArray[0])) {
			filter = DEFAULTFILTER;
			throw new Error1(MSG + filterLine);
		}
	}

	/**
	 * @return the filter
	 */
	public String getFilter(){return filter;}

	/**
	 * @return the order
	 */
	public String getOrder(){return order;}
}
