package filesprocessing.filters;

import java.io.File;

/**
 * Class creates a filter object that filtered by File size is strictly less than the given number
 * of k-bytes
 */
public class SmallerThan extends Comparison{


	public SmallerThan(double num, File[] array, boolean condition) {
		super(num, array, condition);
	}

	@Override
	protected boolean compare(double size, double limit) {
		return size < limit;
	}
}
