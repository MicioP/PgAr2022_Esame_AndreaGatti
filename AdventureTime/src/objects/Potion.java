package objects;

import java.util.function.Consumer;

import entities.*;

public class Potion implements InteractiveObject{

	
	private static final double POTION_PROBABILITY = 0.25;
	private static final double PERCENTAGE = 0.50;

	/*
	public Double getProbability() {
		return POTION_PROBABILITY;
	}
	 */

	
	public Consumer<Entity> action() {
		Consumer<Entity> action = (Entity e) -> e.setHp((int) Math.floor(e.getMaxHp()*PERCENTAGE));
		return action;
	}

}
