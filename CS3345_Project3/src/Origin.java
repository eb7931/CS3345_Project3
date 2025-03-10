import java.util.LinkedList;
		
public class Origin{
		private static boolean debug = false;
		private boolean debugEquals = false;
		private String name;
		private LinkedList<Destination> possibleDestinations;
		
		public Origin(String cityName) {
			possibleDestinations = new LinkedList<Destination>() ;
			name = cityName;
		}
		
		public static void debug() {debug = true;}
		
		public boolean leadsTo(String name) {
			return leadsTo(new Destination(name));
		}
		
		public boolean leadsTo(Destination city) {
			if(possibleDestinations.contains(city))
				return true;
			else 
				return false;
		}
		
		@Override
		public boolean equals(Object o) {
			if(!(o instanceof Origin)) {
				if(debug)
					System.out.println("tried to add invalid city");
				return false;
			}
			else {
				if(((Origin) o).name().equals(this.name())) {
					if(debugEquals) {
						System.out.println(((Origin) o).name() + " is equal to " + this.name());
					}
					return true;
				}
				else {
					if(debugEquals) {
						System.out.println(((Origin) o).name() + " is not equal to " + this.name());
					}
					return false;
				}
			}
		}
		
		public LinkedList<Destination> destinations() {
			return possibleDestinations;
		}
		
		public String name() {return name;}
	}