import java.util.*;

public class FlightPlanner{
	private AdjacencyList flights;
	private LinkedList<Path> paths;
	private LinkedList<Integer> indexStack;
	private static boolean debug = false;
	
	public FlightPlanner(AdjacencyList f){
		paths = new LinkedList<Path>();
		flights = f;
		indexStack = new LinkedList<Integer>();
		
		for(int i = 0; i < flights.getCities().size(); i++) {
			indexStack.add(i);
			generatePaths();
			indexStack.removeLast();
		}
		
		printPaths();
	}
	
	public void printPaths() {
		for(int i = 0; i < paths.size(); i++) {
			System.out.println(paths.get(i));
		}
	}
	
	public ArrayList<Path> getFastestRoutes(String departing, String arriving){
		ArrayList<Double> times = new ArrayList<Double>();
		ArrayList<Path> relevant = new ArrayList<Path>();
		ArrayList<Path> best = new ArrayList<Path>();

		if(debug)
			System.out.println("\nentered getFastestRoutes " + paths.size());
		
		for(int i = 0; i < paths.size(); i++) {
			if(paths.get(i).connects(departing, arriving)) {
				relevant.add(paths.get(i));
				if(debug)
					System.out.println("adding \n" + paths.get(i));
				times.add(paths.get(i).length());
			}
		}
		
		int i = 0;
		while(!relevant.isEmpty() && i < 3) {
			int min = times.indexOf(Collections.min(times));
			best.add(relevant.remove(min));
			times.remove(min);
			i++;
		}
	
		return best;	
	}
	
	public ArrayList<Path> getCheapestRoutes(String departing, String arriving){
		ArrayList<Double> costs = new ArrayList<Double>();
		ArrayList<Path> relevant = new ArrayList<Path>();
		ArrayList<Path> best = new ArrayList<Path>();
		
		if(debug)
			System.out.println("\nentered getCheapestRoutes " + paths.size());
		
		for(int i = 0; i < paths.size(); i++) {
			if(paths.get(i).connects(departing, arriving)) {
				relevant.add(paths.get(i));
				if(debug)
					System.out.println("adding \n" + paths.get(i));
				costs.add(paths.get(i).cost());
			}
		}
		
		int i = 0;
		while(!relevant.isEmpty() && i < 3) {
			int min = costs.indexOf(Collections.min(costs));
			best.add(relevant.remove(min));
			costs.remove(min);
			if(debug)
				System.out.println(best.get(i));
			i++;
		}
	
		return best;
	}
	
	public static void debug() {debug = true;}
	
	private void generatePaths(){
			Path currentPath = new Path(indexStack, flights);
			paths.add(currentPath);
			Origin currentCity = flights.get(currentPath.getCities().getLast());
			LinkedList<Destination> nextDestinations = currentCity.destinations();
			
		for(int i = 0; i < nextDestinations.size(); i++) {
			if(!currentPath.contains(nextDestinations.get(i).name())) {
				indexStack.add(i);
				
				if(debug)
					System.out.println("Adding link from " + currentCity.name() + " to " + nextDestinations.get(i).name());
				
				generatePaths();
				indexStack.removeLast();
			}
		}
	}
}