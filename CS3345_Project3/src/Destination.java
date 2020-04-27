
public class Destination{
	private String name;
	private double cost;
	private double length;
	
	public Destination(String cityName, double c, double l) {
		name = cityName;
		cost = c;
		length = l;
	}
	
	public Destination(String cityName) {
		name = cityName;
	}
	
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