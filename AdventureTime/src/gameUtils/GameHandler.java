package gameUtils;

import java.util.ArrayList;

import entities.*;
import mapElements.*;
import objects.*;


public class GameHandler {

	private int[] playerLocation; // Posizione del player [riga, colonna]
	private MapElement<Player> playerReference;
	private MapElement<Chest> tempChestReference;
	private ArrayList<ArrayList<MapElement>> currentMap;
	
	
	
	
	public GameHandler(ArrayList<ArrayList<MapElement>> map) {
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
	 * Per semplificare leggermente le dinamiche, ho deciso che un player non puo' "stare sopra" una chest
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
					if(currentMap.get(newRow).get(newColumn) instanceof EmptyBox) { // Controlla che la nuova posizione sia una casella libera
						playerLocation[0] = newRow;
						currentMap.get(newRow).set(newColumn, playerReference);    // Aggiorna la posizione sulla mappa
						return GameEvents.EMPTY_BOX;
					}
					else if(currentMap.get(newRow).get(newColumn) instanceof Chest) {  // Controlla se nuova posizione contiene una chest
						tempChestReference = (Chest) currentMap.get(newRow).get(newColumn);
						return GameEvents.CHEST;
					}
					else if(currentMap.get(newRow).get(newColumn) instanceof Monster) {  // Controlla se nella nuova posizione ci sia un mostro
						// Incontro
						return GameEvents.MONSTER;
					}
				}
				return GameEvents.OUT_OF_BOUNDS;
				
			case 'A':  // Sposta il giocatore di una casella verso sx
				newRow = playerLocation[0];  // Calcola la nuova posizione 
				newColumn = playerLocation[1] - 1;
				if(!(newColumn < 0)) {
					if(currentMap.get(newRow).get(newColumn) instanceof EmptyBox) { // Controlla che la nuova posizione sia una casella libera
						playerLocation[1] = newColumn;
						currentMap.get(newRow).set(newColumn, playerReference);   // Aggiorna la posizione sulla mappa
						return GameEvents.EMPTY_BOX;
					}
					else if(currentMap.get(newRow).get(newColumn) instanceof Chest) {  // Controlla se nuova posizione contiene una chest
						tempChestReference = (Chest) currentMap.get(newRow).get(newColumn);
						return GameEvents.CHEST;
					}
					else if(currentMap.get(newRow).get(newColumn) instanceof Monster) {  // Controlla se nella nuova posizione ci sia un mostro
						return GameEvents.MONSTER;
					}
				}
				return GameEvents.OUT_OF_BOUNDS;
				
			case 'S':  // Sposta il giocatore di una casella verso il basso
				newRow = playerLocation[0] + 1;  // Calcola la nuova posizione 
				newColumn = playerLocation[1];
				if(!(newRow > currentMap.size())) {
					if(currentMap.get(newRow).get(newColumn) instanceof EmptyBox) { // Controlla che la nuova posizione sia una casella libera
						playerLocation[0] = newRow;
						currentMap.get(newRow).set(newColumn, playerReference);  // Aggiorna la posizione sulla mappa
						return GameEvents.EMPTY_BOX;
					}
					else if(currentMap.get(newRow).get(newColumn) instanceof Chest) {  // Controlla se nuova posizione contiene una chest
						tempChestReference = (Chest) currentMap.get(newRow).get(newColumn);
						return GameEvents.CHEST;
					}
					else if(currentMap.get(newRow).get(newColumn) instanceof Monster) {  // Controlla se nella nuova posizione ci sia un mostro
						return GameEvents.MONSTER;
					}
				}
				return GameEvents.OUT_OF_BOUNDS;
				
			case 'D':  // Sposta il giocatore di una casella verso dx
				newRow = playerLocation[0];  // Calcola la nuova posizione 
				newColumn = playerLocation[1] + 1;
				if(!(newColumn > currentMap.get(newRow).size())) {
					if(currentMap.get(newRow).get(newColumn) instanceof EmptyBox) { // Controlla che la nuova posizione sia una casella libera
						playerLocation[1] = newColumn;
						currentMap.get(newRow).set(newColumn, playerReference); // Aggiorna la posizione sulla mappa
						return GameEvents.EMPTY_BOX;
					}
					else if(currentMap.get(newRow).get(newColumn) instanceof Chest) {  // Controlla se nuova posizione contiene una chest
						tempChestReference = (Chest) currentMap.get(newRow).get(newColumn); 
						currentMap.get(newRow).set(newColumn, playerReference); 
						return GameEvents.CHEST;
					}
					else if(currentMap.get(newRow).get(newColumn) instanceof Monster) {  // Controlla se nella nuova posizione ci sia un mostro
						return GameEvents.MONSTER;
					}
				}
				return GameEvents.OUT_OF_BOUNDS;
				
		}
		
		return GameEvents.OUT_OF_BOUNDS;
			
	}
	
	
	
	public InteractiveObject chest() {	
		return tempChestReference.getElement().getObj();
	}
	
	
	
	
	public void fight() {
		
	}
	
	
	
	
	
	
	
	
	
	
}
