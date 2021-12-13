package filesprocessing.filters;

import java.io.File;

/**
 * Class creates a filter object that filtered by value is the prefix of the file name
 */
public class Prefix extends Str{

	public Prefix(String str, File[] array, boolean condition) {
		super(str, array, condition);
	}

	@Override
	public void filter() {
		for (File file : getFilesArray()) {
			if ((file.getName().startsWith(getStr())) == getCondition()) {
				getReturn().add(file);
			}
		}
	}
}
