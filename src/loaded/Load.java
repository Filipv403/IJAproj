package loaded;

import java.time.LocalTime;
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
                street.setOpen(Boolean.parseBoolean(strings[5]));
                street.setTraffic(Integer.parseInt(strings[6]));
                streets.add(street);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return streets;
    }

    public static ArrayList<MyLine> loadLines(ArrayList<MyStreet> streets, ArrayList<MyStop> stops){
        ArrayList<MyLine> lines = new ArrayList<MyLine>();
        //ArrayList<MyStreet> streets = Load.loadStreets();
        //ArrayList<MyStop> stops = Load.loadStops();
        try {
            for (MyStop myStop : stops) {
                for (MyStreet myStreet : streets) {
                    if (myStreet.addStop(myStop)) {
                        myStop.setStreet(myStreet);
                        break;
                    }
                }
            }

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

                MyLine myLine = new MyLine(strings[0]);

                for (int i = 1; i < strings.length; i++) {
                    boolean added = false;
                    for (MyStop myStop : stops) {
                        if (myStop.getId().equals(strings[i])) {
                            if (!myLine.addStop(myStop))
                                throw new Exception();
                            added = true;
                            break;
                        }
                    }

                    if (added)
                        continue;

                    for (MyStreet myStreet : streets) {
                        if (myStreet.getId().equals(strings[i])) {
                            if (!myLine.addStreet(myStreet))
                                throw new Exception();
                            break;
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

    public static ArrayList<Schedule> loadSchedules(ArrayList<MyStop> stops) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("./data/Schedules.csv"));
            String line = "";
            int lineCNT = 0;
            while((line = br.readLine()) != null) {
                if (lineCNT == 0) {
                    lineCNT++;
                    continue;
                }
                lineCNT++;

                String[] strings;
                if (line.contains(",")) {
                    strings = line.split(",");
                } else {
                    strings = line.split(";");
                }

                Schedule schedule = new Schedule(Integer.parseInt(strings[0]));


                for (int i = 1; i < strings.length - 1; i+=2) {
                    for (Stop stop: stops) {
                        if (stop.getId().equals(strings[i + 1])){
                            schedule.addStop(LocalTime.parse(strings[i]), stop);
                            break;
                        }
                    }
                }

                schedules.add(schedule);
            }
        } catch (Exception e) {
        e.printStackTrace();
    }
        return schedules;
    }

    public static ArrayList<Bus> loadBuses(ArrayList<MyLine> lines, ArrayList<MyStop> stops){
        ArrayList<Bus> buses = new ArrayList<Bus>();
        ArrayList<Schedule> schedules = loadSchedules(stops);
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
                Schedule mySchedule = null;
                for (Schedule schedule: schedules) {
                    if(schedule.getBusID() == bus.getId()) {
                        mySchedule = schedule;
                        break;
                    }
                }

                for (MyLine myLine : lines) {
                    if(myLine.getId().equals(strings[3])){
                        bus.addLine(myLine, mySchedule);
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