package loaded;

import java.util.List;
import java.util.ArrayList;

import files.*;
import gui.*;
import loaded.Load;

public class Loaded {
    private ArrayList<MyStop> myStops = new ArrayList<MyStop>();
    private ArrayList<MyStreet> myStreets = new ArrayList<MyStreet>();
    private ArrayList<MyLine> myLines = new ArrayList<MyLine>();
    private ArrayList<Bus> Buses = new ArrayList<Bus>();

    public Loaded(){
        this.myStops = Load.loadStops();
        this.myStreets = Load.loadStreets();
        this.myLines = Load.loadLines(this.myStreets, this.myStops);
        this.Buses = Load.loadBuses(this.myLines, this.myStops);
    }

    public ArrayList<MyStop> getStops(){
        return this.myStops;
    }

    public ArrayList<MyStreet> getStreets(){
        return this.myStreets;
    }

    public ArrayList<MyLine> getLines(){
        return this.myLines;
    }

    public ArrayList<Bus> getBuses(){
        return this.Buses;
    }

}