package gui;

import java.util.ArrayList;
import files.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import loaded.*;

/**
 * Rozhraní pro vykreslení objektů na mapě
 *
 * @author Filip Václavík (xvacla30)
 * @author Michal Zobaník (xzoban01)
 */
public interface MapObjects {
    /**
     * Vykreslí mapu
     * 
     * @param gridPane panel na který se má umístit mapa a vyskakovací okénko
     * @param l data ze souborů pro ulice apod.
     * @return mapa s objekty
     */
    public static Group drawStreet(GridPane gridPane, Loaded l, AppData appData){
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
            line.setOnMouseClicked(e -> {
                if(!myStreet.isSelected()){
                    appData.getStreetSetText().setText("Nastavení Ulice: " + myStreet.getId());
                    myStreet.setSelected(true);
                    if(myStreet.isOpen()){
                        //pokud je otevrena po kliknuti
                        appData.getCheckBox().setSelected(true);
                        appData.getCheckBox().setText("Otevřená");
                        myStreet.getMapLine().setStroke(Color.rgb(85, 255, 0));
                        appData.getCheckBox().setOnAction(ex-> {
                            myStreet.setOpen(!myStreet.isOpen());
                            if(appData.getCheckBox().isSelected()){
                                appData.getCheckBox().setText("Otevřená");
                            }else{
                                appData.getCheckBox().setText("Zavřená");
                            }
                        });
                    }else{
                        //pokud je zavrena po kliknuti
                        appData.getCheckBox().setSelected(false);
                        appData.getCheckBox().setText("Zavřená");
                        myStreet.getMapLine().setStroke(Color.RED);
                        appData.getCheckBox().setOnAction(ex-> {
                            myStreet.setOpen(!myStreet.isOpen());
                            if(appData.getCheckBox().isSelected()){
                                appData.getCheckBox().setText("Otevřená");
                            }else{
                                appData.getCheckBox().setText("Zavřená");
                            }
                        });
                    }
                    //event, po každé změně v hodnotě spinneru, změní provoz instance třídy
                    /*
                    appData.getTrafficSpinner.event(e -> {
                        final Spinner<Integer> trafficSpinner = appData.getTrafficSpinner();
                        myStreet.setTraffic(trafficSpinner.getValue());
                    });
                    */
                }else{
                    //odkliknuti silnice
                    myStreet.setSelected(false);
                    appData.getCheckBox().setSelected(false);
                    appData.getCheckBox().setText("Zavřená");
                    appData.getStreetSetText().setText("Nastavení Ulice: Žádná nevybrána");
                    myStreet.getMapLine().setStroke(Color.rgb(65, 63, 68));
                    myStreet.deselect();
                }
            });
            myStreet.setMapLine(line);
            root.getChildren().addAll(myStreet.getMapLine());

            //vykresleni a pridani zastavek
            for (MyStop myStop : stops) {
                bool = myStreet.addStop(myStop);
                if(bool){
                    rectangle = new Rectangle(myStop.getCoordinate().getX(), myStop.getCoordinate().getY(), 5, 5);
                    rectangle.setStroke(Color.RED);
                    rectangle.setFill(Color.RED);
                    myStop.setMapRect(rectangle);
                    root.getChildren().addAll(myStop.getMapRect(), new Text(myStop.getCoordinate().getX(), myStop.getCoordinate().getY(), myStop.getId()));
                }
            }
            i=0;
        }

        //vykresleni autobusu
        for (Bus bus : buses) {
            circle = new Circle(bus.getFirstStopCoordinate().getX(), bus.getFirstStopCoordinate().getY(), 7);
            circle.setStroke(Color.BLUE);
            circle.setFill(Color.BLUE);
            circle.setOnMouseClicked(e -> {
                //l.getStreets().forEach(MyStreet::deselect);
                //l.getStops().forEach(MyStop::deselect);
                appData.deselectBus();
                appData.setSelectedBus(bus);
                bus.highlightLine();
                MyPopup myPopup = new MyPopup();
                myPopup.load(bus);
                myPopup.display(gridPane);
                appData.setPopup(myPopup);
                //bus.setPopUp(myPopup);
            });
            bus.setCircle(circle);
            root.getChildren().addAll(bus.getCircle());
        }
        return root;
    }
}