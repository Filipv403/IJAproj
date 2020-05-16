package files;

import java.util.ArrayList;
import java.util.List;


/**
 * Rozhraní definující ulici
 * 
 * @author xvacla30
 */
public interface Street {
	
	/**
	 * @return nazev ulice
	 */
	public String getId();
	
	/**
	 * Vrátí seznam všech souřadnic, kterýma je definovaná ulice
	 * 
	 * @return seznam souřadnic
	 */
	public List<Coordinate> getCoordinates();

	/**
	 * Získá seznam zastávek na ulici
	 * 
	 * @return seznam zastávek
	 */
    public List<Stop> getStops();

	/**
	 * Vloží zastávku na ulici, kontroluje se zda se na ní nachází, vrátí false pokud
	 * zastávka se nenachází na ulici a nemůže být přidána
	 * 
	 * @param stop zastávka na ulici
	 * @return true pokud se přidá, false pokud se nepřidá
	 */
	public boolean addStop(Stop stop);

	/**
	 * Získá počáteční souřadnici z pole
	 * 
	 * @return počáteční souřadnice
	 */
	public Coordinate begin();

	/**
	 * Získá poslední souřadnici z pole
	 * 
	 * @return poslední souřadnice
	 */
	public Coordinate end();

	/**
	 * Zjistí zda na sebe ulice navazují
	 * 
	 * @param s ulice
	 * @return true pokud na sebe ulice navazují jinak false
	 */
	public boolean follows(Street s);

	/**
	 * Zruší zvýraznění úsečky na mapě
	 */
	public void deselect();

	/**
	 * Zvýrazní cestu na mapě
	 */
	public void select();

	/**
	 * Zjistí jestli je cesta otevřená
	 *
	 * @return true nebo false podle dané instance ulice
	 */
	public boolean isOpen();

	/**
	 * Získá stupen provozu na cestě
	 *
	 * @return stupen provozu
	 */
	public int getTraffic();

	/**
	 * Nastaví možnost zavírání cesty
	 *
	 * @param closeable true, cestu je možné uzavřít
	 */
	public void setCloseable(boolean closeable);
	
	/**
	 * Seznamy pro nastavení výchozí ulice
	 */
	java.util.List<Stop> list_stop = new ArrayList<>();
	static java.util.List<Coordinate> list_tmp_c = new ArrayList<>();
	static java.util.List<Coordinate> list_c = new ArrayList<>();
	
	/**
	 * Nastaví výchozí ulici podle souřadnic aby na sebe navazovali
	 * 
	 * @param id název ulice
	 * @param c pole souřadnic ulic
	 * @return ulice
	 */
	public static Street defaultStreet(String id, Coordinate... c){
		Street street = new MyStreet(id, c);
		for(int i=0; i < c.length; i++){
			list_tmp_c.add(c[i]);
			list_c.add(c[i]);
		}
		if(list_tmp_c.size() == 2){
			if (list_tmp_c.get(0).getX() == list_tmp_c.get(1).getX() || list_tmp_c.get(0).getY() == list_tmp_c.get(1).getY() || list_tmp_c.get(0).getX() == list_tmp_c.get(1).getY()
				|| list_tmp_c.get(0).getY() == list_tmp_c.get(1).getX()){
				list_tmp_c.clear();
				return street;
			}
		}else if(list_tmp_c.size() == 3){
			if (list_tmp_c.get(0).getX() == list_tmp_c.get(1).getX() || list_tmp_c.get(0).getY() == list_tmp_c.get(1).getY()
				|| list_tmp_c.get(0).getX() == list_tmp_c.get(1).getY() || list_tmp_c.get(0).getY() == list_tmp_c.get(1).getX()){
				if (list_tmp_c.get(1).getX() == list_tmp_c.get(2).getX() || list_tmp_c.get(1).getY() == list_tmp_c.get(2).getY()
					|| list_tmp_c.get(1).getX() == list_tmp_c.get(2).getY() || list_tmp_c.get(1).getY() == list_tmp_c.get(2).getX()) {
					list_tmp_c.clear();
					return street;
				}
			}
		}
		list_tmp_c.clear();
		list_c.clear();
		return null;
	}
}
