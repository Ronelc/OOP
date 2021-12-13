import oop.ex3.spaceship.Item;

import java.util.HashMap;

/** this class creatr a LTS object */
public class LongTermStorage extends ParentClass {

	/** start capacity */
	final private int CAPACITY = 1000;


	public LongTermStorage() {
		classCapacity = CAPACITY;
		availableCapacity = CAPACITY;
		map = new HashMap<String, Integer>();
	}

	/**
	 * try to add items to the locker and print informative message if not succeed
	 * @param item item that we want to add
	 * @param n num of times
	 * @return 0 if succeed -1 if dont
	 */
	public int addItem(Item item, int n) {
		if (n >= 0) {
			if (item.getVolume() * n <= availableCapacity) {
				map.put(item.getType(), n + getItemCount(item.getType()));
				availableCapacity -= n * item.getVolume();
				return SUCCESSFUL;
			}
			String msg1 = String.format(MSG1, n, item.getType());
			System.out.println((msg1));
			return ERROR1;
		}
		System.out.println(MSG6);
		return ERROR1;
	}

	/**
	 * reset the long term locker
	 */
	public void resetInventory() {
		map = new HashMap<String, Integer>();
		availableCapacity = CAPACITY;

	}


}
