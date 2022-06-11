package xmlutils;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.*;
import mapElements.Chest;
import mapElements.EmptyBox;
import mapElements.MapElement;
import mapElements.Wall;
import xmlutils.XMLReader;


/**
 * Classe per leggere una mappa da file xml
 * 
 *
 */

public class MapReader {

	private static Pattern contentPattern = Pattern.compile("Content of Tag : (.)");
	
	
	/**
	 * @param f File xml della mappa
	 * @param playerName Nome del giocatore (Per creare l'oggetto Player)
	 * @return Matrice di MapElement (ArrayList di ArrayList<MapElement>)
	 */
	
	public static ArrayList<ArrayList<MapElement>> readMapFromXML(File f, String playerName) {
		
		ArrayList< ArrayList<MapElement> > map = new ArrayList< ArrayList<MapElement> >();
		//MapElement<Entity>[][] map = new MapElement<Entity>[10][10];
		
		//matrixOne.get(row).get(column).add(value)
		
		XMLReader r = new XMLReader(f);
		Matcher matcher;
		//int row = 0, column = 0;
		
		String last = r.readNext();
		
		while(!last.contains("closed")) {
			
			if(last.contains("Tag : row") & !last.contains("End")) {
				//ArrayList<MapElement> rowList = map.get(row);
				ArrayList<MapElement> rowList = new ArrayList<MapElement>();
				last = r.readNext();
				
				while(!last.contains("Tag : row")) {
					
					if(last.contains("Content")) {
						matcher = contentPattern.matcher(last);
						if(matcher.find()) {
							//System.out.println(matcher.group(1));
							switch (matcher.group(1)){
								case "#":
									MapElement<Wall> w = new Wall();
									rowList.add(w);
									break;
								case ".": 
									MapElement<EmptyBox> e = new EmptyBox();
									rowList.add(e);
									break;
								case "O": 
									MapElement<Player> p = new Player(playerName);
									rowList.add(p);
									break;
								case "M": 
									MapElement<Monster> m = new Monster();
									rowList.add(m);
									break;
								case "C": 
									MapElement<Chest> c = new Chest();
									rowList.add(c);
									break;
								case "S": 
									MapElement<EmptyBox> e1 = new EmptyBox();
									rowList.add(e1);
									break;
								case "K":
									MapElement<EmptyBox> e2 = new EmptyBox();
									rowList.add(e2);
									break;
								case "D": 
									MapElement<EmptyBox> e3 = new EmptyBox();
									rowList.add(e3);
									break;
								case "T": 
									MapElement<EmptyBox> e4 = new EmptyBox();
									rowList.add(e4);
									break;
								case "t": 
									MapElement<EmptyBox> e5 = new EmptyBox();
									rowList.add(e5);
									break;
								case "B": 
									MapElement<EmptyBox> e6 = new EmptyBox();
									rowList.add(e6);
									break;
								case "P": 
									MapElement<EmptyBox> e7 = new EmptyBox();
									rowList.add(e7);
									break;
							}  // switch
						} // if
					} // if 
					
					last = r.readNext();
				} // while
				
				map.add(rowList);
			} // if
			
			last = r.readNext();
		} // while

		return map;		
	}
		
		
		
	
	
	/**
	 * Metodo per generare una stringa rappresentate la mappa
	 * @param map Mappa da rappresentare
	 * @return Stringa rappresentate la mappa
	 */
	public static StringBuffer mapToString(ArrayList<ArrayList<MapElement>> map) {
		
		StringBuffer str = new StringBuffer();
		
		for(ArrayList<MapElement> row : map) {
			
			for(MapElement e : row) {
				
				if(e.getElement().getClass().toString().contains("Player")) {
					str.append("P");
				}
				else if(e.getElement().getClass().toString().contains("Wall")) {
					str.append("#");
				}
				else if(e.getElement().getClass().toString().contains("EmptyBox")) {
					str.append(".");
				}
				else if(e.getElement().getClass().toString().contains("Chest")) {
					str.append("C");
				}
				else if(e.getElement().getClass().toString().contains("Monster")) {
					str.append("M");
				}
			}
			
			str.append("\n");
		}
		
		
		return str;
	}
	
	
}
