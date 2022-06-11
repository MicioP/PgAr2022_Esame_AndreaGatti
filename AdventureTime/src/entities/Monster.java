package entities;


import java.util.ArrayList;
import java.util.Random;

import mapElements.MapElement;
import objects.Weapon;
import objects.InteractiveObject;


/**
 * Classe rappresentate un mostro.
 *
 */

public class Monster implements Entity, MapElement<Monster>{

	private static Random rand = new Random();
	private static final ArrayList<String> dijkstraPerm = permutation("dijkstra");  // ArrayList di permutazioni della stringa "dijkstra"
	private static int nameCount = 1;  // Contatore per tenere traccia delle permutazioni gia' usate
	private static int MAX_VALUE = 25;  
	private static int MIN_VALUE = 15;
	
	
	private String name;  // Nome del mostro
	private int hp;     // Vita del mostro
	private int maxHp;  // Valore massimo della vita per il mostro (Variabile da mostro a mostro)
	private int attackDamage = 5;
	private int defenseValue = 5;
	private InteractiveObject onHand;  // Arma del mostro
	

		
	public Monster() {
		this.name = dijkstraPerm.get(nameCount++);  // Assegna un nome al mostro 
		this.hp = rand.nextInt() % ((MAX_VALUE + 1 - MIN_VALUE) + MIN_VALUE);  // Genera un numero casuale compreso fra MAX_VALUE e MIN_VALUE per la vita del mostro
		this.maxHp = this.hp;
		this.onHand = new Weapon();  // Crea una nuova arma
		if(nameCount == dijkstraPerm.size()) {  // Se l'arraylist di nomi (permutazioni di "dijkstra") per i mostri e' finito, ricomincia da capo 
			nameCount = 0;
		}
	}

	


	public String getName() {
		return name;
	}
	

	public int getHp() {
		return hp;
	}

	
	public void setHp(int value) {
		this.hp += value;
	}
	
	
	/**
	 * @return true se il mostro e' morto (hp<0), false altrimenti
	 */
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
	
	
	public int getMaxHp() {
		return maxHp;
	}
	
	
	
	public void setOnHand(InteractiveObject obj) {
		this.onHand = obj;
	}
	
	
	
	public Monster getElement() {
		return this;
	}
	
	
	
	
	/**
	 * Metodo per calcolare tutte le permutazioni di una data stringa
	 */
	private static ArrayList<String> permutation(String str) {
	 	
	    ArrayList<String> perm = new ArrayList<String>();
	    
	    if(str.length() == 0) {
	    	perm.add(str);
	    	return perm;
	    }
	    else {
	    	// Esamina tutti i caratteri della str
	    	for(int i = 0; i < str.length(); i++) {
	    		// Elimina l'i-esimo carattere
	    		String substr =  str.substring(0, i) + str.substring(i + 1);
	    		// Genera tutte le permutazioni della substr con una chiamata ricorsiva
	    		ArrayList<String> substrPerm = permutation(substr);
	    		// Aggiungi il carattere escluso all'inizio di ciascuna permutazione della substr
	    		for(String s : substrPerm) {
	    			perm.add(str.charAt(i) + s);
	    		}
	    	}
	    	return perm;    	
	    }
	    
	}


	
	
	
	
	
	
}
