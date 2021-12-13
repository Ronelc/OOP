import oop.ex3.spaceship.*;

import java.util.HashMap;
import java.util.Set;

/**
 * this class creat locker object
 */
public class Locker extends ParentClass {

	/** object of long term locker */
	private final LongTermStorage lockerLTS;

	/** list of list that contain the constraint */
	private final Item[][] constraint;

	/**  error messages*/
	String MSG2 = "Error: Your request cannot be completed at" +
				  "this time. Problem: the locker does not contain %d items of type %s";
	String MSG3 = "Error: Your request cannot be completed at this time. Problem: cannot " +
				  "remove a negative number of items of type %s ";
	String MSG4 = "Warning: Action successful, but has caused items to be moved to storage";
	String MSG5 = "Error: Your request cannot be completed at this time Problem: the locker cannot contain" +
				  " items of type %s, as it contains a contradicting item";


	/**
	 * This constructor initializes a Locker object that is associated with the given long-term storage (you
	 * can assume they reside in the same spaceship), with the given capacity and Item constraints.
	 * @param lts
	 * @param capacity
	 * @param constraints
	 */
	public Locker(LongTermStorage lts, int capacity, Item[][] constraints) {
		lockerLTS = lts;
		constraint = constraints;
		classCapacity = capacity;
		availableCapacity = capacity;
		map = new HashMap<String, Integer>();
	}

	/**
	 * add item to the locker if possible
	 * @param item item that need to add
	 * @param n number of times
	 * @return -1 if not succeed, 1 if succeed in the long term, 0 if succeed in the locker
	 */
	public int addItem(Item item, int n) {
		String msg1 = String.format(MSG1, n, item.getType());
		String msg2 = String.format(MSG1, (int) n-n/2, item.getType());
		String msg5 = String.format(MSG5, item.getType());
		if (n >= 0 && item.getType() != null) {
			if (checkConstraints(item)) {
				if (item.getVolume() * n <= getAvailableCapacity()) {
					if (item.getVolume() * n + item.getVolume() * getItemCount(item.getType())
						<= getAvailableCapacity() * 0.5) {
						map.put(item.getType(), n + getItemCount(item.getType()));
						availableCapacity -= (item.getVolume()) * n;
						return SUCCESSFUL;
					}
					if (lockerLTS.addItem(item, n - (n / 5)) == 0) {
						map.put(item.getType(), (n / 5) + getItemCount(item.getType()));
						availableCapacity -= (item.getVolume()) * ( (int) (n / 5));
						System.out.println(MSG4);
						return RETURN1;
					}
					System.out.println((msg2));
					return ERROR1;
				}
				System.out.println((msg1));
				return ERROR1;
			}
			System.out.println(msg5);
			return ERROR2;
		}
		System.out.println(MSG6);
		return ERROR1;
	}

	/**
	 * remove item from the locker and print informative message
	 * @param item item that need to remove
	 * @param n number of times
	 * @return -1 if not succeed, 0 if succeed.
	 */
	public int removeItem(Item item, int n) {
		if (n >= 0) {
			if (getItemCount(item.getType()) >= n) {
				map.put(item.getType(), getItemCount(item.getType()) - n);
				availableCapacity += n * item.getVolume();
				return SUCCESSFUL;
			}
			String msg2 = String.format(MSG2, n, item.getType());
			System.out.println(msg2);
			return ERROR1;
		}
		String msg3 = String.format(MSG3, item.getType());
		System.out.println(msg3);
		return ERROR1;

	}

	/*
	checks if there is a Constraints with other elements
     */
	private boolean checkConstraints(Item item) {
		Set<String> keyList = map.keySet();
		for (String key : keyList) {
			for (int i = 0; i < constraint.length; i++) {
				if (key.equals(constraint[i][0].getType()) && map.get(key) > 0) {
					if (constraint[i][1].getType().equals(item.getType())) {
						return false;
					}
				}
				if (key.equals(constraint[i][1].getType()) && map.get(key) > 0) {
					if (constraint[i][0].getType().equals(item.getType())) {
						return false;
					}
				}
			}
		}
		return true;
	}


}

