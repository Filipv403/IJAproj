package files;

import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Třídá reprezentující objíždku
 *
 * @author Michal Zobaník (xzoban01)
 */
public class Detour {
    private List<Street> replace;
    private List<Street> detourList;
    private long delay;
    private int jump;

    public Detour() {
        this.replace = new ArrayList<>();
        this.detourList = new ArrayList<>();
    }

    /**
     * Zjistí jestli se jedná o objížďku zadané cety
     *
     * @param street cesta
     * @return true jestli se jedná o objížďku zadané cesty
     */
    public boolean isReplacing(Street street) {
        return replace.get(0).equals(street);
    }

    /**
     * Odstraneni objizdky
     */
    public void remove() {
        detourList.forEach(s -> {
            s.setCloseable(true);
            s.deselect();
        });
    }

    /**
     * Dokončí zadávání objížďky. Upraví seznam cest a zastávek, které objížďka objíždí
     *
     * @param line Linka pro kterou je objížďka určena
     * @return true jestli se jedná o platně vytvořenou objížďku
     */
    public boolean finalize(MyLine line) {
        boolean startFound = false;
        boolean endFound = false;
        int startIdx = -1;

        for (int i = 1; i < line.map_list.size(); i++) {
            Coordinate node = line.map_list.get(i).getKey().getEqualCoord(line.map_list.get(i - 1).getKey());
            Coordinate detourStartNode = line.map_list.get(i).getKey().getEqualCoord(detourList.get(0));
            Coordinate detourEndNode = line.map_list.get(i).getKey().getEqualCoord(detourList.get(detourList.size() - 1));

            //hledání začátku
            if (!startFound) {
                if (line.map_list.get(i).getKey().equals(line.map_list.get(i - 1).getKey()))
                    continue;

                if (node.equals(detourEndNode))
                    Collections.reverse(detourList);
                else if (!node.equals(detourStartNode))
                    continue;

                startFound = true;
                startIdx = i;

            } else { //hledání konce
                if (line.map_list.get(i).getKey().equals(line.map_list.get(i - 1).getKey())) {
                    replace.add(line.map_list.get(i - 1).getKey());
                    continue;
                }

                if (node.equals(detourEndNode)) {
                    replace.add(line.map_list.get(i - 1).getKey());
                    jump = i - startIdx - 1;
                    return multipleDetourCheck(line);
                }

                replace.add(line.map_list.get(i - 1).getKey());
            }
        }

        return false;
    }

    /**
     * Kontroluje jestli zadaná objížďka nenahrazuje nějakou už objížděnou cestu
     *
     * @return true jestli je vše splněno
     */
    private boolean multipleDetourCheck(MyLine line) {
        for (Detour det : line.detours) {
            for (Street s : det.getReplace()) {
                for (Street s2 : this.replace) {
                    if (s.equals(s2))
                        return false;
                }
            }
        }

        return true;
    }

    /**
     * Přidá cestu, po které objížďka povede po provedení potřebných kontrol
     *
     * @param street Nová cessta po které objížďka povede
     * @return True jestli se přidání povedlo
     */
    public boolean addStreet(Street street) {
        int lastidx = detourList.size() - 1;

        if (detourList.isEmpty() || (detourList.size() == 1 && detourList.get(0).follows(street))) {
            setStreetProp(street);
            return true;
        } else if (detourList.get(0).follows(street) && detourList.get(0).getEqualCoord(street) != detourList.get(1).getEqualCoord(detourList.get(0))) {
            detourList.add(0, street);
            street.select(true);
            street.setCloseable(false);
            return true;
        } else if (detourList.get(lastidx).follows(street) &&
                detourList.get(lastidx).getEqualCoord(street) != detourList.get(lastidx).getEqualCoord(detourList.get(lastidx - 1))) {
            setStreetProp(street);
            return true;
        }

        return false;
    }

    /**
     * Nastaví vlastnosti ulice a přidá ji do objížďky
     *
     * @param street ulice k přidání
     */
    private void setStreetProp(Street street) {
        detourList.add(street);
        street.select(true);
        street.setCloseable(false);
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
        //route.add(new AbstractMap.SimpleEntry<>(street.getEqualCoord(detourList.get(0)), null));
        for (int i = 0; i < detourList.size() - 1; i++) {
            route.add(new AbstractMap.SimpleEntry<>(detourList.get(i).getEqualCoord(detourList.get(i + 1)), null));
        }
        route.add(new AbstractMap.SimpleEntry<>(detourList.get(detourList.size() - 1).getEqualCoord(getLastReplaced(street)), null));
    }

    /**
     * Zjistí kolik cest/zastávek se díky objížďce přeskocčilo
     *
     * @return počet přeskočených cest/zastávek
     */
    public int getJump() {
        return jump;
    }

    public List<Street> getReplace() {
        return replace;
    }

    public long getDelay() {
        return delay;
    }
}
