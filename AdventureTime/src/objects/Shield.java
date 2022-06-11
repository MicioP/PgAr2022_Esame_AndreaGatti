package objects;

import java.util.function.Consumer;

import entities.*;

public class Shield implements InteractiveObject{

	
	private static final double SHIELD_PROBABILITY = 0.35;
	private int shieldHp = 5;

	/*
	public Double getProbability() {
		return SHIELD_PROBABILITY;
	}
	*/

	public Consumer<Entity> action() {
		Consumer<Entity> action = (Entity e) -> e.setOnHand(this);
		return action;
	}
	
	
	public void decreaseShieldHp(int value) {
		this.shieldHp -= value;
	}
	
	public boolean isBroken() {
		return shieldHp <= 0 ? true : false;
	}
	

}
