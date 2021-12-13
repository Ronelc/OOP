package filesprocessing;

import java.io.File;

/**
 * This class creates an object of Sort. And arranges the array by the conditions given as parameters if abs -
 * Sort files by absolute name type - Sort files by file type, going from ’a’ to ’z’ size - Sort files by file
 * size, going from smallest to largest. and then compare the conditions to check REVERSE
 */
public class Sort {

	/** the array we need to sort */
	private final File[] arrayToSort;

	/** the array size */
	private int size;

	/**
	 * Sort contracture
	 * @param toSort the array we need to sort
	 * @param sortBy parameter - abs/type/size.
	 * @param condition is REVERSE
	 */
	public Sort(File[] toSort, String sortBy, boolean condition) {
		arrayToSort = toSort;
		size = toSort.length;
		sort(arrayToSort, size, sortBy, condition);
	}


	/**
	 * mergeSort method.
	 * @param array - array to sort
	 * @param size - size of array
	 * @param sortBy - A String that tells the method by which parameters to order
	 * @param condition - boolean variable that tells the method whether to arrange straight or in reverse
	 */
	public static void sort(File[] array, int size, String sortBy, boolean condition) {
		if (size < 2) {
			return;
		}
		int mid = size / 2;
		File[] left = new File[mid];
		File[] right = new File[size - mid];
		System.arraycopy(array, 0, left, 0, mid);
		if (size - mid >= 0) {
			System.arraycopy(array, mid, right, 0, size - mid);
		}
		sort(left, mid, sortBy, condition);
		sort(right, size - mid, sortBy, condition);
		merge(array, left, right, mid, size - mid, sortBy, condition);
	}

	/**
	 * The method that makes the merge
	 * @param array - array to sort
	 * @param leftArray - array of Left obj of the array
	 * @param rightArray - array of right obj of the array
	 * @param left - num of mid of array
	 * @param right - num of length of array - mid
	 * @param sortBy - A String that tells the method by which parameters to order
	 * @param condition - boolean variable that tells the method whether to arrange straight or in reverse
	 */
	public static void merge(File[] array, File[] leftArray, File[] rightArray, int left, int right,
							 String sortBy, boolean condition) {
		int i = 0, j = 0, k = 0;
		while (i < left && j < right) {
			int compare = new SortRunner(sortBy, leftArray[i], rightArray[j]).comparator;
			if ((compare > 0) == condition) {
				array[k++] = leftArray[i++];
			} else {
				array[k++] = rightArray[j++];
			}
		}
		while (i < left) {
			array[k++] = leftArray[i++];
		}
		while (j < right) {
			array[k++] = rightArray[j++];
		}
	}

	/**
	 * @return the sorted array
	 */
	public File[] getSortedArray() {
		return arrayToSort;
	}

}

/**
 * The class that makes the comparisons between the elements sent to it according to there type
 */
class SortRunner {

	public int comparator;

	public SortRunner(String sortBy, File file1, File file2) {
		switch (sortBy) {
		case "abs":
			comparator = new Abs().compare(file1, file2);
			break;
		case "type":
			comparator = new Type().compare(file1, file2);
			break;
		case "size":
			comparator = new Size().compare(file1, file2);
			break;
		}
	}
}

/**
 * Sort files by absolute name, going from ’a’ to ’z’
 */
class Abs {

	public int compare(File file1, File file2) {
		return file1.getAbsolutePath().compareTo(file2.getAbsolutePath());
	}
}

/**
 * Sort files by file type, going from ’a’ to ’z’
 */
class Type {

	public int compare(File file1, File file2) {
		String file1Type = getType(file1.getName());
		String file2Type = getType(file2.getName());
		if (file2Type.compareTo(file1Type) == 0) {
			return file1.getAbsolutePath().compareTo(file2.getAbsolutePath());
		}
		return file1Type.compareTo(file2Type);
	}

	/*
	* returns the type of the file according to the instructions given.
   	*/
	private String getType(String fileName) {
		int period = fileName.lastIndexOf('.');
		if (period <= 0 || period == fileName.length()) {
			return "";
		}
		return fileName.substring(period + 1);
	}
}

/**
 * Sort files by file size, going from smallest to largest.
 */
class Size {

	public int compare(File file1, File file2) {
		if (file1.length() == file2.length()) {
			return file2.getAbsolutePath().compareTo(file1.getAbsolutePath());
		} else if (file1.length() > file2.length()) {
			return -1;
		}
		return 1;
	}
}