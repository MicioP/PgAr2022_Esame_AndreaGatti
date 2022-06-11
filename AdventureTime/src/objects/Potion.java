package objects;

import java.util.function.Consumer;

import entities.*;

/**
 * Classe che rappresenta una pozione
 *
 */

public class Potion implements InteractiveObject{

	private static final double PERCENTAGE = 0.50;

	/*
	public Double getProbability() {
		return POTION_PROBABILITY;
	}
	 */

	
	public Consumer<Entity> action() {
		Consumer<Entity> action = (Entity e) -> e.setHp((int) Math.floor(e.getMaxHp()*PERCENTAGE));  // Funzione che data una entita', aumenta di (50%*vita massima) la susa vita
		return action;
	}

}
