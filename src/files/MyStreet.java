package files;

import java.util.ArrayList;
import java.util.List;

import files.Street;
import files.Coordinate;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Třídá reprezentující jedtlové autobusy
 *
 * @author Filip Václavík (xvacla30)
 * @author Michal Zobaník (xzoban01)
 */
public class MyStreet implements Street {
    private String nazev;
	private Coordinate[] c = new Coordinate[2];
	private boolean isOpen;
	private boolean isCloseable;

	@Override
	public String toString() {
		return nazev;
	}

	private int traffic;
	private Line mapLine;
	private int selectMode;

	/**
	 * Prázdný konstruktor ulice, který nastaví prázdné jméno, souřadnice na nulu, vše na tru a provoz na 1
	 */
	public MyStreet() {
		this.nazev = "";
		this.c[0] = new Coordinate(0,0);
		this.isOpen = true;
		this.isCloseable = true;
		this.traffic = 1;
		this.selectMode = -1;
	}

	/**
	 * Konstruktor pro ulice, definuje se název a souřadnice začátku a konce, ostatní se nastaví na true a provoz na 1
	 * 
	 * @param nazev nazev
	 * @param c souřadnice
	 */
	public MyStreet(String nazev, Coordinate... c) {
		this.nazev = nazev;
		this.c = c;
		this.isOpen = true;
		this.isCloseable = true;
		this.traffic = 1;
		this.selectMode = -1;
	}
	
	/**
	 * @return nazev ulice
	 */
	public java.lang.String getId() {
		return nazev;
	}

	/**
	 * Vrátí pole všech souřadnic, kterýma je definovaná ulice
	 * 
	 * @return c souřadnice
	 */
	public Coordinate[] getCoordinate(){
		return this.c;
	}
	
	/**
	 * Vrátí seznam všech souřadnic, kterýma je definovaná ulice
	 * 
	 * @return seznam souřadnic
	 */
	public java.util.List<Coordinate> getCoordinates() {
		return list_c;
	}

	/**
	 * Vrátí ulici podle předaného názvu, jinak null
	 * 
	 * @param id název hledané ulice
	 * @return street nebo null pokud se nenajde
	 */
	public MyStreet getStreetById(String id){
		if(this.nazev.equals(id)){
			return new MyStreet(this.nazev, this.c);
		}
		return null;
	}

	/**
	 * Nastaví jméno ulice
	 * 
	 * @param id ulice
	 */
	public void setId(String id){
		this.nazev = id;
	}
	
	/**
	 * Získá počáteční souřadnici z pole
	 * 
	 * @return počáteční souřadnice
	 */
	public Coordinate begin() {
		return c[0];
	}
	
	/**
	 * Získá poslední souřadnici z pole
	 * 
	 * @return poslední souřadnice
	 */
	public Coordinate end() {
		return c[(c.length)-1];
	}
	
	/**
	 * Získá seznam zastávek na ulici
	 * 
	 * @return seznam zastávek
	 */
	public java.util.List<Stop> getStops() {
		return list_stop;
	}
	
	/**
	 * Vloží zastávku na ulici, kontroluje se zda se na ní nachází, vrátí false pokud
	 * zastávka se nenachází na ulici a nemůže být přidána
	 * 
	 * @param stop zastávka na ulici
	 * @return true pokud se přidá, false pokud se nepřidá
	 */
	public boolean addStop(Stop stop) {
		Coordinate stopPos = stop.getCoordinate();
		for (int i = 0; i < c.length - 1; i++) {
			float vectX, vectY;
			float t1, t2;

			vectX = c[i].diffX(c[i + 1]);
			vectY = c[i].diffY(c[i + 1]);

			if (vectX == 0) {
				if (c[i].getX() != stopPos.getX())
					continue;
				t1 = 0;
			} else
				t1 = (stopPos.getX() - c[i + 1].getX()) / vectX;

			if (vectY == 0){
				if (c[i].getY() != stopPos.getY())
					continue;
				t2 = 0;
			} else
				t2 = (stopPos.getY() - c[i + 1].getY()) / vectY;

			if (vectX == 0 || vectY == 0) {
				if (t1 < 0.0 || t1 > 1.0 || t2 < 0.0 || t2 > 1.0)
					continue;
			} else {
				if (t1 != t2 || t1 < 0.0 || t1 > 1.0)
					continue;
			}
			stop.setStreet(this);
			//stops.add(stop);
			return true;
		}

		return false;
	}

