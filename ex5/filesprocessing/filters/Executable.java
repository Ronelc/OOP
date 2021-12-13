package filesprocessing.filters;

import java.io.File;

/**
 * Class creates a filter object that filter Does file have execution permission?
 */
public class Executable extends Terms{

	public Executable(boolean executable, File[] array, boolean condition) {
		super(executable, array, condition);
	}

	@Override
	public void filter() {
		for (File file : getFilesArray()) {
			if ((file.canExecute() == getBol()) == getCondition()) {
				getReturn().add(file);
			}
		}
	}
}
