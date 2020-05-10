package gui;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import files.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import loaded.*;

public interface MapObjects {
    public static Group drawStreet(){
        MyStreet street;
        Line line = new Line();
        Rectangle rectangle = new Rectangle();
        Circle circle = new Circle();
        Coordinate[] cList;

        int[] x = {0, 0};
        int[] y = {0, 0};
        int i = 0;


        ArrayList<MyStreet> streets = new ArrayList<MyStreet>();
        ArrayList<MyStop> stops = new ArrayList<MyStop>();
        ArrayList<Bus> buses = new ArrayList<Bus>();
        Group root = new Group();
        Loaded l = new Loaded();
        Boolean bool;
        
        //vlozeni
        streets = l.getStreets();
        stops = l.getStops();
        buses = l.getBuses();

        for (MyStreet myStreet : streets) {
            cList = myStreet.getCoordinate();
            for (Coordinate coordinate : cList) {
                x[i] = coordinate.getX();
                y[i] = coordinate.getY();
                i++;
            }
            //vykresleni ulic
            line = new Line(x[0], y[0], x[1], y[1]);
            line.setStroke(Color.rgb(65, 63, 68));
            line.setStrokeWidth(7);
            root.getChildren().addAll(line);
            //vykresleni a pridani zastavek
            for (MyStop myStop : stops) {
                bool = myStreet.addStop(myStop);
                if(bool){
                    rectangle = new Rectangle(myStop.getCoordinate().getX(), myStop.getCoordinate().getY(), 5, 5);
                    rectangle.setStroke(Color.RED);
                    rectangle.setFill(Color.RED);
                    root.getChildren().addAll(rectangle);
                }
            }
            //vykresleni autobusu
            for (Bus bus : buses) {
                circle = new Circle(bus.getFirstStopCoordinate().getX(), bus.getFirstStopCoordinate().getY(), 7);
                circle.setStroke(Color.BLUE);
                circle.setFill(Color.BLUE);
                root.getChildren().addAll(circle);
            }   
            i=0;
        }

        return root;
    }

}