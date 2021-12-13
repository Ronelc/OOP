
/**
 * this class create the basher space ship
 */
public class BasherSpaceShip extends SpaceShip {

	/** */
	final double DISTANCELIMIT = 0.19;

	/** The direction the ship need toward to */
	int Direct;

	/**
	 * function that do the action for the human spaceShip scasing after the closest spaceShip, and try to
	 * colide with him after the make his shield on
	 * @param game the game object to which this ship belongs.
	 */
	@Override
	public void doAction(SpaceWars game) {
		findTheClosestShip(game);
		Accel = true;
		this.Direct = NODIRECT;
		if (Math.abs(Distance) <= this.DISTANCELIMIT) {
			shieldOn();
		}
		if (Angle > 0) {
			this.Direct = LEFT;
		}
		if (Angle < 0) {
			this.Direct = RIGHT;
		}
		getPhysics().move(Accel, this.Direct);
		Shield = false;
		addEnergy();
	}
}

