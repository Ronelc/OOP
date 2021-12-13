/**
 * Creates a data structure of type ClosedHashSet.
 */
public class ClosedHashSet extends SimpleHashSet {

	/** array of linked list */
	private String[] closeHashArray;

	/** count the num of item in the array */
	private int itemsCounter = 0;

	/** An array to checks if there has been anything in this cell in the past */
	private int[] intsArray;

	/** The number we have to divide with */
	private final int PROBING = 2;

	/** The number by which we need to divide or multiply when we resize the table */
	private final int UPDATE = 2;

	/** the cell is empty */
	private final int EMPTY = 0;

	/** the cell is full now or was full before */
	private final int FULL = 1;

	/** the string doesnt exist*/
	private final int NOTFOUND = -1;

	/**
	 * Constructs a new, empty table with the specified load factors, and the default initial
	 * capacity (16).
	 * @param upperLoadFactor - The upper load factor of the hash table.
	 * @param lowerLoadFactor - The lower load factor of the hash table
	 */
	public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
		super(upperLoadFactor, lowerLoadFactor);
		closeHashArray = new String[INITIAL_CAPACITY];
		intsArray = new int[INITIAL_CAPACITY];
	}

	/**
	 * A default constructor. Constructs a new, empty table with default initial capacity (16),
	 * upper load factor (0.75) and lower load factor (0.25).
	 */
	public ClosedHashSet() {
		super();
		closeHashArray = new String[INITIAL_CAPACITY];
		intsArray = new int[INITIAL_CAPACITY];
	}

	/**
	 * Data constructor - builds the hash set by adding the elements one by one. Duplicate
	 * values should be ignored. The new table has the default values of initial capacity (16),
	 * upper load factor (0.75), and lower load factor (0.25).
	 * @param data - Values to add to the set.
	 */
	public ClosedHashSet(java.lang.String[] data) {
		closeHashArray = new String[INITIAL_CAPACITY];
		intsArray = new int[INITIAL_CAPACITY];
		for (String val : data) {
			add(val);
		}
	}

	/**
	 * capacity in class SimpleHashSet
	 * @return The current capacity (number of cells) of the table.
	 */
	@Override
	public int capacity() {
		return closeHashArray.length;
	}

	/**
	 * Clamps hashing indices to fit within the current table capacity
	 * @param index, index - the index before clamping
	 * @return an index properly clamped
	 */
	@Override
	protected int clamp(int index) {
		for (int i = 0; i < capacity(); i++) {
			int num = (index + (i + i * i) / PROBING) & ((capacity()) - 1);
			if (closeHashArray[num] == null) {
				return num;
			}
		}
		return 0;
	}


	/**
	 * Add a specified element to the set if it's not already in it.
	 * @param newValue New value to add to the set
	 * @return False iff newValue already exists in the set
	 */
	@Override
	public boolean add(String newValue) {
		int returnNum = hashing(newValue);
		if (!contains(newValue)) {
			closeHashArray[returnNum] = newValue;
			intsArray[returnNum] = FULL;
			itemsCounter++;
			if (checkUpper()) {
				increaseArray();
			}
			return true;
		}
		return false;
	}

	/**
	 *Look for a specified value in the set.
	 * @param searchVal Value to search for
	 * @return True iff searchVal is found in the set
	 */
	@Override
	public boolean contains(String searchVal) {
		return search(searchVal) != NOTFOUND;
	}

	/**
	 *Remove the input element from the set.
	 * @param toDelete Value to delete
	 * @return True iff toDelete is found and deleted
	 */
	@Override
	public boolean delete(String toDelete) {
		if (search(toDelete) != NOTFOUND){
			closeHashArray[search(toDelete)] = null;
			itemsCounter --;
			if (checkLower()) {
				decreaseArray();
			}
			return true;
		}
		return false;
	}

	/**
	 * return the number of full cells.
	 */
	@Override
	public int size() {
		return itemsCounter;
	}

	/**
	 * main function to create new array and add all the items to it.
	 * @param capacity tha number of capacity that needed.
	 */
	private void resize(int capacity) {
		String[] temporaryArray = closeHashArray.clone();
		closeHashArray = new String[capacity];
		intsArray = new int[capacity];
		itemsCounter = 0;
		for (String element : temporaryArray) {
			if (element != null) {
				add(element);
			}
		}
	}

	/**
	 * create bigger array and add all the items to the new array
	 */
	private void increaseArray() {
		resize(capacity() * UPDATE);
	}

	/**
	 * create smaller array and add all the items to the new array
	 */
	private void decreaseArray() {
		resize(capacity() / UPDATE);
	}

	/**
	 *
	 * @param toSearch string to search
	 * @return -1 if the string does not exist, the index otherwise.
	 */
	private int search(String toSearch) {
		int hash = toSearch.hashCode();
		for (int i = 0; i < capacity(); i++) {
			int index = (hash + (i + i * i) / PROBING) & ((capacity()) - 1);
			if (closeHashArray[index] == null && intsArray[index] == EMPTY) {
				return NOTFOUND;
			}
			else if (closeHashArray[index] != null){
				if (closeHashArray[index].equals(toSearch)){
					return index;
				}
			}
		}
		return NOTFOUND;
	}


}
