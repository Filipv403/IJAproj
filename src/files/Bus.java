package files;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap;

import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import simulation.RouteCalculation;

/**
 * Třídá reprezentující jednotlivé autobusy
 *
 * @author Filip Václavík (xvacla30)
 * @author Michal Zobaník (xzoban01)
 */
public class Bus {
    private int id;
    private String type;
    private String carrier;
    private MyLine line;
    private Circle circle;
    private Schedule schedule;
    private long actDelay;
    private AbstractMap.SimpleEntry<Stop, LocalTime> nextStop;

    /**
     * Konstruktor pro Autobus
     * Nastaví číslo a zpoždění na 0 a z typu a dopravce udělá prázdné řetězce
     */
    public Bus(){
        this.id=0;
        this.type="";
        this.carrier="";
        this.actDelay = 0;
        this.nextStop = new AbstractMap.SimpleEntry<Stop, LocalTime>(null, null);
    }

    /**
     * Konstruktor pro Autobus, zpoždění nastavuje na 0
     * 
     * @param id číslo autobusu
     * @param type typ vozidla
     * @param carrier dopravce
     */
    public Bus(int id, String type, String carrier){
        this.id = id;
        this.type = type;
        this.carrier = carrier;
        this.actDelay = 0;
        this.nextStop = new AbstractMap.SimpleEntry<Stop, LocalTime>(null, null);
    }

    /**
     * 
     * @return id autobusu
     */
    public int getId() {
        return this.id;
    }

    /**
     * 
     * @return typ autobusu
     */
    public String getType(){
        return this.type;
    }

    /**
     * 
     * @return dopravce autobusu
     */
    public String getCarrier(){
        return this.carrier;
    }

    /**
     * 
     * @return linku autobusu
     */
    public MyLine getLine(){
        return this.line;
    }

    /**
     * 
     * @return jízdní řád autobusu
     */
    public Schedule getSchedule(){
        return this.schedule;
    }

    /**
     * Nastaví číslo autobus
     * 
     * @param id číslo autobusu
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Nastaví typ autobus
     * 
     * @param type typ autobusu
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * Nastaví dopravce autobusu
     * 
     * @param carrier dopravce autobusu
     */
    public void setCarrier(String carrier){
        this.carrier = carrier;
    }

    /**
     * Přidá linku do autobusu
     * 
     * @param line přidávaná linka
     * @param schedule přidávaný jizdní řád
     */
    public void addLine(MyLine line, Schedule schedule){
        this.line = line;
        this.schedule = schedule;
    }

    /**
     * Získá souřadnice první autobusové zastávky
     * 
     * @return souřadnice první autobusové zastávky
     */
    public Coordinate getFirstStopCoordinate(){
        return this.line.getFirstStop().getCoordinate();
    }

    @Override
    public String toString(){
        return "#"+this.id + " " + this.type + " " + this.carrier + " " + this.line.getId();
    }

    /**
     * Nastaví odkaz na kruh, který autobus reprezentuje na mapě
     *
     * @param circle kruh, kterým je autobus reprezentovaný
     */
    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    /**
     *  Získá kruh reprezentující autobus na mapě
     *
     * @return kruh, reprezentující autobus
     */
    public Circle getCircle(){
        return this.circle;
    }

    /**
     * Zvírazní linka autobusu na mapě
     */
    public void highlightLine() {

        this.line.highlight();
    }

    /**
     * Nastaví pozici autobusu na mapě podle aktuálního času simulace
     *
     * @param currentTime Aktuální čas
     */
    public void updatePos(LocalTime currentTime) {
        if (id == 31)
            System.out.println();

        List<AbstractMap.SimpleEntry<Integer, Long>> detourDelay = new ArrayList<>();

        //Sestavení cesty
        List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> route = this.line.getBusRoute(this.schedule, detourDelay);
        //Získání cest, o kterých autobus jede
        List<Street> streets = this.line.getStreets();
        //spočítání zpoždění trasy
        List<Long> delay = RouteCalculation.computeDelay(route, streets);

        RouteCalculation.addDetourDelay(detourDelay, route, delay);

        //nastavení viditelnostu autobusu
        if (route != null && currentTime.isAfter(route.get(0).getValue()) && currentTime.isBefore(route.get(route.size() - 1).getValue())) {
            this.circle.setVisible(true);
        } else {
            this.circle.setVisible(false);
            return;
        }

        //výpočet aktuální polohy
        int nextStop = schedule.getNextStop(route, currentTime);
        this.nextStop = findNextStop(route, nextStop);

        long routeDuration = Duration.between(route.get(nextStop).getValue(), route.get(nextStop - 1).getValue()).getSeconds();
        long currentDuration = Duration.between(currentTime, route.get(nextStop - 1).getValue()).getSeconds();

        double len = RouteCalculation.routeLenght(route, nextStop - 1, nextStop);
        double currentLen = len * (currentDuration / (double) routeDuration);

        Vec2d newPos = RouteCalculation.getPosition(route.get(nextStop).getKey(), route.get(nextStop - 1).getKey(), currentLen);

        //spočítání aktuálního zpoždění
        actDelay = RouteCalculation.getCurrentDelay(route.get(nextStop).getKey(), route.get(nextStop - 1).getKey(), delay.get(nextStop), delay.get(nextStop - 1), currentLen);

        //nastavení nové polohy
        this.circle.setCenterX(newPos.x);
        this.circle.setCenterY(newPos.y);
    }

    /**
     * Najde další zastávku na trase autobusu
     *
     * @param route Cesta autobusu
     * @param nextNode Index dalšího bodu na cestě
     * @return Další zastávku
     */
    private AbstractMap.SimpleEntry<Stop, LocalTime> findNextStop(List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> route, int nextNode) {
        Stop stop;
        for (int i = nextNode; i < route.size(); i++) {
            if ((stop = schedule.getStopAt(route.get(i).getKey())) != null) {
                return new AbstractMap.SimpleEntry<>(stop, route.get(i).getValue());
            }
        }

        return new AbstractMap.SimpleEntry<>(null, null);
    }

    /**
     * Získá další zastávku autobusu
     *
     * @return zastávka autobusu
     */
    public AbstractMap.SimpleEntry<Stop, LocalTime> getNextStop() {
        return nextStop;
    }

    /**
     * zruší vybrání autobusu
     */
    public void deselect() {
        this.line.deselect();
        circle.setStroke(Color.BLUE);
        circle.setFill(Color.BLUE);
    }

    /**
     * Získá aktuální zpoždění autobusu
     * @return aktuální zpoždění v sekundách
     */
    public long getActDelay() {
        return actDelay;
    }
}