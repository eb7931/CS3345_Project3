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
			generatePaths(0);
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
	
	private void generatePaths(int index){
			Path currentPath = new Path(indexStack, flights);
			paths.add(currentPath);
			//int pathLength = currentPath.getCities().size();
			//Origin previousCity = flights.get(currentPath.getCities().get(pathLength - 2));
			Origin currentCity = flights.get(currentPath.getCities().getLast());
			//LinkedList<Destination> destinations = previousCity.destinations();
			LinkedList<Destination> nextDestinations = currentCity.destinations();
			//int currentIndex = destinations.indexOf(new Destination(currentCity.name()));
			//boolean hadNext = false;
		for(int i = 0; i < nextDestinations.size(); i++) {
			if(!currentPath.contains(nextDestinations.get(i).name())) {
				indexStack.add(i);
				if(debug)
					System.out.println("Adding link from " + currentCity.name() + " to "
							+ nextDestinations.get(i).name());
				generatePaths(i);
				//hadNext = true;
				indexStack.removeLast();
			}
		}		
		//if(hadNext)
			//index = indexStack.removeLast() + 1;

	}
	
	
	
	/*
	private void generatePaths(boolean b) {
		LinkedList<Integer> indexStack = new LinkedList<Integer>();
		indexStack.add(0);
		boolean complete = false;
		while(!complete) {
			//Path currentPath = new Path(flights.getCities().get(indexStack.peekLast()).name());
			boolean deadEnd = false;
			while(!deadEnd) {
				//LinkedList<Destination> currentOptions = flights.get(currentPath.getCities().getLast()).destinations();

				//if(indexStack.peekLast() >= currentOptions.size() - 1) {
				
				if(nextUnvisited(indexStack) == -1){
					deadEnd = true;
					paths.add(new Path(indexStack, flights));
					indexStack.removeLast();
					if(hasNext(indexStack)) {
						int lastIndex = indexStack.removeLast() + 1;
						if(indexStack.size() == 0 && lastIndex < flights.getCities().size()) {
							indexStack.add(lastIndex);
						}
						else if(nextUnvisited(indexStack, lastIndex) != -1)
							indexStack.add(nextUnvisited(indexStack, lastIndex));
						else
							while(!hasNext(indexStack) && !indexStack.isEmpty()) {
								paths.add(new Path(indexStack, flights));
								indexStack.removeLast();
							}
							
					}
					else {
						while(!hasNext(indexStack) && !indexStack.isEmpty()) {
							paths.add(new Path(indexStack, flights));
							indexStack.removeLast();
						}
					}
				}
				else if(nextUnvisited(indexStack) != -1){
					indexStack.add(nextUnvisited(indexStack));
					//Destination nextCity = currentOptions.get(indexStack.peekLast());
					//currentPath.addCity(nextCity.name(), nextCity.cost(), nextCity.length());
				}
				else {
					
				}
				
			
				if(indexStack.isEmpty() || indexStack.size() == 0) {
					complete = true;
				}
			}
			
			
			
			
			
		}
	}
	*/
	/*
	private boolean hasNext(LinkedList<Integer> indexStack) {
		Path currentPath = new Path(indexStack, flights);
		int pathLength = currentPath.getCities().size();
		if(pathLength > 1) {
			Origin prev = flights.get(currentPath.getCities().get(pathLength - 2));
			
			if(debug)
				System.out.println("previous in hasNext is " + prev.name());
 			
			if(prev.destinations().size() <= indexStack.peekLast() + 1)
				return false;
			else
				return true;
		}
		else if(pathLength == 0) {
			return false;
		}
		else if(flights.getCities().size() <= indexStack.peekLast() + 1) {
			return false;
		}
		else
			return true;
		//LinkedList<Destination> destinations = flights.get(currentPath.getCities().peekLast()).destinations();
	}
	
	private int nextUnvisited(LinkedList<Integer> indexStack) {
		return nextUnvisited(indexStack, 0);
	}
	
	private int nextUnvisited(LinkedList<Integer> indexStack, int currIndex) {
		int index = -1;
		Path currentPath = new Path(indexStack, flights);
		Origin current = flights.get(currentPath.getCities().peekLast());
		LinkedList<Destination> destinations = current.destinations();
		int numDestinations = destinations.size();
		
		for(int i = currIndex; i < numDestinations; i++) {
			if(current.leadsTo(destinations.get(i)) && !currentPath.contains(destinations.get(i).name()))
				index = i;
		}
		
		if(debug) {
			System.out.println("Current is " + current.name());
			if(index == -1) {
				System.out.println(currentPath.getCities().peekLast() + " does not have next unvisited ");
			}
			else 
				System.out.println("Next unvisited from " + currentPath.getCities().peekLast() + " is "
					+ destinations.get(index).name());
		}
		
		return index;
	}
	*/
}