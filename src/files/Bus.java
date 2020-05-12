package files;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap;

import com.sun.javafx.geom.Vec2d;
import files.*;
import javafx.scene.shape.Circle;
import simulation.RouteCalculation;

/**
 * @author xvacla30
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

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public void updatePos(LocalTime currentTime) {
        if (this.schedule.isOnRoute(currentTime)) {
            this.circle.setVisible(true);
        } else {
            this.circle.setVisible(false);
            return;
        }

        prevTime = currentTime;

        Stop nextStop = schedule.getNextStop(currentTime);
        Stop prevStop = schedule.getPreviousStop(currentTime);

        ArrayList<Coordinate> route = this.line.getRoute(prevStop, nextStop);

        long routeDuration = Duration.between(schedule.getTime(nextStop), schedule.getTime(prevStop)).getSeconds();
        long currentDuration = Duration.between(currentTime, schedule.getTime(prevStop)).getSeconds();

        double len = RouteCalculation.routeLenght(route);
        double currentLen = len * (currentDuration / (double) routeDuration);

        Vec2d newPos = RouteCalculation.getPosition(route, currentLen);


        this.circle.setCenterX(newPos.x);
        this.circle.setCenterY(newPos.y);
    }
}