package gui;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import files.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import loaded.*;

public interface MapObjects {
    public static Group drawStreet(){
        MyStreet street;
        Line line = new Line();
        Coordinate[] cList;

        int[] x = {0, 0};
        int[] y = {0, 0};
        int i = 0;


        ArrayList<MyStreet> streets = new ArrayList<MyStreet>();
        Group root = new Group();

        //to vkladani se jeste predela
        street = AddMapItem.setStreet("Kratochv\u00edlova", 0, 0, 0, 200);
        streets.add(street);
        street = AddMapItem.setStreet("ulice2", 0, 200, 200, 200);
        streets.add(street);

        for (MyStreet myStreet : streets) {
            cList = myStreet.getCoordinate();
            for (Coordinate coordinate : cList) {
                x[i] = coordinate.getX();
                y[i] = coordinate.getY();
                i++;
            }
            line = new Line(x[0], y[0], x[1], y[1]);
            line.setStroke(Color.rgb(65, 63, 68));
            line.setStrokeWidth(7);
            root.getChildren().addAll(line);
            i=0;
        }

        return root;
    }

}