
package filesprocessing;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.io.File;

/**
 * the main class
 */
public class DirectoryProcessor {


	/**
	 * the main function
	 * @param args files and command file.
	 */
	public static void main(String[] args) {

		ArrayList<Warnings> warningsArray;
		try {
			if (args.length != 2) {
				throw new Error2("ERROR: file missing");
			}
			String sourcedir = args[0];
			String commandfile = args[1];
			ArrayList<File> filteredFiles = new ArrayList<>();
			File[] filesArray = new File(sourcedir).listFiles();
			if (filesArray == null){
				throw new Error2("ERROR: file missing");
			}
			for (File file : filesArray) {
				if (!file.isDirectory()) {
					filteredFiles.add(file);
				}
			}
			File[] filesForFilter = filteredFiles.toArray(new File[0]);
			Errors error = new Errors(commandfile);
			error.check();
			warningsArray = error.getWarning();
			for (Warnings warning : warningsArray) {
				try {
					warning.checkFilter();
				} catch (Error1 e1) {
					System.err.print(e1.getMessage() + "\n");
				}
				try {
					warning.checkOrder();
				} catch (Error1 e2) {
					System.err.print(e2.getMessage() + "\n");
				}
				FiltersMain filtersMain = new FiltersMain(warning.getFilter(), filesForFilter);
				File[] filesForOrder = filtersMain.getReturn().toArray(new File[0]);
				OrderMain orderMain = new OrderMain(warning.getOrder(), filesForOrder);
				for (File file1 : orderMain.getReturnArray()) {
					System.out.println(file1.getName());
				}
			}
		}
		catch (Error2 | IOException e3) {
			System.err.print(e3.getMessage());
		}
	}
}

