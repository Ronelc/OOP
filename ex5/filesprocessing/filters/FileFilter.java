package filesprocessing.filters;

import java.io.File;

/**
 * Class creates a filter object that filtered value equals the file name (excluding path)
 */
public class FileFilter extends Str {

	public FileFilter(String str, File[] array, boolean condition) {
		super(str, array, condition);
	}

	@Override
	public void filter() {
		for (File file : getFilesArray()) {
			if ((getStr().equals(file.getName())) == getCondition()) {
				getReturn().add(file);
			}
		}
	}
}
