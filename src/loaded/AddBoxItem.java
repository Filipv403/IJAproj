package loaded;

import java.util.ArrayList;

import files.*;
import gui.*;

public interface AddBoxItem {
    public static BusBox itemBus(Loaded l, BusBox busBox){
        ArrayList<Bus> buses = new ArrayList<Bus>();
        buses = l.getBuses();

        for (Bus bus : buses) {
            //vlozeni
            busBox.addBusFile(bus);
        }

        return busBox;
    }

    public static StopBox itemStop(Loaded l, StopBox stopBox){

        //nacteni zastavek ze souboru
        ArrayList<MyStop> stops = new ArrayList<MyStop>();
        ArrayList<MyStreet> streets = new ArrayList<MyStreet>();
        stops = l.getStops();
        streets = l.getStreets();
        //vlozeni
        for (MyStop myStop : stops) {
            for (MyStreet myStreet : streets) {
                if(myStreet.addStop(myStop)){
                    myStop.setStreet(myStreet);
                    stopBox.addStopFile(myStop);
                    break;
                }
            }
        }

        return stopBox;
    }

    public static LineBox itemLine(Loaded l, LineBox lineBox){
        ArrayList<MyStop> stops = new ArrayList<MyStop>();
        ArrayList<MyStreet> streets = new ArrayList<MyStreet>();
        ArrayList<MyLine> lines = new ArrayList<MyLine>();
        stops = l.getStops();
        streets = l.getStreets();
        lines = l.getLines();
        
        //vlozeni
        for (MyLine myLine : lines) {
            lineBox.addLineFile(myLine);
        }

        return lineBox;
    }

}