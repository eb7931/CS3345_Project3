public class Main{
	public static void main(String args[]) {
		String flightDataFile = "src/flightData.txt";
		String flightPlanFile = "src/flightPlans.txt";
		
		AdjacencyList flightMap = new AdjacencyList();
		flightMap.debug();
		flightMap.analyzeFiles(flightDataFile, flightPlanFile);
	}
}