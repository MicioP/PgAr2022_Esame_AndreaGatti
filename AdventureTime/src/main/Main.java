package main;

import java.io.File;
import java.util.ArrayList;

import entities.*;
import gameUtils.GameHandler;
import mapElements.MapElement;
import objects.Potion;
import objects.Shield;
import objects.Weapon;
import objects.InteractiveObject;
import xmlutils.MapReader;



public class Main {

	public static final String titolo = "Menu' principale";
	public static final String voci[] = {"Muovi Player", "Satistiche", "Inventario"};
	
	

	public static void main(String[] args) {
		
		MyMenu mainMenu = new MyMenu(titolo, voci);
		
		
		
		ArrayList<ArrayList<MapElement>> map = MapReader.readMapFromXML(new File("livelli//livello1.xml"));
		
		//System.out.println(MapReader.mapToString(map));
		
		
		GameHandler g = new GameHandler(map);
		
		
		int scelta = 1;
		
		while(scelta != 0) {
			switch (mainMenu.scegli()) {
				case 1:   // Muovi il giocatore
					
					char dir = InputDati.leggiCharCheck("Insert direction: ", 'W', 'A', 'S', 'D');  // Leggi la direzione
					
					switch(g.movePlayer(dir)) {  
					
						case EMPTY_BOX:  // Il player si e' mosso su una casella vuota
							System.out.println(MapReader.mapToString(g.getCurrentMap()));
							break;
							
						case CHEST:   // Il player si e' mosso su una chest
							System.out.println(MapReader.mapToString(g.getCurrentMap()));
							if(InputDati.yesOrNo("You found a chest! Wanna check whats inside?")) {  // Chiedi se si vuole aprire la chest
								InteractiveObject chestObj = g.chest();
								if(chestObj instanceof Potion) {  // Se l'oggetto e' una pozione, chiedi se si vuole prenderla e metterla nello zaino (cosi' da poterla utilizzare dopo)
									if(InputDati.leggiChar("You found a Potion! Press E to put it in your backpack.") == 'E') {
										g.getPlayerRef().addItemToBackpack((Potion) chestObj);
									}
								}
								else if(chestObj instanceof Shield) {
									if(InputDati.leggiChar("You found a Shield! Press E to grab it.") == 'E') {
										g.getPlayerRef().setOnHand(chestObj);
									}
								}
								else if(chestObj instanceof Weapon){
									if(InputDati.leggiChar(String.format("You found a Weapon (Damage: %d)! Press E to grab it.", ((Weapon) chestObj).getDamage())) == 'E') {
										g.getPlayerRef().setOnHand(chestObj);
									}
									
								}
							}
							break;
							
						case MONSTER:
							break;
							
						case OUT_OF_BOUNDS:
							break;
					}
					
					
					
					
					
					
					break;
					
				case 2:
					break;
					
					
					
					
				case 3:  // Inventario
					
					int potionNum = g.getPlayerRef().getBackpack().size();
					System.out.printf("You have %d potions in the backpack.\n", potionNum);   // Visualizza il numero di pozioni nello zaino del player
					if(potionNum > 0) {  // Se c'e' almeno una pozione, permetti di usarla.
						if(InputDati.leggiChar("Press E to use a potion.") == 'E') {
							Potion p = g.getPlayerRef().getBackpack().get(potionNum - 1);  // Recupera la pozione 
							p.action().accept(g.getPlayerRef());   // Utilizza la pozione
							g.getPlayerRef().removeItemToBackpack(p);   // Rimuovi la pozione usata dallo zaino
						}						
					}
					break;
		
			}	
		
		}

	}
	

	
	
	
}
