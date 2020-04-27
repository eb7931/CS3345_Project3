import java.util.LinkedList;

public class Path{
	private static boolean debug = false;
	private double cost = 0;
	private double length = 0;
	private AdjacencyList flights;
	private String start;
	private String end;
	
	private LinkedList<String> cities;
	
	public boolean connects(String departing, String arriving) {
		
		if(start.equals(departing) && end.equals(arriving)) {
			//System.out.println(departing + " connects to " + arriving);
			return true;
		}
		else {
			
			return false;
		}
	}
	
	
	public static void debug() {debug = true;}
	
	public LinkedList<String> getCities(){return cities;}
	
	public boolean contains(String city) {
		boolean contains = false;
		for(int i = 0; i < cities.size(); i ++) {
			if(cities.get(i).equals(city)) {
				contains = true;
			}
			//else
				//System.out.println(city + " is not equal to " + cities.get(i));
		}
		return contains;
	}
	
	public String toString() {
		String pathString = "";
		for(int i = 0; i < cities.size(); i++) {
			pathString += cities.get(i);
			if(i < cities.size() - 1) {
				pathString += " -> ";
			}
		}
		pathString += ". Time: " + length + " Cost: " + cost;
		return pathString;
	}
	
	public Path(LinkedList<Integer> indexStack, AdjacencyList f) {
		flights = f;
		cities = new LinkedList<String>();
		
		if(debug) {
			String path = "\nPrint Path constructor\nPath: ";
			for(int i = 0; i < indexStack.size(); i++) {
				path += indexStack.get(i);
				if(i < indexStack.size() - 1)
					path += ",";
			}
			System.out.println(path);
		}
		
		
		for(int i = 0; i < indexStack.size(); i++) {
			if(i == 0) {
				Origin current = flights.getCities().get(indexStack.get(0));
				cities.add(current.name());
				//System.out.println("pushing " + current.name());
			}
			else {
				Origin prev = flights.get(cities.get(i - 1));
				Destination current = prev.destinations().get(indexStack.get(i));
				double c = current.cost();
				double l = current.length();
				addCost(c);
				addLength(l);
				cities.add(current.name());
				//System.out.println("pushing " + current.name());
			}
		}
		start = cities.getFirst();
		end = cities.getLast();
		
		if(debug) {
			System.out.println(toString());
			System.out.println();
		}
		
	}
	
	public void addCity(String name, double c, double l) {
		cities.add(name);
		addCost(c);
		addLength(l);
	}
	public double cost() {return cost;}
	public double length() {return length;}
	public void addCost(double c) {cost += c;}
	public void addLength(double l) {length += l;}
	public void setCost(double c) {cost = c;}
	public void setLength(double l) {length = l;}
	
	
}