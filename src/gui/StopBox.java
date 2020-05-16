package gui;

import javafx.scene.control.ComboBox;

import files.*;

/**
 * Třída s custom comboBoxem pro zastávky
 * 
 * @author Filip Václavík (xvacla30)
 */
public class StopBox {
    /**
     * ComboBox pro zastávky
     */
    private ComboBox stopBox = new ComboBox();
    
    /**
     * Konstruktor StopBoxu
     * 
     * @param name jméno comboBoxu
     */
    public StopBox(String name){
        this.stopBox.setPromptText(name);
    }

    /**
     * Vložení prvku do custom comboBoxu přes program
     * 
     * @param id název zastávky
     */
    public void addStop(String id){
        MyStop stop = new MyStop(id);
        this.stopBox.getItems().addAll(stop.toString());
    }

    /**
     * Vložení prvku do custom comboBoxu přes soubor
     * 
     * @param stop instance třídy zastávky
     */
    public void addStopFile(MyStop stop){
        this.stopBox.getItems().addAll(stop.toString());
    }

    /**
     * @return custom stopBox
     */
    public ComboBox getStopBox() {
        return this.stopBox;
    }  
}