package loaded;

import files.*;

/**
 * Rozhraní slouží pro vložení do mapy ulici, dle zadaných souřadnic.
 * Slouží pro vykreslení z programu
 *
 * @author Filip Václavík (xvacla30)
 */
public interface AddMapItem {
    /**
     * Přidá souřadnici do vytvořené instance třídy
     * 
     * @param x souřadnice na ose x
     * @param y souřadnice na ose y
     * @return Coordinate c, instanci třídy souřadnice
     */
    public static Coordinate addCoordinate(int x, int y){
        Coordinate c = new Coordinate();

        c.setXY(x, y);

        return c;
    }

    /**
     * Vytvoří ulici dle jejich souřadnic
     * 
     * @param name jméno ulice
     * @param StartX počáteční x souřadnice
     * @param StartY počáteční y souřadnice
     * @param EndX koncová x souřadnice
     * @param EndY koncová y souřadnice
     * @return vrátí vytvořenou ulici 
     */
    public static MyStreet setStreet(String name, int StartX, int StartY, int EndX, int EndY){
        MyStreet street = new MyStreet(name, AddMapItem.addCoordinate(StartX, StartY), AddMapItem.addCoordinate(EndX, EndY));
        return street;
    }
}