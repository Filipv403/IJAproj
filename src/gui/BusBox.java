package gui;

import gui.*;
import javafx.scene.control.ComboBox;

import files.*;

public class BusBox {
    private ComboBox busBox = new ComboBox();
    
    public BusBox(String name){
        this.busBox.setPromptText(name);
    }

    public void addBus(int id, String type, String carrier){
        Bus bus = new Bus(id, type, carrier);
        this.busBox.getItems().addAll(bus.toString());
    }

    public void addBusFile(Bus bus){
        this.busBox.getItems().addAll(bus.toString());
    }

    /**
     * @return the busBox
     */
    public ComboBox getBusBox() {
        return this.busBox;
 
    }
}