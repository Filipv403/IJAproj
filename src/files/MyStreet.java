package files;

import java.util.ArrayList;
import files.Street;
import files.Coordinate;

public class MyStreet implements Street {
    private String nazev;
	private Coordinate[] c;
	

	public MyStreet() {
		this.nazev = "";
		this.c[0] = new Coordinate(0,0);
	}

	public MyStreet(String nazev, Coordinate... c) {
		this.nazev = nazev;
		this.c = c;
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
		if(this.nazev == id){
			MyStreet street = new MyStreet(this.nazev, this.c);			
			return street;
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
		stop.setStreet(this);
			if (stop.getCoordinate().getX() == this.end().getX()) {
				if (stop.getCoordinate().getY() <= this.end().getY()) {
					list_stop.add(stop);
					return true;
				}
			}
			else if (stop.getCoordinate().getY() == this.end().getY()) {
				if (stop.getCoordinate().getX() <= this.end().getX()) {
					list_stop.add(stop);
					return true;
				}
			}
		return false;
	}
	//zda na sebe ulice nabazují pomocí koncových a počátačních souřadnic
	public boolean follows(Street s) {
		
		if ((this.begin()).equals(s.begin()) || (this.end()).equals(s.end()) || (this.begin()).equals(s.end()) || (this.end()).equals(s.begin())) {
			return true;
		}
		return false;
	}

	//nastaví výchozí ulici, speciálně i pro se dvěmi souřadnicemi a třemi souřadnicemi, rovnost souřadnic
	public static Street defaultStreet(String id, Coordinate... c) {
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