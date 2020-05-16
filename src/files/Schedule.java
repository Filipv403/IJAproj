package files;

import java.time.LocalTime;
import java.util.*;

/**
 * Třídá reprezentující jízdní řád autobusu s časy příjezdu k jednotlivým zastávkám
 *
 * @author Michal Zobaník (xzoban01)
 */
public class Schedule {
    private List<AbstractMap.SimpleImmutableEntry<LocalTime,Stop>> schedule;
    private int busID;

    /**
     * Konstruktor jízdního řádu
     *
     * @param bus Id autobusu pro kterého jiszdní řád platí
     */
    public Schedule(int bus) {
        this.schedule = new ArrayList<>();
        this.busID = bus;
    }

    /**
     * Přidá zastávku do jízdního řádu
     *
     * @param time Čas příjezdu na zastávku
     * @param stop Zastávka
     * @return true jestli se podařilo přidat zastávku
     */
    public boolean addStop(LocalTime time, Stop stop) {
        //přidání do prázdého řádu
        if (schedule.isEmpty()) {
            schedule.add(new AbstractMap.SimpleImmutableEntry<>(time, stop));
            return true;
        }

        //kontrola posloupnosti času
        if (time.isAfter(schedule.get(schedule.size() - 1).getKey())) {
            schedule.add(new AbstractMap.SimpleImmutableEntry<>(time, stop));
            return true;
        }

        return false;
    }

    /**
     * Získá předpokládaný příjezd na zastávku
     *
     * @param stop Zastávka
     * @return Čas příjezdu na zastávku, null když zastávka není v jízdním řádu
     */
    public LocalTime getTime(Stop stop) {
        for (AbstractMap.SimpleImmutableEntry<LocalTime, Stop> myStop : schedule) {
            if (myStop.getValue().equals(stop))
                return myStop.getKey();
        }

        return null;
    }

    /**
     * Získá ID autobusu pro který je jízdní řád určen
     *
     * @return ID autobusu
     */
    public int getBusID() {
        return busID;
    }

    /**
     * Získá následující cíl autobusu (zlom na cestě/zastávka) podle aktuálního času
     *
     * @param route Cesta, po které autobus jede
     * @param time Aktuální čas
     * @return Index na kterém se nachází následující cíl
     */
    public int getNextStop(List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> route, LocalTime time) {
        for (int i = 1; i < route.size(); i++) {
            if (route.get(i).getValue().isAfter(time)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Najde Zastávku, která se nachází na zadaných souřadnicích
     *
     * @param c Souřadnice zastávky
     * @return Zastávka, která leží na souřadnicích, jestli není žádná nalezená vrací null
     */
    public Stop getStopAt(Coordinate c) {
        for (AbstractMap.SimpleImmutableEntry<LocalTime,Stop> stopPair: schedule) {
            if (stopPair.getValue().getCoordinate().equals(c))
                return stopPair.getValue();
        }

        return null;
    }
}
