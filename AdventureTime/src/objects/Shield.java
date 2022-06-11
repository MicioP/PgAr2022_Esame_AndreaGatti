package objects;

import java.util.function.Consumer;

import entities.*;

/**
 * Classe che rappresenta uno scudo
 * @author Andrea
 *
 */

public class Shield implements InteractiveObject{


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
	
	
	public int getHp() {
		return shieldHp;
	}
	
	public void setHp(int value) {
		this.shieldHp += value;
	}
	
	public boolean isBroken() {
		return shieldHp <= 0 ? true : false;
	}
	

}
