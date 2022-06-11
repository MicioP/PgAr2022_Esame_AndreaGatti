package objects;

import java.util.Random;
import java.util.function.Consumer;

import entities.*;

public class Weapon implements InteractiveObject{
	
	private static final double WEAPON_PROBABILITY = 0.40;
	private static final int MAX_VALUE = 55;
	private static final int MIN_VALUE = 35;
	private static final String[] weaponNames = {"Excalibur", "Durlindana", "Tizona", "Attila", "Dhu", "Gramr", "Marakumo"};
	private static int nameCount;
	private static Random rand = new Random();
	
	private int damage;
	private String name;
	
	// Aggiungi nome!
	
	public Weapon() {
		this.damage = Math.abs(rand.nextInt() % ((MAX_VALUE + 1 - MIN_VALUE) + MIN_VALUE));
		this.name = weaponNames[nameCount++];
		if(nameCount == weaponNames.length) {
			nameCount = 0;
		}
		
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
	
	public String getName() {
		return name;
	}

}
