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
	private int traffic;
	private Line mapLine;
	private boolean isSelected;

	public MyStreet() {
		this.nazev = "";
		this.c[0] = new Coordinate(0,0);
		this.isOpen = true;
		this.isCloseable = true;
		this.traffic = 1;
		this.isSelected = false;
	}

	public MyStreet(String nazev, Coordinate... c) {
		this.nazev = nazev;
		this.c = c;
		this.isOpen = true;
		this.isCloseable = true;
		this.traffic = 1;
		this.isSelected = false;
	}
	
	public java.lang.String getId() {
		return nazev;
	}

	public Coordinate[] getCoordinate(){
		return this.c;
	}
	
	public java.util.List<Coordinate> getCoordinates() {
		return list_c;
	}

	public MyStreet getStreetById(String id){
		if(this.nazev.equals(id)){
			return new MyStreet(this.nazev, this.c);
		}
		return null;
	}

	public void setId(String id){
		this.nazev = id;
	}
	
	public Coordinate begin() {
		return c[0];
	}
	
	public Coordinate end() {
		return c[(c.length)-1];
	}
	
	public java.util.List<Stop> getStops() {
		return list_stop;
	}
	
	// vloží zastávku, podmínky kontrolují rozmezí intervalů zda se nachází na přímce
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
	//zda na sebe ulice nabazují pomocí koncových a počátačních souřadnic
	public boolean follows(Street s) {

		return (this.begin()).equals(s.begin()) || (this.end()).equals(s.end()) || (this.begin()).equals(s.end()) || (this.end()).equals(s.begin());
	}

	//nastaví výchozí ulici, speciálně i pro se dvěmi souřadnicemi a třemi souřadnicemi, rovnost souřadnic
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
		if (traffic < 0 || traffic > 100)
			this.traffic = 1;
		else
			this.traffic = traffic;
	}

	/**
	 * Zjistí jestli je cesta otevřená
	 *
	 * @return
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
				if (!isSelected)
					this.mapLine.setStroke(Color.rgb(65, 63, 68));
				else
					this.mapLine.setStroke(Color.rgb(85, 255, 0));
			}
		}
	}

	/**
	 * Nastaví odkaz na úsečku reprezentující cestu na mapě
	 *
	 * @param mapLine Úsečka reprezentující cestu
	 */
	public void setMapLine(Line mapLine) {
		this.mapLine = mapLine;
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
		this.isSelected = false;

		if (this.mapLine != null && this.isOpen()) {
			this.mapLine.setStroke(Color.rgb(65, 63, 68));
		}
	}

	/**
	 * Zvýrazní cestu na mapě
	 */
	public void select() {
		this.isSelected = true;

		if (this.mapLine != null && this.isOpen()) {
			this.mapLine.setStroke(Color.rgb(85, 255, 0));
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
}