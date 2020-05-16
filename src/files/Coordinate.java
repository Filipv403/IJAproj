package files;

/**
 * Třída pro souřadnice
 * 
 * @author xvacla30
 */
public class Coordinate{

    /**
     * souřadnice x a y
     */
    private int x = 0;
    private int y = 0;

    /**
     * Prázdný konstruktor, nastaví hodnoty souřadnic na 0
     */
    public Coordinate() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Konstruktor nastaví hodnoty souřadnic dle parametru
     * 
     * @param x souřadnice x
     * @param y souřadnice y
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 
     * @return x souřadnici
     */
    public int getX() {
        return this.x;
    }

    /**
     * 
     * @return y souřadnici
     */
    public int getY(){
        return this.y;
    }

    /**
     * Prázdné souřadnici nastaví souřadnice x a y
     * 
     * @param x souřadnice
     * @param y souřadnice
     */
    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    /**
     * Vypočítá rozdíl této souřadnici se zadanou souřadnicí na x ose
     * 
     * @param c odečítaná souřadnice
     * @return rozdíl souřadnic na x ose
     */
    public int diffX(Coordinate c){
        int diffX = this.x - c.x;
        return diffX;
    }

    /**
     * Vypočítá rozdíl této souřadnici se zadanou souřadnicí na y ose
     * 
     * @param c odečítaná souřadnice
     * @return rozdíl souřadnic na y ose
     */
    public int diffY(Coordinate c){
        int diffY = this.y - c.y;
        return diffY;
    }

    /**
     * Vytvoří novou souřadnici 
     * 
     * @param x souřadnice
     * @param y souřadnice
     * @return vytvořenou souřadnici
     */
    public static Coordinate create(int x, int y){
        if(x < 0 || y < 0){
            return null;
        }
        Coordinate coordinate = new Coordinate(x, y);
        return coordinate;
    }

    @Override
    public boolean equals(Object o){
        Coordinate co = (Coordinate) o;
        if (x != co.getX() || y != co.getY()) {
            return false;
        }
        return true;
    }
}
