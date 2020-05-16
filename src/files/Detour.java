package files;

import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.List;

/**
 * Třídá reprezentující objíždku
 *
 * @author Michal Zobaník (xzoban01)
 */
public class Detour {
    private List<Street> replace;
    private List<Street> detourList;
    private int delay;
    private int jump;

    /**
     * Zjistí jestli se jedná o objížďku zadané cety
     *
     * @param street cesta
     * @return true jestli se jedná o objížďku zadané cesty
     */
    public boolean isReplacing(Street street) {
        return replace.get(0).equals(street) || replace.get(replace.size() - 1).equals(street);
    }

    /**
     * Odstraneni objizdky
     */
    public void remove() {
        detourList.forEach(s -> s.setCloseable(true));
    }

    /**
     * Dokončí zadávání objížďky. Upraví seznam cest a zastávek, které objížďka objíždí
     *
     * @param line Linka pro kterou je objížďka určena
     * @return true jestli se jedná o platně vytvořenou objížďku
     */
    public boolean finalize(MyLine line) {
        return false;
    }

    /**
     * Přidá cestu, po které objížďka povede
     *
     * @param street Nová cessta po které objížďka povede
     * @return True jestli se přidání povedlo
     */
    public boolean addStreet(Street street) {
        if (replace.isEmpty()) {
            replace.add(street);
            street.setCloseable(false);
            return true;
        } else {
            if (replace.get(0).follows(street) || replace.get(replace.size() - 1).follows(street)) {
                replace.add(street);
                street.setCloseable(false);
                return true;
            }
        }

        return false;
    }

    /**
     * Najde souřadnice, které mají společné zadané cesty
     *
     * @param street1 Cesta 1
     * @param street2 Cesta 2
     * @return Souřadnice, které mají cesty společné
     */
    //pridat do street ..........................  (a odebrat z myline)......
    private Coordinate getEqualCoord(Street street1, Street street2) {
        if (street1.begin().equals(street2.begin()) || street1.begin().equals(street2.end()))
            return street1.begin();
        else if (street1.end().equals(street2.end()) || street1.end().equals(street2.begin()))
            return street1.end();
        else
            return null;
    }

    /**
     * Najde Poslední vynechanou ulici z původní cesty
     *
     * @param firstStreet první cesta před objížďkou
     * @return původní cesta linky, která končí na křižovatce s objíždkou
     */
    private Street getLastReplaced(Street firstStreet) {
        if (replace.get(0).equals(firstStreet))
            return replace.get(replace.size() - 1);
        else
            return replace.get(0);
    }

    /**
     * Přidá cestu objížďky místo původní cesty linky
     *
     * @param street Poslední cesta před objížďkou
     * @param route Trasa autobusu do objížďky, do kterré bude cesta přidána
     */
    public void getRoute(Street street, List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> route) {
        if (replace.get(0).follows(street)) {
            route.add(new AbstractMap.SimpleEntry<>(getEqualCoord(street, detourList.get(0)), null));
            for (int i = 0; i < detourList.size() - 1; i++) {
                route.add(new AbstractMap.SimpleEntry<>(getEqualCoord(detourList.get(i), detourList.get(i + 1)), null));
            }
            route.add(new AbstractMap.SimpleEntry<>(getEqualCoord(detourList.get(detourList.size() - 1), getLastReplaced(street)), null));
        } else {
            route.add(new AbstractMap.SimpleEntry<>(getEqualCoord(street, detourList.get(detourList.size() - 1)), null));
            for (int i = detourList.size() - 1; i > 0; i--){
                route.add(new AbstractMap.SimpleEntry<>(getEqualCoord(detourList.get(i), detourList.get(i - 1)), null));
            }
            route.add(new AbstractMap.SimpleEntry<>(getEqualCoord(detourList.get(0), getLastReplaced(street)), null));
        }
    }

    /**
     * Zjistí kolik cest/zastávek se díky objížďce přeskocčilo
     *
     * @return počet přeskočených cest/zastávek
     */
    public int getJump() {
        return jump;
    }
}
