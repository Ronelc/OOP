
/**
 * this is a class that create all the spaceship in the game
 */
public class SpaceShipFactory {
	/**
	 * create the spaceships and return array of spaceships
	 * @param args the name of the spaceships that need to build
	 * @return array of spaceships
	 */


	public static SpaceShip[] createSpaceShips(String[] args) {

		final String HUMAN = "h";
		final String RUNNER = "r";
		final String BASHER = "b";
		final String AGGRESSIVE = "a";
		final String DRUNKARD = "d";
		final String SPECIAL = "s";
		int arrayLength = args.length;


		SpaceShip[] spaceShipsArray = new SpaceShip[arrayLength];
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals(HUMAN)) {
				spaceShipsArray[i] = new HumanSpaceShip();
			}
			if (args[i].equals(RUNNER)) {
				spaceShipsArray[i] = new RunnerSpaceShip();
			}
			if (args[i].equals(BASHER)) {
				spaceShipsArray[i] = new BasherSpaceShip();
			}
			if (args[i].equals(AGGRESSIVE)) {
				spaceShipsArray[i] = new AggressiveSpaceShip();
			}
			if (args[i].equals(DRUNKARD)) {
				spaceShipsArray[i] = new DrunkardSpaceShip();
			}
			if (args[i].equals(SPECIAL)) {
				spaceShipsArray[i] = new SpecialSpaceShip();
			}
		}
		return spaceShipsArray;
	}
}
