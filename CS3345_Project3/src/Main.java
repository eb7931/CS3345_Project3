public class Main{
	public static void main(String args[]) {
		boolean debug = false;
		boolean verbose = true;
		
		String flightDataFile = "src/flightData.txt";
		String flightPlanFile = "src/flightPlans.txt";
		
		AdjacencyList flightMap = new AdjacencyList();
		FlightFinder flights = new FlightFinder();
		
		if(debug) {
			AdjacencyList.debug();
			FlightPlanner.debug();
			Origin.debug();
			Destination.debug();
			Path.debug();
			FlightFinder.debug();	
		}
		
		
		flightMap.analyzeFiles(flightDataFile);
		flights.analyzeFiles(flightMap, flightPlanFile);
		if(verbose) {
			System.out.println(flights);
			flightMap.printCities();
			flightMap.printEdges();
		}
		
		flights.printResults();
		
	}
}