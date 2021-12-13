import java.util.Map;
import java.util.Set;

/**
 * This is the classic parent from which Locker and LST are inherited.
 */
public class ParentClass {

	/** map of all the item in the locker, string - type, integer - num of times */
	protected Map<String, Integer> map;

	/** error/ success variables*/
	final int ERROR1 = -1;
	final int ERROR2 = -2;
	final int ERROR3 = -3;
	final int RETURN1 = 1;
	final int SUCCESSFUL = 0;

	/** the start capacity of the class*/
	protected int classCapacity;

	/** the available capacity of the class*/
	protected int availableCapacity;

	/**error messages */
	String MSG1 = "Error: Your request cannot be completed at this time Problem: no room for %d " +
				  "items of type %s ";
	String MSG6 = "Error: Your request cannot be completed at this time.";


	/**
	 * return the num of the item in the locker
	 * @param type type of the item
	 * @return num of item
	 */
	public int getItemCount(String type) {
		Set<String> keyList = map.keySet();
		for (String key : keyList) {
			if (key.equals(type)) {
				return map.get(type);
			}
		}
		return 0;
	}

	/**
	 * @return returns a map of all the Items contained in the long-term/locker storage unit, and their
	 * respective quantities
	 */
	public Map<String, Integer> getInventory() {
		return map;
	}

	/**
	 *
	 * @return Returns the long-term storage’s/locker total capacity.
	 */
	public int getCapacity() {
		return classCapacity;
	}

	/**
	 *
	 * @return the long-term storage’s/locker available capacity, i.e. how many storage units are
	 * unoccupied by Items
	 */
	public int getAvailableCapacity() {
		return availableCapacity;
	}
}
