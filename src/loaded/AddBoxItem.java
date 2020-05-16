package loaded;

import java.util.ArrayList;

import files.*;
import gui.*;

/**
 * Rozhraní slouží pro přidání objektů z načtených seznamů do custom vytvořených comboboxů, busbox, stopbox a linebox
 *
 * @author Filip Václavík (xvacla30)
 */
public interface AddBoxItem {
    /**
     * Prochází seznam s autobusy a přidává je do busBoxu
     * 
     * @param l načtená instance třídy s daty
     * @param busBox custom vytvořený comboBox pro autobusy
     * @return busBox
     */
    public static BusBox itemBus(Loaded l, BusBox busBox){
        ArrayList<Bus> buses = new ArrayList<Bus>();
        buses = l.getBuses();

        for (Bus bus : buses) {
            //vlozeni
            busBox.addBusFile(bus);
        }

        return busBox;
    }

    /**
     * Prochází seznam se zastávkami a přidává je do stopBoxu
     * 
     * @param l načtená instance třídy s daty
     * @param stopBox custom vytvořený comboBox pro zastávky
     * @return stopBox
     */
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

    /**
     * Prochází seznam s linkami a přidává je do lineBoxu
     * 
     * @param l načtená instance třídy s daty
     * @param lineBox custom vytvořený comboBox pro linky
     * @return lineBox
     */
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