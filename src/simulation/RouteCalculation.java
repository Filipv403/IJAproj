package simulation;

import com.sun.javafx.geom.Vec2d;
import files.Coordinate;
import files.Street;
import java.time.Duration;
import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Třídá obsahující funkce pro výpočet cesty autobusu
 *
 * @author Michal Zobaník (xzoban01)
 */
public class RouteCalculation {
    /**
     * Vypočítá a nastaví časy příjezdů autobusu na jednotlivé uzly cesty, které mají hodnotu času rovnou null.
     * Čas rozdělí rovnoměrně podle poměru délky jednotlivých úseků mezi dvěma body s definovanými časy.
     *
     * @param route Cesta autobusu, která bude aktualizivána
     */
    public static void updateTimes(List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> route) {
        int[] idx;
        //přepočítá časy pro každý úsek mezi 2 definovanými časy
        while ((idx = routeBetween(route)) != null) {
            LocalTime startTime = route.get(idx[0]).getValue();
            LocalTime endTime = route.get(idx[1]).getValue();
            long routeDuration = Duration.between(startTime, endTime).getSeconds();
            double routeL = routeLenght(route, idx[0], idx[1]);

            for (int i = idx[0] + 1; i < idx[1]; i++) {
                startTime = startTime.plusSeconds((long) (routeDuration * (routeLenght(route, i-1, i) / routeL)));
                route.get(i).setValue(startTime);
            }
        }
    }

    /**
     * Hledá úsek v cestě, který nemá uvedené časy příjezdu. Pokud žádný takový úsek nenajde vrací null.
     * Pokud ho najde vrací indexy zastávky před a po tomto úseku
     *
     * @param route Cesta, po které autobus jede
     * @return
     */
    private static int[] routeBetween(List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> route) {
        int[] idx = new int[2];
        boolean gap = false;
        for (int i = 1; i < route.size(); i++) {
            if (route.get(i).getValue() == null) {
                //byla nalezen výskyt bez času
                gap = true;
            } else {
                if (gap) {
                    //byl nalezený výskyt s časem po mezeře bez definovaných časů
                    idx[1] = i;
                    return idx;
                } else {
                    //nalezení začátku mezery
                    idx[0] = i;
                }
            }
        }

        return null;
    }

    /**
     * Spočítá zpoždění na základě úrovně provozu na jednotlivých úsecích cesty autobusu
     * a aktualizuje jízdní řád autobusu
     *
     * @param route Cesta, po které autobus jede
     * @param streets Pole s cestami na kterých jednotlivé úseky route leží
     * @return Pole se spožděním v sekundách na jednotlivých zastávkách cesty
     */
    public static ArrayList<Long> computeDelay(List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> route, List<Street> streets) {
        if (route == null)
            return null;

        ArrayList<Long> delay = new ArrayList<>();
        long curentDelay = 0;
        delay.add(curentDelay);

        //dopočítání časů mimo zastávky
        updateTimes(route);

        for (int i = 0; i < route.size() - 1; i++) {
            if (streets.get(i).getTraffic() == 1) {
                //přidání zpoždění z předchozích ulic
                route.get(i + 1).setValue(route.get(i + 1).getValue().plusSeconds(curentDelay));
                delay.add(curentDelay);
            } else {
                //přidání zpoždění aktuální ulice a předchozích ulic
                long dur = Duration.between(route.get(i).getValue(), route.get(i + 1).getValue()).getSeconds();
                long newDelay =(long) (((streets.get(i).getTraffic() - 1) / 2.0) * dur);

                curentDelay += newDelay;
                route.get(i + 1).setValue(route.get(i + 1).getValue().plusSeconds(curentDelay));
                delay.add(curentDelay);
            }
        }

        return delay;
    }

    /**
     * Spočítá vzdálenost cesty mezi úseky od a po zadaný index z cesty autobusu
     *
     * @param route Cesta autobusu
     * @param startIdx Počáteční index od kterého se počítá vzálenost cesty
     * @param endIdx Počáteční index po kterýse počítá vzálenost cesty
     * @return vzdálenost lomené cesty mezi body na zadaných indexes
     */
    public static double routeLenght(List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> route, int startIdx, int endIdx) {
        double l = 0;
        for (int i = startIdx; i < endIdx; i++) {
            double tmp = sqrt(pow(route.get(i).getKey().diffX(route.get(i+1).getKey()), 2) + pow(route.get(i).getKey().diffY(route.get(i+1).getKey()), 2));
            l += tmp;
        }

        return l;
    }

    /**
     * Spočítá nové souřadnice na úsečce zadané dvěma body
     *
     * @param nextPos Bod reprezentující konec úsečky
     * @param prevPos Bod reprezentující začátek úsečky
     * @param currentLenght Vzdálenost od počátku úsečku
     * @return Souřadnice na úsečce ve vzdálenosti currentLenght od počátku prevPos
     */
    public static Vec2d getPosition(Coordinate nextPos, Coordinate prevPos, double currentLenght) {
        double totalL = sqrt(pow(nextPos.diffX(prevPos), 2) + pow(nextPos.diffY(prevPos), 2));
        double k = currentLenght / totalL;

        Coordinate vect = new Coordinate(nextPos.diffX(prevPos), nextPos.diffY(prevPos));
        double x = prevPos.getX() + k * vect.getX();
        double y = prevPos.getY() + k * vect.getY();

        return new Vec2d(x, y);
    }

    public static long getCurrentDelay(Coordinate nextPos, Coordinate prevPos, long nextDelay, long prevDelay, double currentLenght) {
        double totalL = sqrt(pow(nextPos.diffX(prevPos), 2) + pow(nextPos.diffY(prevPos), 2));
        double k = currentLenght / totalL;

        return (long) (prevDelay + (k * (nextDelay - prevDelay)));
    }
}
