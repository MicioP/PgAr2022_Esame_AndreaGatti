package entities;

import java.util.ArrayList;

import mapElements.MapElement;
import objects.*;

public class Player implements Entity, MapElement<Player>{

	private static final int MAX_HP = 20;
	
	private String name;
	private int hp = 20;
	private int attackDamage = 5;
	private int defenseValue = 5;
	private InteractiveObject onHand;
	private ArrayList<Potion> backpack = new ArrayList<Potion>();

		
	public Player(String name) {
		this.name = name;
	}



	public String getName() {
		return name;
	}
	

	
	public int getHp() {
		return hp;
	}
	
	
	
	public int getMaxHp() {
		return MAX_HP;
	}

	
	public void setHp(int value) {
		this.hp += value;
		if(this.hp > MAX_HP) {
			this.hp = MAX_HP;
		}
	}
	
	
	public boolean isDead() {
		return this.hp <= 0 ? true : false;
	}
	
	
	public int getAttackDamage() {
		return attackDamage;
	}

	
	public int getDefenseValue() {
		return defenseValue;
	}

	
	public InteractiveObject getOnHand() {
		return onHand;
	}
	
	
	public void setOnHand(InteractiveObject obj) {
		this.onHand = obj;
	}
	
	
	public Player getElement() {
		return this;
	}
	
	
	public ArrayList<Potion> getBackpack(){
		return backpack;
	}
	
	
	public void addItemToBackpack(Potion obj) {
		backpack.add(obj);
	}
	
	
	public void removeItemToBackpack(Potion obj) {
		backpack.remove(obj);
	}


}
