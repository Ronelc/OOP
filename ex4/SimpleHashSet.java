

public abstract class SimpleHashSet extends java.lang.Object implements SimpleSet {

	/** Describes the higher load factor of a newly created hash set. */
	protected static final float DEFAULT_HIGHER_CAPACITY = 0.75F;

	/** Describes the lower load factor of a newly created hash set. */
	protected static final float DEFAULT_LOWER_CAPACITY = 0.25F;

	/** Describes the capacity of a newly created hash set */
	protected static final int INITIAL_CAPACITY = 16;


	/** The minimal table capacity that allowed */
	private final int MINIMAL_CAPACITY = 1;

	/** upLoadFactor */
	protected static float upLoadFactor;

	/** lowLoadFactor */
	protected static float lowLoadFactor;


	/**
	 * Constructs a new hash set with the default capacities given in DEFAULT_LOWER_CAPACITY and
	 * DEFAULT_HIGHER_CAPACITY.
	 */
	protected SimpleHashSet() {
		upLoadFactor = DEFAULT_HIGHER_CAPACITY;
		lowLoadFactor = DEFAULT_LOWER_CAPACITY;
	}

	/**
	 * Constructs a new hash set with capacity INITIAL_CAPACITY
	 * @param upperLoadFactor - the upper load factor before rehashing
	 * @param lowerLoadFactor - the lower load factor before rehashing
	 */
	protected SimpleHashSet(float upperLoadFactor, float lowerLoadFactor) {
		upLoadFactor = upperLoadFactor;
		lowLoadFactor = lowerLoadFactor;
	}

	/**
	 * @return The current capacity (number of cells) of the table.
	 */
	public abstract int capacity();

	/**
	 * @return The lower load factor of the table.
	 */
	protected float getLowerLoadFactor() {
		return lowLoadFactor;
	}

	/**
	 * @return The higher load factor of the table.
	 */
	protected float getUpperLoadFactor() {
		return upLoadFactor;
	}

	/**
	 * Clamps hashing indices to fit within the current table capacity
	 * @param index - the index before clamping.
	 * @return an index properly clamped.
	 */
	protected abstract int clamp(int index);

	/**
	 * check if resize needed
	 * @return true if we have to resize. false otherwise.
	 */
	protected boolean checkUpper() {
		return size() > getUpperLoadFactor() * capacity();
	}

	/**
	 * check if resize needed
	 * @return true if we have to resize. false otherwise.
	 */
	protected boolean checkLower() {
		return size() < getLowerLoadFactor() * capacity() && capacity() > MINIMAL_CAPACITY;
	}

	/**
	 * function that return hash code in the form of index in the array
	 * @param toHash the word that need hash code
	 * @return hash code
	 */
	protected int hashing(String toHash){
		return clamp(toHash.hashCode());
	}


}
