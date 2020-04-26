import java.io.*;
import java.util.*;

public class AdjacencyList{
	private LinkedList<Origin> cities;
	private boolean debug = false;
	
	public AdjacencyList() {
		cities = new LinkedList<Origin>();
	}
	
	public void debug() {debug = true;}
	
	public void analyzeFiles(String flightDataFile, String flightPlanFile) {
		parseFlightData(flightDataFile);
		
	}
	

	private void parseFlightPlan(String fileName) {
		File flightPlans = new File(fileName);
		
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
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addDestination(String o, String d, String c, String l) {
		int originIndex = cities.indexOf(new Origin(o));
		double cost = Double.parseDouble(c);
		double length = Double.parseDouble(l);
		
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
		
	}
	
	private void addOrigin(String name) {
		for(int i = 0; i < cities.size(); i++) {
			if(cities.indexOf(new Origin(name)) == -1) {
				cities.add(new Origin(name));
			}
		}
	}
	
	private class Origin{
		private String name;
		private LinkedList<Destination> possibleDestinations = null;
		private Origin nextOrigin = null;
		
		public Origin(String cityName) {
			name = cityName;
		}
		
		public boolean equals(Origin o1, Origin o2) {
			if(o1.name() == o2.name())
				return true;
			else 
				return false;
		}
		
		public LinkedList<Destination> destinations() {
			return possibleDestinations;
		}
		
		public String name() {return name;}
	}
	
	private class Destination{
		private String name;
		private Destination nextDestination = null;
		private double cost;
		private double length;
		
		public Destination(String cityName, double c, double l) {
			name = cityName;
			cost = c;
			length = l;
		}
		
		public boolean equals(Origin o1, Origin o2) {
			if(o1.name() == o2.name())
				return true;
			else 
				return false;
		}
		
		public String name() {return name;}
	}
}