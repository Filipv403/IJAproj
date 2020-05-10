package loaded;

import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import files.*;
import gui.*;

public interface Load {
    public static ArrayList<MyStop> loadStops(){
        ArrayList<MyStop> stops = new ArrayList<MyStop>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("./data/Stops.csv"));
            String line = "";
            int lineCNT = 0;
            while((line = br.readLine()) != null){
                if (lineCNT == 0) {
                    lineCNT++;
                    continue;
                }
                lineCNT++;

                String[] strings;
                if(line.contains(",")){
                    strings = line.split(",");
                }else{
                    strings = line.split(";");
                }

                MyStop stop = new MyStop(strings[0], new Coordinate(Integer.parseInt(strings[1]), Integer.parseInt(strings[2])));
                stops.add(stop);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stops;
    }

    public static ArrayList<MyStreet> loadStreets(){
        ArrayList<MyStreet> streets = new ArrayList<MyStreet>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("./data/Streets.csv"));
            String line = "";
            int lineCNT = 0;
            while((line = br.readLine()) != null){
                if (lineCNT == 0) {
                    lineCNT++;
                    continue;
                }
                lineCNT++;

                String[] strings;
                if(line.contains(",")){
                    strings = line.split(",");
                }else{
                    strings = line.split(";");
                }

                MyStreet street = new MyStreet(strings[0], new Coordinate(Integer.parseInt(strings[1]), Integer.parseInt(strings[2])), new Coordinate(Integer.parseInt(strings[3]), Integer.parseInt(strings[4])));
                streets.add(street);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return streets;
    }

    public static ArrayList<MyLine> loadLines(){
        ArrayList<MyLine> lines = new ArrayList<MyLine>();
        ArrayList<MyStreet> streets = new ArrayList<MyStreet>();
        ArrayList<MyStop> stops = new ArrayList<MyStop>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("./data/Lines.csv"));
            String line = "";
            int lineCNT = 0;
            while((line = br.readLine()) != null){
                if (lineCNT == 0) {
                    lineCNT++;
                    continue;
                }
                lineCNT++;

                String[] strings;
                if(line.contains(",")){
                    strings = line.split(",");
                }else{
                    strings = line.split(";");
                }

                streets = Load.loadStreets();
                stops = Load.loadStops();

                MyLine myLine = new MyLine(strings[0]);
                for (int i = 1; i < strings.length; i++) {
                    for (MyStop myStop : stops) {
                        for (MyStreet myStreet : streets) {
                            if(myStreet.addStop(myStop)){
                                myStop.setStreet(myStreet);
                                if(myStreet.getId().equals(strings[i])){
                                    myLine.addStreet(myStreet);
                                    myLine.addStop(myStop);
                                }
                                break;
                            }
                        }
                    }
                }
                lines.add(myLine);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static ArrayList<Bus> loadBuses(){
        ArrayList<Bus> buses = new ArrayList<Bus>();
        ArrayList<MyLine> lines = new ArrayList<MyLine>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("./data/Buses.csv"));
            String line = "";
            int lineCNT = 0;
            while((line = br.readLine()) != null){
                if (lineCNT == 0) {
                    lineCNT++;
                    continue;
                }
                lineCNT++;

                String[] strings;
                if(line.contains(",")){
                    strings = line.split(",");
                }else{
                    strings = line.split(";");
                }

                Bus bus = new Bus(Integer.parseInt(strings[0]), strings[1], strings[2]);
                
                lines = Load.loadLines();
                for (MyLine myLine : lines) {
                    if(myLine.getId().equals(strings[3])){
                        bus.addLine(myLine);
                    }
                }

                buses.add(bus);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buses;
    }
}