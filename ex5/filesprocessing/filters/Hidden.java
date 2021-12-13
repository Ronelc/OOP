package filesprocessing.filters;

import java.io.File;

/**
 * Class creates a filter object that filter by Is the file a hidden file?
 */
public class Hidden extends Terms {

	public Hidden(boolean executable, File[] array, boolean condition) {
		super(executable, array, condition);
	}

	@Override
	public void filter() {
		for (File file : getFilesArray()) {
			if ((file.isHidden() == getBol()) == getCondition()) {
				getReturn().add(file);
			}
		}
	}
}