	/**
	 * Zjistí zda na sebe ulice navazují
	 * 
	 * @param s ulice
	 * @return true pokud na sebe ulice navazují jinak false
	 */
	public boolean follows(Street s) {

		return (this.begin().equals(s.begin()) || this.end().equals(s.end()) || this.begin().equals(s.end()) || this.end().equals(s.begin())) && !this.equals(s);
	}

	/**
	 * Nastaví výchozí ulici podle souřadnic aby na sebe navazovali
	 * 
	 * @param id název ulice
	 * @param c pole souřadnic ulic
	 * @return ulice
	 */
	public static Street defaultStreet(String id, Coordinate... c) {
		Street street = new MyStreet(id, c);
		for (Coordinate coordinate : c) {
			list_tmp_c.add(coordinate);
			list_c.add(coordinate);
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

	/**
	 * Získá stupen provozu na cestě
	 *
	 * @return stupen provozu
	 */
	public int getTraffic() {
		return traffic;
	}

	/**
	 * Naství stupen provozu
	 *
	 * @param traffic Nový stupen provozu
	 */
	public void setTraffic(int traffic) {
		if (traffic < 0)
			this.traffic = 1;
		else this.traffic = Math.min(traffic, 100);
	}

	/**
	 * Zjistí jestli je cesta otevřená
	 *
	 * @return true nebo false dané instance ulice
	 */
	public boolean isOpen() {
		return isOpen;
	}

	/**
	 * Nastaví jestli je cesta zavřená/otevřená. Při nastavení zavřené cesty nastaví její barvu na červenou.
	 *
	 * @param open Otevřenost cesty
	 */
	public void setOpen(boolean open) {
		isOpen = open;

		if (this.mapLine != null) {
			if (!open) {
				this.mapLine.setStroke(Color.RED);
			}
			else {
				changeColor();
			}
		}
	}

	private void changeColor() {
		switch (selectMode) {
			case -1:
				this.mapLine.setStroke(Color.rgb(65, 63, 68));
				break;
			case 0:
				this.mapLine.setStroke(Color.rgb(85, 255, 0));
				break;
			case 1:
				this.mapLine.setStroke(Color.rgb(212, 245, 29));
				break;
			case 2:
				this.mapLine.setStroke(Color.rgb(235, 94, 7));
				break;
		}
	}

	/**
	 * Nastaví odkaz na úsečku reprezentující cestu na mapě
	 *
	 * @param mapLine Úsečka reprezentující cestu
	 */
	public void setMapLine(Line mapLine) {
		this.mapLine = mapLine;
		this.setOpen(this.isOpen);
	}

	/**
	 * Získá úsečku reprezentující cestu
	 *
	 * @return Úsečka reprezentující cestu
	 */
	public Line getMapLine(){
		return this.mapLine;
	}

	/**
	 * Zruší zvýraznění úsečky na mapě
	 */
	public void deselect() {
		this.selectMode = -1;

		if (this.mapLine != null && this.isOpen()) {
			changeColor();
		}
	}

	/**
	 * Zvýrazní cestu na mapě
	 */
	public void select(int newMode) {
		this.selectMode = newMode;

		if (this.mapLine != null && this.isOpen()) {
			changeColor();
		}
	}

	/**
	 * Nastaví možnost zavírání cesty
	 *
	 * @param closeable true, cestu je možné uzavřít
	 */
	public void setCloseable(boolean closeable) {
		isCloseable = closeable;
	}

	/**
	 * Najde souřadnice, které mají společné zadané cesty
	 *
	 * @param street Cesta se kterou se hledají stejné koncové souřadnice
	 * @return Souřadnice, které mají cesty společné
	 */
	public Coordinate getEqualCoord(Street street) {
		if (this.begin().equals(street.begin()) || this.begin().equals(street.end()))
			return this.begin();
		else if (this.end().equals(street.end()) || this.end().equals(street.begin()))
			return this.end();
		else
			return null;
	}

	public boolean isCloseable() {
		return isCloseable;
	}
}