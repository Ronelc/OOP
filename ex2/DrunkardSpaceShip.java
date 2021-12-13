import java.util.Random;

/**
 * this class create the drunkard space ship
 */
public class DrunkardSpaceShip extends SpaceShip {

	/** Count round for that ship only */
	private int CountDrunk = 0;

	/** The direction the ship need toward to */
	private int Direct;


	Random rand0 = new Random();


	/**
	 * function that do the action for the drunkard spaceShip depend on random, and depend on if the drunkard is close
	 * enough to the auther space ships
	 * @param game the game object to which this ship belongs.
	 */
	@Override
	public void doAction(SpaceWars game) {
		CountRounds++;
		CountDrunk++;
		Accel = true;
		if (CountDrunk == 70) {
			random();
		}
		if (CountDrunk%20 == 0){
		fire(game);}
		addEnergy();
		getPhysics().move(Accel, Direct);
	}

	/**
	 *  making random and decide if turn right or left
	 */
	private void random() {
		int i;
		i = rand0.nextInt(3);
		int[]  list = {-1,0,1};
		Direct = list[i];
		getPhysics().move(Accel, Direct);
		CountDrunk = 0;
	}
}
