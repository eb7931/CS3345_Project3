
public class Destination{
	private static boolean debug = false;
	private String name;
	private Destination nextDestination = null;
	private double cost;
	private double length;
	
	public Destination(String cityName, double c, double l) {
		name = cityName;
		cost = c;
		length = l;
	}

	public static void debug() {debug = true;}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Destination)) {
			return false;
		}
		else {
			if(((Destination) o).name().equals(this.name()))
				return true;
			else 
				return false;
		}
	}
	
	public double cost() {return cost;}
	
	public double length() {return length;}
	
	public String name() {return name;}
}