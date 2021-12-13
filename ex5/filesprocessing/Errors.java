package filesprocessing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Errors {

	/** files path*/
	private final Path path;

	/** array of warning obj*/
	private final ArrayList<Warnings> warningsArray;

	/** the default order*/
	private final String DEFAULTORDER = "abs";

	/** the ORDER string */
	private final String ORDER = "ORDER";

	/** the FILTER string */
	private final String FILTER = "FILTER";

	/** error message */
	private final String ERROR = "ERROR: in ";

	/** read file error message */
	private final String READERROR = "ERROR: Can't reade the file";


	/**
	 * The class tests the file for instructions on the filters and the order checks that they meet the
	 * conditions and if it uses section at the end are given an array containing objects of type
	 * section
	 */
	public Errors(String path) {
		this.path = Paths.get(path);
		this.warningsArray = new ArrayList<>();
	}

	/**
	 * method that check the file
	 * @throws Error2 error message
	 * @throws IOException exception
	 */
	public void check() throws Error2, IOException {
		List<String> fileLines;
		try {
			fileLines = Files.readAllLines(this.path);
		}
		catch (IOException exception){
			throw new Error2(READERROR);
		}
		int check = 0;
		int size = fileLines.size();
		while (size > check) {
			Warnings warning;
			if (!fileLines.get(check).equals(FILTER)) {
				throw new Error2(ERROR + FILTER);
			} else if (size <= check + 2 || !fileLines.get(check + 2).equals(ORDER)) {
				throw new Error2(ERROR + ORDER);
			} else if (size == check + 3 || fileLines.get(check + 3).equals(FILTER)) {
				warning = new Warnings(fileLines.get(check + 1), DEFAULTORDER, check + 1, check + 2);
				warningsArray.add(warning);
				check += 3;
			} else {
				warning = new Warnings(fileLines.get(check + 1), fileLines.get(check + 3), check + 1,
									   check + 3);
				warningsArray.add(warning);
				check += 4;
			}
		}
	}

	/**
	 * @return the warning array
	 */
	public ArrayList<Warnings> getWarning() {
		return warningsArray;
	}
}
