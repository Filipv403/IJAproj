package files;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.AbstractMap;

import com.sun.javafx.geom.Vec2d;
import gui.MyPopup;
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
    private int delay;
    private LocalTime prevTime = LocalTime.of(9,0);
    private Stop nextStop;
    private MyPopup popUp;

    /**
     * Konstruktor pro Autobus
     * Nastaví číslo a zpoždění na 0 a z typu a dopravce udělá prázdné řetězce
     */
    public Bus(){
        this.id=0;
        this.type="";
        this.carrier="";
        this.delay = 0;
        this.nextStop = null;
        this.popUp = null;
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
        this.delay = 0;
        this.nextStop = null;
        this.popUp = null;
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
        return "#"+this.id + " " + this.type + " " + this.carrier + " " + this.line;
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
        //Sestavení cesty
        List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> route = this.line.getBusRoute(this.schedule);
        //Získání cest, o kterých autobus jede
        List<Street> streets = this.line.getStreets();
        //spočítání zpoždění
        List<Long> delay = RouteCalculation.computeDelay(route, streets);

        this.nextStop = getLine().getFirstStop();
        if (this.popUp != null) {
            //this.popUp.update(this);
        }

        //nastavení viditelnostu autobusu
        if (route != null && currentTime.isAfter(route.get(0).getValue()) && currentTime.isBefore(route.get(route.size() - 1).getValue())) {
            this.circle.setVisible(true);
        } else {
            deselect();
            this.circle.setVisible(false);
            return;
        }

        //výpočet aktuální polohy
        int nextStop = schedule.getNextStop(route, currentTime);
        this.nextStop = findNextStop(route, nextStop);

        if (this.popUp != null)
            this.popUp.update();

        long routeDuration = Duration.between(route.get(nextStop).getValue(), route.get(nextStop - 1).getValue()).getSeconds();
        long currentDuration = Duration.between(currentTime, route.get(nextStop - 1).getValue()).getSeconds();

        double len = RouteCalculation.routeLenght(route, nextStop - 1, nextStop);
        double currentLen = len * (currentDuration / (double) routeDuration);

        Vec2d newPos = RouteCalculation.getPosition(route.get(nextStop).getKey(), route.get(nextStop - 1).getKey(), currentLen);

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
    private Stop findNextStop(List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> route, int nextNode) {
        Stop stop;
        for (int i = nextNode; i < route.size(); i++) {
            if ((stop = schedule.getStopAt(route.get(i).getKey())) != null) {
                return stop;
            }
        }

        return line.getFirstStop();
    }

    /**
     * Získá další zastávku autobusu
     *
     * @return zastávka autobusu
     */
    public Stop getNextStop() {
        return nextStop;
    }

    /**
     * Nastaví vyskakovací okno
     * @param popUp vyskakovací okno
     */
    public void setPopUp(MyPopup popUp) {
        this.popUp = popUp;
    }

    /**
     * odstraní vyskakovací okno
     */
    public void deselect() {
        if (this.popUp != null) {
            this.popUp.notDisplay();
            this.popUp = null;
        }
    }
}