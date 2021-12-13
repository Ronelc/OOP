/**
 * this class create the aggressive space ship
 */
public class AggressiveSpaceShip extends SpaceShip {

	/** distance from other spaceShips */
	final double ANGLELIMIT = 0.21;

	/** The direction the ship need toward to */
	int Direct;

	/**
	 * function that do the action for the aggressive depend on if the aggressive is close
	 * enough to the auther space ships then turn to them, and if close enough firing to them.
	 * @param game the game object to which this ship belongs.
	 */
	@Override
	public void doAction(SpaceWars game) {
		findTheClosestShip(game);
		CountRounds++;
		Accel = true;
		Direct = NODIRECT;
		if (Angle > 0) {
			Direct = LEFT;
		}
		if (Angle < 0) {
			Direct = RIGHT;
		}
		if (Math.abs(Angle) < ANGLELIMIT) {
			fire(game);
		}
		getPhysics().move(Accel, Direct);
		addEnergy();
	}
}
