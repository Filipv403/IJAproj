package files;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.AbstractMap;

import com.sun.javafx.geom.Vec2d;
import files.*;
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

    public Bus(){
        this.id=0;
        this.type="";
        this.carrier="";
        this.delay = 0;
    }

    public Bus(int id, String type, String carrier){
        this.id = id;
        this.type = type;
        this.carrier = carrier;
        this.delay = 0;
    }

    public int getId() {
        return this.id;
    }

    public String getType(){
        return this.type;
    }

    public String getCarrier(){
        return this.carrier;
    }

    public MyLine getLine(){
        return this.line;
    }

    public Schedule getSchedule(){
        return this.schedule;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setCarrier(String carrier){
        this.carrier = carrier;
    }

    public void addLine(MyLine line, Schedule schedule){
        this.line = line;
        this.schedule = schedule;
    }

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

        //nastavení viditelnostu autobusu
        if (route != null && currentTime.isAfter(route.get(0).getValue()) && currentTime.isBefore(route.get(route.size() - 1).getValue())) {
            this.circle.setVisible(true);
        } else {
            this.circle.setVisible(false);
            return;
        }

        //výpočet aktuální polohy
        int nextStop = schedule.getNextStop(route, currentTime);

        long routeDuration = Duration.between(route.get(nextStop).getValue(), route.get(nextStop - 1).getValue()).getSeconds();
        long currentDuration = Duration.between(currentTime, route.get(nextStop - 1).getValue()).getSeconds();

        double len = RouteCalculation.routeLenght(route, nextStop - 1, nextStop);
        double currentLen = len * (currentDuration / (double) routeDuration);

        Vec2d newPos = RouteCalculation.getPosition(route.get(nextStop).getKey(), route.get(nextStop - 1).getKey(), currentLen);

        //nastavení nové polohy
        this.circle.setCenterX(newPos.x);
        this.circle.setCenterY(newPos.y);
    }
}