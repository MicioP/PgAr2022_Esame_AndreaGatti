package gameUtils;

import main.InputDati;
import main.MyMenu;
import objects.InteractiveObject;
import objects.Potion;
import objects.Shield;
import objects.Weapon;
import xmlutils.MapReader;

/**
 * Classe con un metodo statico per gestire una partita
 *
 */


public class GameHandler {

	public static final String title = "Main Menu'";
	public static final String options[] = {"Move Player", "Print Log", "Inventory"};
	
	
	/**
	 * Metodo static per la gestione di una partita.
	 * @param g Oggetto di tipo MapHandler, contente la mappa del gioco e i metodi per agire su di essa 
	 */
	public static void gameHandler(MapHandler g) {
		
		MyMenu mainMenu = new MyMenu(title, options);  // Menu Principale
		StringBuffer logger = new StringBuffer(); // StringBuffer per i log
		
		int scelta = 1;
				
		while(scelta != 0) {
			System.out.println(MapReader.mapToString(g.getCurrentMap())); // Stampa la mappa
			switch (mainMenu.scegli()) { 
				case 1:   // Muovi il giocatore
					
					char dir = InputDati.leggiCharCheck("Insert direction: ", 'W', 'A', 'S', 'D');  // Leggi la direzione
					
					switch(g.movePlayer(dir)) {    // Muovi il giocatore nella direzione letta e gestisci l'evento restituito
					
						case EMPTY_BOX:  // Il player si e' mosso su una casella vuota, in questo caso non serve fare nulla di particolare 
							break;
							
						case CHEST:   // Il player si e' mosso su una chest
							if(InputDati.yesOrNo("You found a chest! Wanna check whats inside?")) {  // Chiedi se si vuole aprire la chest
								InteractiveObject chestObj = g.chest();  // Recupera il riferimento dell'oggetto contenuto nella chest
								if(chestObj instanceof Potion) {  // Se l'oggetto e' una pozione, chiedi se si vuole prenderla e metterla nello zaino (cosi' da poterla utilizzare dopo)
									if(InputDati.leggiChar("You found a Potion! Press E to put it in your backpack.") == 'E') {
										logger.append(String.format("%s picked up a potion.\n", g.getPlayerRef().getName()));
										g.getPlayerRef().addItemToBackpack((Potion) chestObj);
									}
								}
								else if(chestObj instanceof Shield) {  // Se l'oggetto e' uno scudo, chiedi se si vuole raccoglierlo
									if(InputDati.leggiChar("You found a Shield! Press E to grab it.") == 'E') {
										logger.append(String.format("%s picked up a shield.\n", g.getPlayerRef().getName()));
										chestObj.action().accept(g.getPlayerRef());
									}
								}
								else if(chestObj instanceof Weapon){   // Se l'oggetto e' un'arma, chiedi se si vuole raccoglierlo
									if(InputDati.leggiChar(String.format("You found a Weapon %s (Damage: %d)! Press E to grab it.", ((Weapon) chestObj).getName() ,((Weapon) chestObj).getDamage())) == 'E') {
										logger.append(String.format("%s picked up a weapon named %s.\n", g.getPlayerRef().getName(), ((Weapon) chestObj).getName()));
										chestObj.action().accept(g.getPlayerRef());
									}
									
								}
							}
							break;
							
						case MONSTER:  // Il player ha incontrato un mostro
							boolean isPlayerAlive = g.fight(logger);   // Fai combattere il player e il mostro
							// Se la funzione g.fight() restituisce true significa che il player ha battuto il mostro 
							if(isPlayerAlive) {
								logger.append(String.format("%s has beated a monster!\n", g.getPlayerRef().getName()));
							}
							else {
								System.out.printf("%s IS DEAD!\n", g.getPlayerRef().getName());  // Nel caso in cui il mostro abbia sconfitto il player, stampa una stringa di avviso e esci dal gioco
								if(InputDati.yesOrNo("Do you want to see game logs?")) {  // Chiedi se si vogliono visualizzare i log 
									System.out.println(logger);
								}
								return;
							}
							break;
							
						case OUT_OF_BOUNDS:  // Il player ha selezionato una direzione invalida 
							System.out.println("You can't go outside the map!");
							break;
					}
					
					
					break;
					
					
				case 2:  // Stampa i log raccolti finora
					System.out.println(logger);
					break;
					
					
					
					
				case 3:  // Stampa il contenuto dello zaino del giocatore
					
					int potionNum = g.getPlayerRef().getBackpack().size();
					System.out.printf("You have %d potions in the backpack.\n", potionNum);   // Visualizza il numero di pozioni nello zaino del player
					if(potionNum > 0) {  // Se c'e' almeno una pozione, permetti di usarla.
						if(InputDati.leggiChar("Press E to use a potion.") == 'E') {
							Potion p = g.getPlayerRef().getBackpack().get(potionNum - 1);  // Recupera la pozione 
							p.action().accept(g.getPlayerRef());   // Utilizza la pozione
							g.getPlayerRef().removeItemToBackpack(p);   // Rimuovi la pozione usata dallo zaino
							logger.append(String.format("%d used a potion.\n", g.getPlayerRef().getName()));
						}						
					}
					break;
		
			}	
		
		}
		
		return;
				
	}
	
	
	
}
