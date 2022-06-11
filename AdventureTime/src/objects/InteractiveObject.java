package objects;

import java.util.function.Consumer;

import entities.*;

/**
 * Interfaccia che rappresenta un oggetto con cui il player puo' interagire
 *
 */

public interface InteractiveObject {

	//public Double getProbability();
	
	public Consumer<Entity> action();
	
	
}
