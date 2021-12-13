package filesprocessing.filters;

import java.io.File;

/**
 * Class creates a filter object that filter value is contained in the file name (excluding path)
 */
public class Contains extends Str {


	public Contains(String str, File[] array, boolean condition) {
		super(str, array, condition);
	}

	@Override
	public void filter() {
		for (File file : getFilesArray()) {
			if ((file.getName().contains(getStr())) == getCondition()) {
				getReturn().add(file);
			}
		}
	}
}
