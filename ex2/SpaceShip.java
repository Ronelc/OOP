import java.awt.Image;
import oop.ex2.*;


/**
 * The API spaceships need to implement for the SpaceWars game. It is your decision whether SpaceShip.java
 * will be an interface, an abstract class, a base class for the other spaceships or any other option you will
 * choose.
 * @author oop
 */
public abstract class SpaceShip {

	/** object of spaceship*/
	SpaceShipPhysics Physics;

	/** health in the start of the game */
	private final int STARTHEALTH = 22;

	/** max energy in the start of the game */
	final int STARTMAXENERGY = 210;

	/** energy in the start of the game */
	final int STARTCURRENTENERGY = 190;

	/** the number of round at the start */
	private final int STARTCOUNTROUNDS = 0;

	/**  the healthy price of getting shoot */
	private final int REDUCEHEALTH = 1;

	/** The price of collection with other ship */
	final int RAISENERGY = 18;

	/**  the price of getting shoot or hitting by space ship*/
	private final int REDUCEENERGY = 10;

	/** */
	final int CHARGEENERGY = 1;

	/** the price of shooting */
	final int SHOTREDUCE = 19;

	/** the number of rounds that the ship can't shoot*/
	final int ROUNDSTOSHOT = 7;

	/** the price of doing teleport */
	final int TELEPORTREDUCE = 140;

	/** the price of doing shield on */
	final int SHIELDREDUCE = 3;

	/**  turn left */
	protected final int RIGHT = -1;

	/** turn right */
	protected final int LEFT = 1;

	/** straight */
	protected final int NODIRECT = 0;

	/** default shield */
	boolean Shield = false;

	/** default max energy */
	int MaximalEnergy;

	/** default current energy*/
	int CurrentEnergy;

	/** default health*/
	int HealthLevel;

	/** counter that count the rounds of the game */
	int CountRounds;

	/** Check if the ship need to accel*/
	protected boolean Accel = false;

	/** The distance from the closest ship */
	protected double Distance;

	/** The angle from the closest ship */
	protected double Angle;


	public SpaceShip() {
		Physics = new SpaceShipPhysics();
		HealthLevel = STARTHEALTH;
		MaximalEnergy = STARTMAXENERGY;
		CurrentEnergy = STARTCURRENTENERGY;
		CountRounds = STARTCOUNTROUNDS;
	}

	/**
	 * Does the actions of this ship for this round. This is called once per round by the SpaceWars game
	 * driver.
	 * @param game the game object to which this ship belongs.
	 */
	public void doAction(SpaceWars game) {
	}

	/**
	 * This method is called every time a collision with this ship occurs
	 */
	public void collidedWithAnotherShip() {
		if (Shield) {
			MaximalEnergy -= REDUCEENERGY;
			HealthLevel -= REDUCEHEALTH;
			MaximalEnergy = Math.max(0, MaximalEnergy);
			HealthLevel = Math.max(0, HealthLevel);
			CurrentEnergy = Math.min(MaximalEnergy, CurrentEnergy);
		} else {
			MaximalEnergy += RAISENERGY;
			CurrentEnergy += RAISENERGY;
		}
	}


	/**
	 * This method is called whenever a ship has died. It resets the ship's attributes, and starts it
	 * at a new
	 * random position.
	 */
	public void reset() {
		this.Physics = new SpaceShipPhysics();
		HealthLevel = STARTHEALTH;
		MaximalEnergy = STARTMAXENERGY;
		CurrentEnergy = STARTCURRENTENERGY;
		CountRounds = STARTCOUNTROUNDS;
	}

	/**
	 * Checks if this ship is dead.
	 * @return true if the ship is dead. false otherwise.
	 */
	public boolean isDead() {
		return HealthLevel == 0;
	}

	/**
	 * Gets the physics object that controls this ship.
	 * @return the physics object that controls the ship.
	 */
	public SpaceShipPhysics getPhysics() {
		return Physics;
	}

	/**
	 * This method is called by the SpaceWars game object when ever this ship gets hit by a shot.
	 */
	public void gotHit() {
		if (Shield) {
			MaximalEnergy -= REDUCEENERGY;
			HealthLevel -= REDUCEHEALTH;
			MaximalEnergy = Math.max(0, MaximalEnergy);
			HealthLevel = Math.max(0, HealthLevel);
			CurrentEnergy = Math.min(MaximalEnergy, CurrentEnergy);
		}
	}


	/**
	 * Gets the image of this ship. This method should return the image of the ship with or without the
	 * shield. This will be displayed on the GUI at the end of the round.
	 * @return the image of this ship.
	 */
	public Image getImage() {
		if (Shield) {
			return GameGUI.ENEMY_SPACESHIP_IMAGE;
		}
		return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
	}

	/**
	 * Attempts to fire a shot.
	 * @param game the game object.
	 */
	public void fire(SpaceWars game) {
		if (CurrentEnergy >= SHOTREDUCE && CountRounds > ROUNDSTOSHOT) {
			CurrentEnergy -= SHOTREDUCE;
			CountRounds = STARTCOUNTROUNDS;
			game.addShot(Physics);
		}
	}

	/**
	 * Attempts to turn on the shield.
	 */
	public void shieldOn() {
		if (Shield){return;}
		if (CurrentEnergy >= SHIELDREDUCE) {
			CurrentEnergy -= SHIELDREDUCE;
			Shield = true;
		}
	}

	/**
	 * Attempts to teleport.
	 */
	public void teleport() {
		if (CurrentEnergy >= TELEPORTREDUCE) {
			CurrentEnergy -= TELEPORTREDUCE;
			Physics = new SpaceShipPhysics();
		}
	}

	/**
	 * add more energy if allowed.
	 */
	protected void addEnergy() {
		if (CurrentEnergy < MaximalEnergy) {
			CurrentEnergy += CHARGEENERGY;
		}
	}

	/**
	 * find the closest ship to this ship, distance and angle.
	 */
	protected void findTheClosestShip(SpaceWars game) {
		SpaceShip CloseShip = game.getClosestShipTo(this);
		Angle = Physics.angleTo(CloseShip.getPhysics());
		Distance = Physics.distanceFrom(CloseShip.getPhysics());
	}
}

