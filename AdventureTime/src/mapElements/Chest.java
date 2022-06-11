package mapElements;

import java.util.Random;

import objects.Potion;
import objects.Shield;
import objects.Weapon;
import objects.InteractiveObject;

public class Chest implements MapElement<Chest>{

	private static final double POTION_PROBABILITY = 0.25;
	private static final double SHIELD_PROBABILITY = 0.35;
	private static final double WEAPON_PROBABILITY = 0.40;
	private static Random rand = new Random();
	
	private InteractiveObject obj;
	
	public Chest() {
		double randDouble = rand.nextDouble();
		
		if(randDouble >= 0 & randDouble <= POTION_PROBABILITY) {
			this.obj = new Potion();
		}
		else if(randDouble > POTION_PROBABILITY & randDouble <= (POTION_PROBABILITY + SHIELD_PROBABILITY)) {
			this.obj = new Shield();
		}
		else if(randDouble > (POTION_PROBABILITY + SHIELD_PROBABILITY) & randDouble <= (POTION_PROBABILITY + SHIELD_PROBABILITY + WEAPON_PROBABILITY)) {
			this.obj = new Weapon();
		}
		
	}
	
	
	public InteractiveObject getObj() {
		return obj;
	}


	public Chest getElement() {
		return this;
	}
	
	
	
	
}
