/**
 * this class create the special space ship
 */
public class SpecialSpaceShip extends SpaceShip {

	/** minimal distance from other spaceShips */
	final double DISTANCELIMIT = 0.25;

	/** distance  of angle from other spaceShips */
	final double ANGLELIMIT = 0.15;

	/** The direction the ship need toward to */
	int Direct;

	/** Count round for that ship only */
	private int CountSpecial = 0;

	/**
	 * function that do the action for the special spaceShip depend on random to decide if to do teleport,
	 * and depend if the space ship is close enough to the auther spaceships
	 * @param game the game object to which this ship belongs.
	 */
	@Override
	public void doAction(SpaceWars game) {
		CountRounds++;
		CountSpecial++;
		Accel = true;
		Direct = NODIRECT;
		findTheClosestShip(game);
		if (CountSpecial == 50000) {
			doTeleport();
		}
		if (Angle > 0) {
			Direct = LEFT;
		}
		if (Angle < 0) {
			Direct = RIGHT;
		}
		if (Angle < ANGLELIMIT && Distance < DISTANCELIMIT) {
			fire(game);
			shieldOn();
		}
		getPhysics().move(Accel, Direct);
		addEnergy();
		Shield = false;
	}

	private void doTeleport() {
		MaximalEnergy = STARTMAXENERGY;
		if (CurrentEnergy < STARTCURRENTENERGY) {
			CurrentEnergy = STARTCURRENTENERGY;
		}
		teleport();
		CountSpecial = 0;
		MaximalEnergy = STARTMAXENERGY;
		CurrentEnergy = STARTCURRENTENERGY;
	}

}
