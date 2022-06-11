package gameUtils;

import java.util.ArrayList;
import java.util.Random;

import entities.*;
import mapElements.*;
import objects.*;


public class MapHandler {

	private MapElement<Player> playerReference;  // Riferimento al player
	private int[] playerLocation; // Posizione del player [riga, colonna]
	private MapElement<Chest> tempChestReference; // Riferimento temporaneo per le chest
	private int[] tempChestLocation; // Posizione della chest a cui fa riferimento tempChestReference
	private MapElement<Monster> tempMonsterReference; // Riferimento temporaneo per i mostri
	private int[] tempMonsterLocation; // Posizione del mostro a cui fa riferimento tempMonsterReference
	private ArrayList<ArrayList<MapElement>> currentMap;
	
	
	
	
	public MapHandler(ArrayList<ArrayList<MapElement>> map) {
		this.currentMap = map;
		
		EXTERNAL_LOOP:    // Chiedo venia per l'uso dell'etichetta  
		for(int row = 0; row < map.size(); row++) {			// Individua il player nella mappa, si presuppone ci sia un unico giocatore
			for(int column = 0; column < map.get(row).size(); column++) {
				if(map.get(row).get(column) instanceof Player) {
					playerReference = (Player) map.get(row).get(column);
					playerLocation = new int[] {row, column};
					break EXTERNAL_LOOP;
				}
			}
		}
		
	}
	
	
	
	public ArrayList<ArrayList<MapElement>> getCurrentMap(){
		return currentMap;
	}
	
	
	
	public Player getPlayerRef() {
		return playerReference.getElement();
	}
	
	
	
	/**
	 * @param direction
	 * @return
	 */
	
	public GameEvents movePlayer(char direction) {
			
		int newRow = 0, newColumn = 0;
		
		switch (direction) {
		
			case 'W': // Sposta il giocatore di una casella verso l'alto
				newRow = playerLocation[0] - 1;  // Calcola la nuova posizione 
				newColumn = playerLocation[1];
				if(!(newRow < 0)) {  // Controlla che la nuova posizione non vada al di fuori della mappa
					return getEvent(newRow, newColumn);
				}
				return GameEvents.OUT_OF_BOUNDS;
				
			case 'A':  // Sposta il giocatore di una casella verso sx
				newRow = playerLocation[0];  // Calcola la nuova posizione 
				newColumn = playerLocation[1] - 1;
				if(!(newColumn < 0)) {
					return getEvent(newRow, newColumn);
				}
				return GameEvents.OUT_OF_BOUNDS;
				
			case 'S':  // Sposta il giocatore di una casella verso il basso
				newRow = playerLocation[0] + 1;  // Calcola la nuova posizione 
				newColumn = playerLocation[1];
				if(!(newRow > currentMap.size() - 1)) {
					return getEvent(newRow, newColumn);
				}
				return GameEvents.OUT_OF_BOUNDS;
				
			case 'D':  // Sposta il giocatore di una casella verso dx
				newRow = playerLocation[0];  // Calcola la nuova posizione 
				newColumn = playerLocation[1] + 1;
				if(!(newColumn > currentMap.get(newRow).size() - 1)) {
					return getEvent(newRow, newColumn);
				}
				return GameEvents.OUT_OF_BOUNDS;
				
		}
		
		return GameEvents.OUT_OF_BOUNDS;
			
	}
	
	
	
	
	
	private GameEvents getEvent(int newRow, int newColumn) {
		
		if(currentMap.get(newRow).get(newColumn) instanceof EmptyBox) { // Controlla che la nuova posizione sia una casella libera
			currentMap.get(playerLocation[0]).set(playerLocation[1], new EmptyBox());    // Sostiuisci la vecchia posizione del player con una casella vuota
			playerLocation[0] = newRow;  
			playerLocation[1] = newColumn;  
			currentMap.get(newRow).set(newColumn, playerReference);    // Aggiorna la posizione del player sulla mappa
			return GameEvents.EMPTY_BOX;
		}
		else if(currentMap.get(newRow).get(newColumn) instanceof Chest) {  // Controlla se nuova posizione contiene una chest
			tempChestReference = (Chest) currentMap.get(newRow).get(newColumn);
			tempChestLocation = new int[] {newRow, newColumn};
			return GameEvents.CHEST;
		}
		else if(currentMap.get(newRow).get(newColumn) instanceof Monster) {  // Controlla se nella nuova posizione ci sia un mostro
			tempMonsterReference = (Monster) currentMap.get(newRow).get(newColumn);
			tempMonsterLocation = new int[] {newRow, newColumn};
			return GameEvents.MONSTER;
		}
		
		return GameEvents.OUT_OF_BOUNDS;
		
	}
	
	
	
	
	
	
	public InteractiveObject chest() {
		currentMap.get(playerLocation[0]).set(playerLocation[1], new EmptyBox());    // Sostiuisci la vecchia posizione del player con una casella vuota
		playerLocation[0] = tempChestLocation[0];
		playerLocation[1] = tempChestLocation[1];
		currentMap.get(playerLocation[0]).set(playerLocation[1], playerReference);    // Dopo che la chest e' stata aperta, aggiorna la posizione del player sulla mappa
		return tempChestReference.getElement().getObj();
	}
	
	
	
	
	
