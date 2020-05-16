package files;

/**
 * Rozhraní definující zastávky
 * 
 * @author xvacla30
 */
public interface Stop {
	/**
	 * 
	 * @return název zastávky
	 */
	public String getId();

	/**
	 * 
	 * @return souřadnice zastávky
	 */
	public Coordinate getCoordinate();

	/**
	 * Nastaví zastávku do ulice
	 * 
	 * @param s ulice
	 */
	public void setStreet(Street s);

	/**
	 * Získá ulici, na které je tato zastávka
	 * 
	 * @return street
	 */
	public Street getStreet();

	/**
     * Zruší zvýraznění zastávky na mapě
     */
	public void deselect();

	/**
     * Zvýrazní zastávku na mapě
     */
	public void select();

	/**
	 * Nastaví výchozí zastávku a vrátí vytvořenou zastávku
	 * 
	 * @param id jméno 
	 * @param c souřadnice
	 * @return stop
	 */
	public static Stop defaultStop(String id, Coordinate c) {
		Stop stop = new MyStop(id, c);
		return stop;
	}
}
