package main;

import java.io.File;
import java.util.ArrayList;

import entities.*;
import gameUtils.GameEvents;
import gameUtils.GameHandler;
import gameUtils.MapHandler;
import mapElements.MapElement;
import objects.Potion;
import objects.Shield;
import objects.Weapon;
import objects.InteractiveObject;
import xmlutils.MapReader;



public class Main {

	public static void main(String[] args) {
		

		String playerName = InputDati.leggiStringa("Insert player name: ");
		
		ArrayList<ArrayList<MapElement>> map = MapReader.readMapFromXML(new File("livelli//livello1.xml"), playerName);
		
		//System.out.println(MapReader.mapToString(map));
		
		MapHandler g = new MapHandler(map);
		
		GameHandler.gameHandler(g);
		

	}
	

	
	
	
}
