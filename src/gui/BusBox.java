package gui;

import javafx.scene.control.ComboBox;

import files.*;

/**
 * Třída s custom comboBoxem pro autobusy
 * 
 * @author Filip Václavík (xvacla30)
 */
public class BusBox {
    /**
     * ComboBox pro autobus
     */
    private ComboBox busBox = new ComboBox();
    
    /**
     * Konstruktor BusBoxu
     * 
     * @param name jméno comboBoxu
     */
    public BusBox(String name){
        this.busBox.setPromptText(name);
    }

    /**
     * Vložení prvku do custom comboBoxu přes program
     * 
     * @param id číslo autobusu
     * @param type typ autobusu
     * @param carrier dopravce autobus
     */
    public void addBus(int id, String type, String carrier){
        Bus bus = new Bus(id, type, carrier);
        this.busBox.getItems().addAll(bus.toString());
    }

    /**
     * Vložení prvku do custom comboBoxu přes soubor
     * 
     * @param bus instance třídy autobusu
     */
    public void addBusFile(Bus bus){
        this.busBox.getItems().addAll(bus.toString());
    }

    /**
     * @return custom busBox
     */
    public ComboBox getBusBox() {
        return this.busBox;
 
    }
}