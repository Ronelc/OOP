import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * Analyzer data class.
 */
public class SimpleSetPerformanceAnalyzer {

	/** = new CollectionFacadeSet[5]; */
	private static SimpleSet[] dataArray;

	private static final String word1 = "hi";
	private static final String word2 = "-13170890158";
	private static final String word3 = "23";
	private static final String txt1 ="src/data1.txt";
	private static final String txt2 = "src/data2.txt";

	/** dividing by this number for milli seconds*/
	private static final int MS = 1000000;

	/** number of iterations for linked list*/
	private static final int LINKEDLISTITERATIONS = 7000;

	/** number of iterations for other arrays*/
	private static final int SETITERATIONS = 70000;


	/**
	 * The method creates the 5 data structures that we run tests on
	 */
	private static void createCollection() {
		ClosedHashSet closedHashSet = new ClosedHashSet();
		OpenHashSet openHashSet = new OpenHashSet();
		CollectionFacadeSet hashSet = new CollectionFacadeSet(new HashSet<String>());
		CollectionFacadeSet linkedList = new CollectionFacadeSet(new LinkedList<String>());
		CollectionFacadeSet treeSet = new CollectionFacadeSet(new TreeSet<String>());
		dataArray = new SimpleSet[]{openHashSet, closedHashSet, treeSet, linkedList, hashSet};
	}

	/**
	 * add the words file as a parameter to 5 data structures
	 * @param path - path to file with array of word to add
	 */
	private static void addString(String path) {
		String[] stringsArray = Ex4Utils.file2array(path);
		for (SimpleSet set : dataArray) {
			long timeBefore = System.nanoTime();
			for (String str : stringsArray) {
				set.add(str);
			}
			long difference = (System.nanoTime() - timeBefore) / MS;
			System.out.println(difference);
		}
	}

	/**
	 * tha time to find word in a array
	 * @param word a word to check if it in the array
	 * @param path  - path to file with array of word
	 */
	private void contain(String word, String path) {
		String[] stringsArray = Ex4Utils.file2array(path);
		int i = -1;
		for (SimpleSet set : dataArray) {
			i++;
			for (String str : stringsArray) {
				set.add(str);
			}
			if (i == 3) {
				long timeBefore = System.nanoTime();
				for (int j = 0; j < LINKEDLISTITERATIONS; j++) {
					set.contains(word);
				}
				long difference = (System.nanoTime() - timeBefore) / LINKEDLISTITERATIONS;
				System.out.println("linkedList:" + difference);
			}
			else {
				for (int j = 0; j < SETITERATIONS; j++) {
					set.contains(word);
				}
				long timeBefore = System.nanoTime();
				for (int j = 0; j < SETITERATIONS; j++) {
					set.contains(word);
				}
				long difference = (System.nanoTime() - timeBefore) / SETITERATIONS;
				System.out.println(difference);
			}
		}
	}

	/**
	 * the main function
	 */
	public static void main(String[] args) {
		SimpleSetPerformanceAnalyzer analyzer = new SimpleSetPerformanceAnalyzer();
		createCollection();



//		addString(txt1);
//		addString(txt2);
//				analyzer.contain(word1, txt1);
//				analyzer.contain(word2, txt1);
//				analyzer.contain(word3, txt2);
				analyzer.contain(word1, txt2);
	}
}
