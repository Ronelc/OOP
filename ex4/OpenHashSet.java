import java.util.LinkedList;

/**
 * Creates a data structure of type OpenHashSet
 */
public class OpenHashSet extends SimpleHashSet {

	/** array of linked list */
	private WrapperArray[] openHashArray;

	/** count the num of item in the array */
	private int itemsCounter = 0;

	/** The number by which we need to divide or multiply when we resize the table */
	private final int UPDATE = 2;


	/**
	 * A default constructor. Constructs a new, empty table with default initial capacity (16),
	 * upper load factor (0.75) and lower load factor (0.25).
	 */
	public OpenHashSet() {
		super();
		openHashArray = new WrapperArray[INITIAL_CAPACITY];
		creator(INITIAL_CAPACITY);
	}

	/**
	 * Constructs a new, empty table with the specified load factors, and the default initial
	 * capacity (16).
	 * @param upperLoadFactor - The upper load factor of the hash table.
	 * @param lowerLoadFactor - The lower load factor of the hash table.
	 */
	public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
		super(upperLoadFactor, lowerLoadFactor);
		openHashArray = new WrapperArray[INITIAL_CAPACITY];
		creator(INITIAL_CAPACITY);
	}


	/**
	 * Data constructor - builds the hash set by adding the elements one by one. Duplicate values
	 * should be ignored. The new table has the default values of initial capacity (16), upper load
	 * factor (0.75), and lower load factor (0.25).
	 * @param data - Values to add to the set.
	 */
	public OpenHashSet(java.lang.String[] data) {
		super();
		openHashArray = new WrapperArray[INITIAL_CAPACITY];
		creator(INITIAL_CAPACITY);
		for (String toAdd : data) {
			add(toAdd);
		}
	}

	/**
	 * Add a specified element to the set if it's not already in it.
	 * @param newValue New value to add to the set
	 * @return False iff newValue already exists in the set
	 */
	@Override
	public boolean add(java.lang.String newValue) {
		int val = hashing(newValue);
		if (openHashArray[val].getLinkedList().contains(newValue)) {
			return false;
		}
		openHashArray[val].getLinkedList().add(newValue);
		itemsCounter++;
		if (checkUpper()) {
			increaseArray();
		}
		return true;
	}

	/**
	 * @return The current capacity (number of cells) of the table.
	 */
	@Override
	public int capacity() {
		return openHashArray.length;
	}

	/**
	 * Clamps hashing indices to fit within the current table capacity
	 * @param index, index - the index before clamping
	 * @return an index properly clamped
	 */
	@Override
	protected int clamp(int index) {
		return (index & (capacity() - 1));
	}


	/**
	 * Look for a specified value in the set.
	 * @param searchVal Value to search for
	 * @return True iff searchVal is found in the set
	 */
	@Override
	public boolean contains(String searchVal) {
		int val = hashing(searchVal);
		return openHashArray[val].getLinkedList().contains(searchVal);
	}

	/**
	 * Remove the input element from the set.
	 * @param toDelete Value to delete
	 * @return True iff toDelete is found and deleted
	 */
	@Override
	public boolean delete(String toDelete) {
		int val = hashing(toDelete);
		if (contains(toDelete)) {
			openHashArray[val].getLinkedList().remove(toDelete);
			itemsCounter--;
			if (checkLower()) {
				decreaseArray();
			}
			return true;
		}
		return false;
	}

	/**
	 * @return The number of elements currently in the set
	 */
	@Override
	public int size() {
		return itemsCounter;
	}

	/**
	 * function that create a new array.
	 */
	private void creator(int capacity) {
		for (int i = 0; i < capacity; i++) {
			openHashArray[i] = new WrapperArray();
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
	 * main function to create new array and add all the items to it.
	 * @param capacity tha number of capacity that needed.
	 */
	private void resize(int capacity) {
		WrapperArray[] temporaryArray = openHashArray.clone();
		openHashArray = new WrapperArray[capacity];
		creator(capacity());
		itemsCounter = 0;
		for (WrapperArray array : temporaryArray) {
			for (String str : array.getLinkedList()) {
				add(str);
			}
		}
	}


	/**
	 * Wrapper class that create the liked list object
	 */
	private static class WrapperArray {

		/** linked list */
		private final LinkedList<String> linkedList;

		/**
		 * create a linked list
		 */
		private WrapperArray() {
			linkedList = new LinkedList<String>();
		}

		/**
		 * get the linked list
		 * @return linked list
		 */
		LinkedList<String> getLinkedList() {
			return linkedList;
		}
	}
}
