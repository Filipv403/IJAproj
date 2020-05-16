package loaded;

import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;

import files.*;
import gui.*;

/**
 * Rozhraní, které načítá jednotlivé prvky pro mapu, zastávky, ulice, linky, jízdní řád, autobusy
 *
 * @author Filip Václavík (xvacla30)
 * @author Michal Zobaník (xzoban01)
 */
public interface Load {

    /**
     *  Načte seznam zastávek ze souboru, pokud se nenačte tak dovolí uživateli vybrat sám soubor
     *
     * @return ArrayList<MyStop> stops, seznam zastávek
     */
    public static ArrayList<MyStop> loadStops() {
        ArrayList<MyStop> stops = new ArrayList<MyStop>();
        BufferedReader br;
        List<File> files;
        try {
            br = new BufferedReader(new FileReader("./data/Stops.csv"));
            Load.stopCSVRead(stops, br);
        }catch (FileNotFoundException fnfe){
            //nacte ze zadaneho souboru
            files = AlertBox.display("Stops.csv").getFiles();
            for (File file : files) {
                if(file.getName().toUpperCase().contains("STOPS")){
                    try {
                        br = new BufferedReader(new FileReader(file));
                        Load.stopCSVRead(stops, br);
                        break;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        } 

        return stops;
    }

    /**
     *  Dovoluje uživateli načíst soubory již za běhu programu
     * 
     * @param userFiles seznam souborů, které vybral uživatel pro načtení
     *
     * @return ArrayList<MyStop> stops, seznam zastávek
     */
    public static ArrayList<MyStop> loadStops(List<File> userFiles) {
        ArrayList<MyStop> stops = new ArrayList<MyStop>();
        BufferedReader br;
        try {
            for (File file : userFiles) {
                if(file.getName().toUpperCase().contains("STOPS")){
                    try {
                        br = new BufferedReader(new FileReader(file));
                        Load.stopCSVRead(stops, br);
                        break;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return stops;
    }

    /**
     *  Prochází csv soubor se zastávkama a přiřazuje je do listu
     * 
     * @param stops seznam zastávek, do kterého se má ukládat
     * @param br bufferedReader, který čte po řádcích soubor
     *
     */
    public static void stopCSVRead(ArrayList<MyStop> stops, BufferedReader br){
        try {
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
    }

    /**
     *  Načte ulice ze souboru, pokud se nenačtou, tak dovolí uživateli znovu vybrat
     *
     * @return ArrayList<MyStreet> streets, seznam ulic
     */
    public static ArrayList<MyStreet> loadStreets(){
        ArrayList<MyStreet> streets = new ArrayList<MyStreet>();
        BufferedReader br;
        List<File> files;
        try {
            br = new BufferedReader(new FileReader("./data/Streets.csv"));
            Load.streetCSVRead(streets, br);
        }catch (FileNotFoundException fnfe){
            //nacte ze zadaneho souboru
            files = AlertBox.display("Streets.csv").getFiles();
            for (File file : files) {
                if(file.getName().toUpperCase().contains("STREETS")){
                    try {
                        br = new BufferedReader(new FileReader(file));
                        Load.streetCSVRead(streets, br);
                        break;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        } 
        return streets;
    }

    /**
     *  Dovoluje uživateli načíst soubory již za běhu programu
     * 
     * @param userFiles seznam souborů, které vybral uživatel pro načtení
     *
     * @return ArrayList<MyStreet> streets, seznam ulic
     */
    public static ArrayList<MyStreet> loadStreets(List<File> userFiles) {
        ArrayList<MyStreet> streets = new ArrayList<MyStreet>();
        BufferedReader br;
        try {
            for (File file : userFiles) {
                if(file.getName().toUpperCase().contains("STREETS")){
                    try {
                        br = new BufferedReader(new FileReader(file));
                        Load.streetCSVRead(streets, br);
                        break;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return streets;
    }

    /**
     *  Prochází csv soubor s ulicemi a přiřazuje je do listu
     * 
     * @param streets seznam ulic, do kterého se má ukládat
     * @param br bufferedReader, který čte po řádcích soubor
     *
     */
    public static void streetCSVRead(ArrayList<MyStreet> streets, BufferedReader br){
        try {
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
    }

    /**
     *  Načítá ze souboru linky a přiřazuje je do seznamu, zároven k linkám přiřazuje již načtené ulice a zastávky,
     *  pokud se soubor nenačte, dovolí uživateli načíst soubor
     * 
     * @param streets seznam již načtených ulic pro linky
     * @param stops seznam již načtených zastávek pro linky
     *
     * @return ArrayList<MyLine> lines, seznam linek
     */
    public static ArrayList<MyLine> loadLines(ArrayList<MyStreet> streets, ArrayList<MyStop> stops){
        ArrayList<MyLine> lines = new ArrayList<MyLine>();
        //ArrayList<MyStreet> streets = Load.loadStreets();
        //ArrayList<MyStop> stops = Load.loadStops();
        BufferedReader br;
        List<File> files;
        try {
            br = new BufferedReader(new FileReader("./data/Lines.csv"));
            Load.lineCSVRead(streets, stops, lines, br);
        }catch (FileNotFoundException fnfe){
            //nacte ze zadaneho souboru
            files = AlertBox.display("Lines.csv").getFiles();
            for (File file : files) {
                if(file.getName().toUpperCase().contains("LINES")){
                    try {
                        br = new BufferedReader(new FileReader(file));
                        Load.lineCSVRead(streets, stops, lines, br);
                        break;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**
     *  Dovolí uživateli načíst soubory již za běhu.
     *  Načítá ze souboru linky a přiřazuje je do seznamu, zároven k linkám přiřazuje již načtené ulice a zastávky
     * 
     * @param userFiles soubory vybrané uživatelem
     * @param streets seznam již načtených ulic pro linky
     * @param stops seznam již načtených zastávek pro linky
     *
     * @return ArrayList<MyLine> lines, seznam linek
     */
    public static ArrayList<MyLine> loadLines(List<File> userFiles, ArrayList<MyStreet> streets, ArrayList<MyStop> stops) {
        ArrayList<MyLine> lines = new ArrayList<MyLine>();
        BufferedReader br;
        try {
            for (File file : userFiles) {
                if(file.getName().toUpperCase().contains("LINES")){
                    try {
                        br = new BufferedReader(new FileReader(file));
                        Load.lineCSVRead(streets, stops, lines, br);
                        break;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return lines;
    }

    /**
     *  Prochází csv soubor s linkami, přiřazuje je do listu a řadí do linek už již načtené zastávky a ulice
     * 
     * @param streets seznam již načtených ulic
     * @param stops seznam již načtených zastávek
     * @param lines seznam linek, do kterého se má ukládat
     * @param br bufferedReader, který čte po řádcích soubor
     *
     */
    public static void lineCSVRead(ArrayList<MyStreet> streets, ArrayList<MyStop> stops, ArrayList<MyLine> lines, BufferedReader br){
        try {
            for (MyStop myStop : stops) {
                for (MyStreet myStreet : streets) {
                    if (myStreet.addStop(myStop)) {
                        myStop.setStreet(myStreet);
                        break;
                    }
                }
            }
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
    }

    /**
     *  Načítá ze souboru jízdní řády a přiřazuje jednotlivé časy k zastávkám, pokud se nenačte dovoluje uživateli načíst znovu
     * 
     * @param stops seznam již načtených zastávek pro časy jízdního řádu
     *
     * @return ArrayList<Schedule> schedules, jízdní řád
     */
    public static ArrayList<Schedule> loadSchedules(ArrayList<MyStop> stops) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        BufferedReader br;
        List<File> files;
        try {
            br = new BufferedReader(new FileReader("./data/Schedules.csv"));
            Load.scheduleCSVRead(stops, schedules, br);
        }catch (FileNotFoundException fnfe){
            //nacte ze zadaneho souboru
            files = AlertBox.display("Schedules.csv").getFiles();
            for (File file : files) {
                if(file.getName().toUpperCase().contains("SCHEDULES")){
                    try {
                        br = new BufferedReader(new FileReader(file));
                        Load.scheduleCSVRead(stops, schedules, br);
                        break;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
        e.printStackTrace();
        }
        return schedules;
    }

    /**
     *  Dovoluje uživateli načíst potřebný soubor. 
     *  Načítá ze souboru jízdní řády a přiřazuje jednotlivé časy k zastávkám
     * 
     * @param userFiles soubory vybrané uživatelem
     * @param stops seznam již načtených zastávek pro časy jízdního řádu
     *
     * @return ArrayList<Schedule> schedules, jízdní řád
     */
    public static ArrayList<Schedule> loadSchedules(List<File> userFiles, ArrayList<MyStop> stops) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        BufferedReader br;
        try {
            for (File file : userFiles) {
                if(file.getName().toUpperCase().contains("SCHEDULES")){
                    try {
                        br = new BufferedReader(new FileReader(file));
                        Load.scheduleCSVRead(stops, schedules, br);
                        break;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return schedules;
    }

    /**
     *  Prochází csv soubor s jízdním řádem, pro jednotlivé zastávky a přiřazuje jednotlivým zastávkám jejich čas
     * 
     * @param stops seznam již načtených zastávek
     * @param schedules seznam jízdního řádu, pro jednotlivé zastávky
     * @param br bufferedReader, který čte po řádcích soubor
     *
     */
    public static void scheduleCSVRead(ArrayList<MyStop> stops, ArrayList<Schedule> schedules, BufferedReader br){
        try {
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
            br.close();
        } catch (Exception e) {
        e.printStackTrace();
        }
    }

    /**
     *  Načítá ze souboru seznam autobusů, pokud nenačte soubor, dovolí uživateli vybrat
     * 
     * @param lines seznam již načtených linek
     * @param stops seznam již načtených zastávek
     *
     * @return ArrayList<Bus> buses, seznam autobusů
     */
    public static ArrayList<Bus> loadBuses(ArrayList<MyLine> lines, ArrayList<MyStop> stops){
        ArrayList<Bus> buses = new ArrayList<Bus>();
        ArrayList<Schedule> schedules = loadSchedules(stops);
        BufferedReader br;
        List<File> files;
        try {
            br = new BufferedReader(new FileReader("./data/Buses.csv"));
            Load.busCSVRead(lines, stops, buses, schedules, br);
        }catch (FileNotFoundException fnfe){
            //nacte ze zadaneho souboru
            files = AlertBox.display("Buses.csv").getFiles();
            for (File file : files) {
                if(file.getName().toUpperCase().contains("BUSES")){
                    try {
                        br = new BufferedReader(new FileReader(file));
                        Load.busCSVRead(lines, stops, buses, schedules, br);
                        break;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buses;
    }

    /**
     *  Dovoluje uživateli vybrat soubory již za běhu programu
     *  Načítá ze souboru seznam autobusů
     * 
     * @param userFiles soubory vybrané uživatelem
     * @param lines seznam již načtených linek
     * @param stops seznam již načtených zastávek
     *
     * @return ArrayList<Bus> buses, seznam autobusů
     */
    public static ArrayList<Bus> loadBuses(List<File> userFiles, ArrayList<MyLine> lines, ArrayList<MyStop> stops) {
        ArrayList<Bus> buses = new ArrayList<Bus>();
        ArrayList<Schedule> schedules = loadSchedules(userFiles, stops);
        BufferedReader br;
        try {
            for (File file : userFiles) {
                if(file.getName().toUpperCase().contains("BUSES")){
                    try {
                        br = new BufferedReader(new FileReader(file));
                        Load.busCSVRead(lines, stops, buses, schedules, br);
                        break;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return buses;
    }

    /**
     *  Prochází csv soubor s autobusy, přiřazuje jim jména, správné linky s jízdním řádem
     * 
     * @param lines seznam již načtených linek
     * @param stops seznam již načtených zastávek
     * @param buses seznam autobusů, do kterých se má ukládat
     * @param schedules seznam jízdního řádu, pro autobusy
     * @param br bufferedReader, který čte po řádcích soubor
     *
     */
    public static void busCSVRead(ArrayList<MyLine> lines, ArrayList<MyStop> stops, ArrayList<Bus> buses, ArrayList<Schedule> schedules, BufferedReader br){
        try {
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
    }
}