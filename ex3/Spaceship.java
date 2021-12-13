import oop.ex3.spaceship.Item;

/** this class create a space ship */
public class Spaceship extends ParentClass {

	/** name of the spaceShip   */
	final String spaceShipName;

	/** list of int that contains the crew ids **/
	int[] crewIDLst;

	/** the number of the taken lockers */
	final int numberOfLockers;

	/**  list of list that contain constraints*/
	Item[][] constraint;

	/** array that contain lockers **/
	Locker[] lockersList;

	/** count the number of the taken lockers */
	int countLockers = 0;

	/** contain the items that storage long term **/
	LongTermStorage shipLTS;


	/**
	 * make the object of spaceShip
	 * @param name name of the space ship
	 * @param crewIDs crew id
	 * @param numOfLockers num of lockers that spaceship can contain
	 */
	public Spaceship(String name, int[] crewIDs, int numOfLockers, Item[][] constraints) {
		spaceShipName = name;
		crewIDLst = crewIDs;
		numberOfLockers = numOfLockers;
		constraint = constraints;
		lockersList = new Locker[numOfLockers];
		shipLTS = new LongTermStorage();
	}

	/**
	 * This method returns the long-term storage object associated with that Spaceship.
	 * @return
	 */
	public LongTermStorage getLongTermStorage() {
		return shipLTS;
	}

	/**
	 * create locker if capacity ok and the crews id ok
	 * @param crewID id num of the crew
	 * @param capacity capacity of lockers that the space ship can contain
	 * @return 0 if the locker create -1 id not ok -2 if capacity not ok -3
	 */
	public int createLocker(int crewID, int capacity) {
		if (isInList(crewIDLst, crewID)) {
			if (capacity >= 0) {
				if (numberOfLockers > countLockers) {
					Locker newLocker = new Locker(shipLTS, capacity, constraint);
					lockersList[countLockers] = newLocker;
					countLockers++;
					return SUCCESSFUL;
				}
				return ERROR3;
			}
			return ERROR2;
		}
		return ERROR1;
	}


	/**
	 * This methods returns an array with the crew's ids.
	 * @return
	 */
	public int[] getCrewIDs() {
		return crewIDLst;
	}

	/**
	 * . This methods returns an array of the Lockers, whose length is numOfLockers
	 * @return
	 */
	public Locker[] getLockers() {
		return lockersList;
	}


	/*
    Checks if the ID is valid
     */
	private boolean isInList(int[] list, int element) {
		for (int j : list) {
			if (j == element) {
				return true;
			}
		}
		return false;
	}
}
