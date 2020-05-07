package files;

/**
 * @author xvacla30
 */
public interface Stop {
	public String getId();

	public Coordinate getCoordinate();

	public void setStreet(Street s);

	public Street getStreet();

	public static Stop defaultStop(String id, Coordinate c) {
		Stop stop = new MyStop(id, c);
		return stop;
	}
}
