package files;

import files.Stop;
import files.Street;

public interface Line {

	public boolean addStop(Stop stop);

	public boolean addStreet(Street street);

	public java.util.List<java.util.AbstractMap.SimpleImmutableEntry<Street,Stop>> getRoute();

	public static Line defaultLine(java.lang.String id) {
		Line line = new MyLine(id);
		return line;
	}
}
