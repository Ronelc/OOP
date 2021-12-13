/**
 * this class create the runner space ship
 */
public class RunnerSpaceShip extends SpaceShip {

	/** minimal distance from other spaceShips */
	final double DISTANCELIMIT = 0.25;

	/** distance  of angle from other spaceShips */
	final double ANGLELIMIT = 0.23;

	/** The direction the ship need toward to */
	int Direct;


	/**
	 * function that do the action for the runner spaceShip depend on if the runner is close * enough to the
	 * auther space ships then run from them, and if close too much do teleprt.
	 * @param game the game object to which this ship belongs.
	 */
	@Override
	public void doAction(SpaceWars game) {
		findTheClosestShip(game);
		Accel = true;
		Direct = NODIRECT;
		addEnergy();
		if (Angle < ANGLELIMIT && Distance < DISTANCELIMIT) {
			teleport();
		}else if (Angle < 0) {
			Direct = LEFT;
		}else if (Angle > 0) {
			Direct = RIGHT;
		}
		getPhysics().move(Accel, Direct);
	}
}
