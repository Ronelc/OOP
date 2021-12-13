package filesprocessing.filters;

import java.io.File;

/**
 * this class Creating a filter File by size is strictly greater than the given number of k-bytes And if we
 * get in the false parameter then the filter will leave the small files equal to the number
 */
public class GreaterThan extends Comparison {

	public GreaterThan(double num, File[] array, boolean condition) {
		super(num, array, condition);
	}

	@Override
	protected boolean compare(double size, double limit) {
		return size > limit;
	}
}
