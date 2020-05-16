package loaded;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

import files.*;

/**
 * Třída s načtenými zastávkami, ulicemi, linkami i autobusy
 *
 * @author Filip Václavík (xvacla30)
 */
public class Loaded {
    /**
    * seznam zastávek, ulic, linek a autobusů
    */
    private ArrayList<MyStop> myStops = new ArrayList<MyStop>();
    private ArrayList<MyStreet> myStreets = new ArrayList<MyStreet>();
    private ArrayList<MyLine> myLines = new ArrayList<MyLine>();
    private ArrayList<Bus> Buses = new ArrayList<Bus>();

    /**
     * Konstruktor pro třídu Loaded
     * Využije statické metody rozhraní Load a načte ulice, zastávky, linky a autobusy
     */
    public Loaded(){
        this.myStops = Load.loadStops();
        this.myStreets = Load.loadStreets();
        this.myLines = Load.loadLines(this.myStreets, this.myStops);
        this.Buses = Load.loadBuses(this.myLines, this.myStops);
    }

    /**
     * Konstruktor pro třídu Loaded
     * Využije statické metody rozhraní Load a načte ulice, zastávky, linky a autobusy
     * 
     * @param filesUser soubory předané uživatelem za běhu programu
     */
    public Loaded(List<File> filesUser){
        if(filesUser.size() == 5 ){
            this.myStops = Load.loadStops(filesUser);
            this.myStreets = Load.loadStreets(filesUser);
            this.myLines = Load.loadLines(filesUser, this.myStreets, this.myStops);
            this.Buses = Load.loadBuses(filesUser, this.myLines, this.myStops);
        }
    }

    /**
     * @return seznam zastávek
     */
    public ArrayList<MyStop> getStops(){
        return this.myStops;
    }

    /**
     * @return seznam ulic
     */
    public ArrayList<MyStreet> getStreets(){
        return this.myStreets;
    }

    /**
     * @return seznam linek
     */
    public ArrayList<MyLine> getLines(){
        return this.myLines;
    }

    /**
     * @return seznam autobusů
     */
    public ArrayList<Bus> getBuses(){
        return this.Buses;
    }

    /**
     * Nahradí prvky již ve vytvořené třídy Loaded uživatelským inputem té stejné třídy
     * 
     * @param userInput uživatelem načtený input se soubory
     * @return userInput, vrací předanou třídu
     */
    public Loaded userInput(Loaded userInput){
        this.myStreets = userInput.getStreets();
        this.myStops = userInput.getStops();
        this.Buses = userInput.getBuses();
        this.myLines = userInput.getLines();
        return userInput;
    }

}