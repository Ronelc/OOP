package filesprocessing.filters;

import java.io.File;

/**
 * Class creates a filter object that filtered by Does file have writing permission
 */
public class Writable extends Terms{

	public Writable(boolean executable, File[] array, boolean condition) {
		super(executable, array, condition);
	}

	@Override
	public void filter() {
		for (File file : getFilesArray()) {
			if ((file.canWrite() == getBol()) == getCondition()) {
				getReturn().add(file);
			}
		}
	}
}
