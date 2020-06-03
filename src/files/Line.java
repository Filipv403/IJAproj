package files;

/**
 * Rozhraní definující linku
 * 
 * @author Filip Václavík (xvacla30)
 */
public interface Line {

	/**
	 * Vloží zastávku do linky podle toho zda leží na ulici, která se nachází na lince, vrátí true nebo false podle
	 * toho zda to bylo úspěšné
	 * 
	 * @param stop přidávaní zastávka 
	 * @return true když se přidala, false když se nepřidala
	 */
	public boolean addStop(Stop stop);

	/**
	 * Vloží ulici do linky, vrátí true nebo false podle
	 * toho zda to bylo úspěšné
	 * 
	 * @param street přidávaná ulice
	 * @return true když se přidala, false když se nepřidala
	 */
	public boolean addStreet(Street street);

	/**
	 * Vrátí linku s ulicemi a zastávkami
	 * 
	 * @return linka
	 */
	public java.util.List<java.util.AbstractMap.SimpleImmutableEntry<Street,Stop>> getRoute();

	/**
	 * Vytvoří linku pouze se jménem
	 * 
	 * @param id jméno linky
	 * @return linka
	 */
	public static Line defaultLine(java.lang.String id) {
		Line line = new MyLine(id);
		return line;
	}

	/**
	 * Zruší zvýraznení linky
	 */
    void deselect();
}
