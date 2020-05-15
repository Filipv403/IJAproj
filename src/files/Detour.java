package files;

import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.List;

public class Detour {
    private List<Street> replace;
    private List<Street> detourList;
    private int delay;
    private int jump;

    public List<Street> getReplace() {
        return replace;
    }

    public boolean isReplacing(Street street) {
        return replace.get(0).equals(street) || replace.get(replace.size() - 1).equals(street);
    }

    public void remove() {
        detourList.forEach(s -> s.setCloseable(true));
    }

    public boolean finalize(MyLine line) {
        return false;
    }

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

    //pridat do street ..........................  (a odebrat z myline)......
    private Coordinate getEqualCoord(Street street1, Street street2) {
        if (street1.begin().equals(street2.begin()) || street1.begin().equals(street2.end()))
            return street1.begin();
        else if (street1.end().equals(street2.end()) || street1.end().equals(street2.begin()))
            return street1.end();
        else
            return null;
    }

    private Street getLastReplaced(Street firstStreet) {
        if (replace.get(0).equals(firstStreet))
            return replace.get(replace.size() - 1);
        else
            return replace.get(0);
    }

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

    public int getJump() {
        return jump;
    }
}
