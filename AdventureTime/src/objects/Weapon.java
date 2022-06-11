package objects;

import java.util.Random;
import java.util.function.Consumer;

import entities.*;

public class Weapon implements InteractiveObject{
	
	private static final double WEAPON_PROBABILITY = 0.40;
	private static final int MAX_VALUE = 55;
	private static final int MIN_VALUE = 35;
	private static Random rand = new Random();
	
	private int damage;
	
	// Aggiungi nome!
	
	public Weapon() {
		this.damage = rand.nextInt() % ((MAX_VALUE + 1 - MIN_VALUE) + MIN_VALUE);
	}
	
	/*
	public Double getProbability() {
		return WEAPON_PROBABILITY;
	}
	*/


	public Consumer<Entity> action() {
		Consumer<Entity> action = (Entity e) -> e.setOnHand(this);
		return action;
	}
	
	
	public int getDamage() {
		return damage;
	}

}
