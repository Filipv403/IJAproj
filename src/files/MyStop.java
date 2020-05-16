package files;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Třídá reprezentující zastávky autobusů
 *
 * @author Filip Václavík (xvacla30)
 * @author Michal Zobaník (xzoban01)
 */
public class MyStop implements Stop {
	private String id;
    private Coordinate c;
    private Street s;
    private Rectangle mapRect;
    
    /**
     * Prázdný konstruktor pro zastávku
     */
    public MyStop() {
        this.id = "";
    }

    /**
     * Konstruktor pro zastávku se jménem zastávky
     * 
     * @param id zastávky
     */
    public MyStop(String id) {
        this.id = id;
    }

    /**
     * Konstruktor pro zastávku se jménem zastávky a souřadnicemi zastávky
     * 
     * @param id zastávky
     * @param c souřadnice zastávky
     */
    public MyStop(String id, Coordinate c) {
        this.id = id;
        this.c = c;
    }

    /**
	 * 
	 * @return název zastávky
	 */
    public String getId(){
        return this.id;
    }

    /**
	 * 
	 * @return souřadnice zastávky
	 */
    public Coordinate getCoordinate(){
        if(c != null){
            return this.c;
        }
        return null;
    }

    /**
	 * Nastaví jméno zastávky
	 * 
	 * @param id název
	 */
    public void setId(String id){
        this.id = id;
    }

    /**
	 * Nastaví zastávku do ulice
	 * 
	 * @param s ulice
	 */
    public void setStreet(Street s){
		this.s = s;
    }

    /**
	 * Získá ulici, na které je tato zastávka
	 * 
	 * @return street
	 */
    public Street getStreet(){
		return this.s;
    }

    /**
	 * Nastaví výchozí zastávku a vrátí vytvořenou zastávku
	 * 
	 * @param id jméno 
	 * @param c souřadnice
	 * @return stop
	 */
    public static Stop defaultStop(String id, Coordinate c){
        Stop stop = new MyStop(id, c);
        return stop;
    }

    @Override
    public boolean equals(Object o){
        Stop stop = (Stop) o;
        return id.equals(stop.getId());
    }

    @Override
    public String toString(){
        try {
            return "" + this.id + " na ulici: " + s.getId();
        } catch (Exception e) {
            return "" + this.id + "";
        }
    }

    /**
     * Nastavení odkazu na čtverec reprezentující zastávku na mapě
     *
     * @param mapRect
     */
    public void setMapRect(Rectangle mapRect) {
        this.mapRect = mapRect;
    }

    /**
     * Získání čtverec reprezentující zastávku na mapě
     *
     * @return čtverec reprezentující zastávku na mapě
     */
    public Rectangle getMapRect(){
        return this.mapRect;
    }

    /**
     * Zruší zvýraznění zastávky na mapě
     */
    public void deselect() {
        if (mapRect != null) {
            mapRect.setFill(Color.RED);
            mapRect.setStroke(Color.RED);
        }
    }

    /**
     * Zvýrazní zastávku na mapě
     */
    public void select() {
        if (mapRect != null) {
            mapRect.setFill(Color.rgb(14, 41, 0));
            mapRect.setStroke(Color.rgb(14, 41, 0));
        }
    }
}