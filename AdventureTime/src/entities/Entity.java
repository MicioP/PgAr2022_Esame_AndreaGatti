package entities;

import objects.InteractiveObject;


/**
 * Interfaccia che definisce i metodi che una entita' deve avere
 *
 */

public interface Entity {

	public String getName();
	
	public int getHp();
	
	public int getMaxHp();
	
	public void setHp(int value);
	
	public int getAttackDamage();
	
	public int getDefenseValue();
	
	public boolean isDead();
	
	public InteractiveObject getOnHand();
	
	public void setOnHand(InteractiveObject obj);
	
	
}