	public boolean fight(StringBuffer logger) {

		Random rand = new Random();
		double mod = rand.nextDouble() <= 0.075 ? 1.5 : 1;  // Il modificatore vale 1.5 con una probabilita' dello 7,5%
		
		int playerWeaponPower = 1;
		int monsterWeaponPower;
		Shield playerShield = null;
		
		if(playerReference.getElement().getOnHand() instanceof Weapon) {
			playerWeaponPower = ((Weapon) playerReference.getElement().getOnHand()).getDamage(); 
		}
		else if(playerReference.getElement().getOnHand() instanceof Shield) {
			playerShield = (Shield) playerReference.getElement().getOnHand();
		}
		
		monsterWeaponPower = ((Weapon) tempMonsterReference.getElement().getOnHand()).getDamage();
		
		
		int playerDamage = (2*playerWeaponPower*playerReference.getElement().getAttackDamage())/(25*playerReference.getElement().getDefenseValue());
		
		
		int monsterDamage = (2*monsterWeaponPower*tempMonsterReference.getElement().getAttackDamage())/(25*tempMonsterReference.getElement().getDefenseValue());
		monsterDamage = (int) Math.floor(monsterDamage * mod);
		
		playerReference.getElement().isDead();
		tempMonsterReference.getElement().isDead();
		
		while(!playerReference.getElement().isDead() & !tempMonsterReference.getElement().isDead()) {
			tempMonsterReference.getElement().setHp(0 - playerDamage);
			logger.append(String.format("%s has inflicted %d of damage to %s, who now has %d HP.\n", playerReference.getElement().getName(), playerDamage, tempMonsterReference.getElement().getName(), tempMonsterReference.getElement().getHp()));
			if(playerShield != null) {  // Se il player ha uno scudo, togli prima i danni ad esso
				if(!playerShield.isBroken()) {
					playerShield.setHp(playerShield.getHp() - monsterDamage);
					if(playerShield.getHp() < 0) {  // Se gli hp dello scudo sono minori di 0, significa che il mostro (oltre ad avere rotto lo scudo) ha inflitto danno anche al player
						playerReference.getElement().setHp(playerShield.getHp());
					}
				}
				if(playerShield.isBroken()) {
					playerReference.getElement().setOnHand(null);
					logger.append(String.format("%s has broken %d's shield!\n", tempMonsterReference.getElement().getName(), playerReference.getElement().getName()));
				}
			}
			else {				
				playerReference.getElement().setHp(0 - monsterDamage);
				logger.append(String.format("%s has inflicted %d of damage to %s, who now has %d HP.\n", tempMonsterReference.getElement().getName(), monsterDamage, playerReference.getElement().getName(), playerReference.getElement().getHp()));		
			}
		}
		
		
		if(tempMonsterReference.getElement().isDead()) {
			currentMap.get(playerLocation[0]).set(playerLocation[1], new EmptyBox());    // Sostiuisci la vecchia posizione del player con una casella vuota
			playerLocation[0] = tempMonsterLocation[0];
			playerLocation[1] = tempMonsterLocation[1];
			currentMap.get(playerLocation[0]).set(playerLocation[1], playerReference);    // Dopo che il mostro e' stato sconfitto, aggiorna la posizione del player sulla mappa
			return true; // Ritorna true se il player ha sconfitto il mostro
		}
		
		return false; // Ritorna false se il mostro ha sconfitto il player
		
		
	}
		
	
}
;