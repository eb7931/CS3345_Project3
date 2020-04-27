import java.io.*;
import java.util.*;

public class AdjacencyList{
	private LinkedList<Origin> cities;
	private static boolean debug = false;
	
	public AdjacencyList() {
		cities = new LinkedList<Origin>();
	}
	
	public static void debug() {debug = true;}
	
	public void analyzeFiles(String flightDataFile) {
		parseFlightData(flightDataFile);
		
	}
	
	public Origin get(String cityName) {
		int index = cities.indexOf(new Origin(cityName));
		if(index != -1) {
			return cities.get(index);
		}
		else {
			return null;
		}
	}
	
	public Origin get(Origin city) {
		int index = cities.indexOf(new Origin(city.name()));
		if(index != -1) {
			return cities.get(index);
		}
		else {
			return null;
		}
	}
	
	public Origin get(Destination city) {
		int index = cities.indexOf(new Origin(city.name()));
		if(index != -1) {
			return cities.get(index);
		}
		else {
			return null;
		}
	}

	public LinkedList<Origin> getCities() {
		return cities;
	}
	
	private void parseFlightData(String fileName) {
		File flightData = new File(fileName);
		try {
			BufferedReader input = new BufferedReader(new FileReader(flightData));
			for(String line = input.readLine(); line != null; line = input.readLine()) {
				String[] parts = line.split("\\|");
				if(parts.length > 3) {
					addOrigin(parts[0]);
					addOrigin(parts[1]);
					addDestination(parts[0],parts[1],parts[2],parts[3]);
				}
				
				if(debug) {
					for(int i = 0; i < parts.length; i++) {
						System.out.println(parts[i]);
					}
				}
			}
			input.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void printEdges() {
		for(int i = 0; i < cities.size(); i++) {
			for(int j = 0; j < cities.get(i).destinations().size(); j++) {
				System.out.println(cities.get(i).name() + " to " + cities.get(i).destinations().get(j).name()
						+ " Costs: " + cities.get(i).destinations().get(j).cost() + " and takes "
						+ cities.get(i).destinations().get(j).length() + " minutes.");
			}
		}
	}
	
	public void printCities() {
		for(int i = 0; i < cities.size(); i++) {
				System.out.println(cities.get(i).name());
		}
	}
	
	private void addDestination(String o, String d, String c, String l) {
		double cost = Double.parseDouble(c);
		double length = Double.parseDouble(l);
		/*
		 * adding the forward direction path
		 */
		int originIndex = cities.indexOf(new Origin(o));
		
		if(originIndex == -1) {
			if(debug)
				System.out.println("issue with finding " + o + " in list of cities.");
		}
		else{
			int destinationIndex = cities.get(originIndex).destinations().indexOf(new Destination(d, cost, length));
			if(destinationIndex == -1) {
				cities.get(originIndex).destinations().add(new Destination(d, cost, length));
			}
			else if(debug) {
				System.out.println("Duplicate flight info from " + o + " to " + d + ".");
			}
		}
		/*
		 * adding the reverse direction path
		 */
		originIndex = cities.indexOf(new Origin(d));
		
		if(originIndex == -1) {
			if(debug)
				System.out.println("issue with finding " + d + " in list of cities.");
		}
		else{
			int destinationIndex = cities.get(originIndex).destinations().indexOf(new Destination(o, cost, length));
			if(destinationIndex == -1) {
				cities.get(originIndex).destinations().add(new Destination(o, cost, length));
			}
			else if(debug) {
				System.out.println("Duplicate flight info from " + d + " to " + o + ".");
			}
		}
		
	}
	
	private void addOrigin(String name) {
		if(!cities.contains(new Origin(name))) {
			cities.add(new Origin(name));
			if(debug) {
				System.out.println("Adding " + name + " to cities.");
			}
		}
	}
	

}