import java.io.*;
import java.util.*;


public class FlightFinder{
	private AdjacencyList flights;
	private LinkedList<Flight> desiredFlights;
	private FlightPlanner planner;
	private static boolean debug = false;

	public FlightFinder() {
		desiredFlights = new LinkedList<Flight>();
	}
	
	public void printResults() {
		for(int i = 0; i < desiredFlights.size(); i++) {
			System.out.println(desiredFlights.get(i));
		}
	}
	
	public static void debug() {debug = true;}
	
	public void analyzeFiles(String flightDataFile, String flightPlanFile) {
		AdjacencyList flightData = new AdjacencyList();
		flightData.analyzeFiles(flightDataFile);
		analyzeFiles(flightData, flightPlanFile);
	}
	
	public void analyzeFiles(AdjacencyList flightData, String flightPlanFile) {
		flights = flightData;
		planner = new FlightPlanner(flights);
		parseFlightPlan(flightPlanFile);
	}
	

	private void parseFlightPlan(String fileName) {
		File flightPlans = new File(fileName);
		try {
			BufferedReader input = new BufferedReader(new FileReader(flightPlans));
			int l = 0;
			
			for(String line = input.readLine(); line != null; line = input.readLine()) {
				String[] parts = line.split("\\|");
				
				if(parts.length > 2) {
					l++;
					desiredFlights.add(new Flight(parts[0],parts[1],parts[2], l));
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

	public String toString() {
		String plans = "\n";
		for(int i = 0; i < desiredFlights.size(); i++) {
			plans += "Flight " + i + " origin: " + desiredFlights.get(i).start()
					+ ", destination: " + desiredFlights.get(i).end()
					+ ", priority: " + desiredFlights.get(i).priority() + "\n";
		}
		plans += "\n";
		
		return plans;
	}
	
	private class Flight{
		private String start;
		private String end;
		private String priority;
		private int flightNum;
		ArrayList<Path> bestRoutes;
		
		
		public Flight(String s, String e, String p, int f) {
			start = s;
			end = e;
			priority = p;
			flightNum = f;
			if(priority.equals("C")) {
				if(debug)
					System.out.println("Doing the cheapest finding");
				bestRoutes = planner.getCheapestRoutes(start, end);
			}
			if(priority.equals("T")) {
				if(debug)
					System.out.println("Doing the fastest finding");
				bestRoutes = planner.getFastestRoutes(start, end);
			}
		}
		
		public String toString() {
			String output = "";
			output += "Flight " + flightNum + ": " + start + ", " + end;
			if(priority.equals("C")) {
				output += " (Cost)";
			}
			if(priority.equals("T")) {
				output += " (Time)";
			}
			if(bestRoutes == null) {
				output = "nullpointer";
			}
			else if(bestRoutes.size() != 0)
				for(int i = 0; i < bestRoutes.size(); i++) {
					output += "\nPath " + (i+1) + ": " + bestRoutes.get(i);
				}
			else {
				output += "\nThere are no available paths for this trip.";
			}
			
			return output;
		}
		
		public String start() {return start;}
		public String end() {return end;}
		public String priority() {return priority;}
	}
	
}