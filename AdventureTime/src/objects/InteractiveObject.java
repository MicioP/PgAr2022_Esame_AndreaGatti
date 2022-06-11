package objects;

import java.util.function.Consumer;

import entities.*;


public interface InteractiveObject {

	//public Double getProbability();
	
	public Consumer<Entity> action();
	
	
}
