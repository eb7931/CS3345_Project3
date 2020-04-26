import java.io.File;

public class FlightPlanner{
	private AdjacencyList flights;
	private String start;
	private String end;
	public void analyzeFiles(String flightDataFile, String flightPlanFile) {
		AdjacencyList flightData = new AdjacencyList();
		flightData.analyzeFiles(flightDataFile);
		analyzeFiles(flightData, flightPlanFile);
	}
	
	public void analyzeFiles(AdjacencyList flightData, String flightPlanFile) {
		flights = flightData;
		parseFlightPlan(flightPlanFile);
		
	}
	

	private void parseFlightPlan(String fileName) {
		File flightPlans = new File(fileName);
		
	}
}