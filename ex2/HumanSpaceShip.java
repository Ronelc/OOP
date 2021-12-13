import oop.ex2.GameGUI;

import java.awt.*;

/**
 * this class create the human space ship
 */
public class HumanSpaceShip extends SpaceShip {

	/** The direction the ship need toward to */
	private int Direct;


	/**
	 * function that do the action for the human spaceShip depend on the pressing on the arrows
	 * @param game the game object to which this ship belongs.
	 */
	@Override
	public void doAction(SpaceWars game) {
		CountRounds++;
		if (game.getGUI().isTeleportPressed()) {
			teleport();
		}
		moves(game);
		if (game.getGUI().isShieldsPressed()) {
			shieldOn();
		}
		if (game.getGUI().isShotPressed()) {
			fire(game);
		}
		addEnergy();
		Shield = false;
	}


	private void moves(SpaceWars game) {
		if (game.getGUI().isRightPressed() && game.getGUI().isLeftPressed()) {
			Direct = NODIRECT;
		} else if (game.getGUI().isRightPressed()) {
			Direct = RIGHT;
		} else if (game.getGUI().isLeftPressed()) {
			Direct = LEFT;
		} else if (game.getGUI().isUpPressed()) {
			Accel = true;
			Direct = NODIRECT;
		}
		getPhysics().move(Accel, Direct);
		Accel = false;
	}


	@Override
	public Image getImage() {
		if (Shield) {
			return GameGUI.SPACESHIP_IMAGE;
		}
		return GameGUI.SPACESHIP_IMAGE_SHIELD;
	}

}
