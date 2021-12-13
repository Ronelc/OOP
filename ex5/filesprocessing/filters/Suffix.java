package filesprocessing.filters;

import java.io.File;

/**
 * Class creates a filter object that filtered by value is the suffix of the file name
 */
public class Suffix extends Str{
	public Suffix(String str, File[] array, boolean condition) {
		super(str, array, condition);
	}

	@Override
	public void filter() {
		for (File file : getFilesArray()) {
			if ((file.getName().endsWith(getStr())) == getCondition()) {
				getReturn().add(file);
			}
		}
	}
}
