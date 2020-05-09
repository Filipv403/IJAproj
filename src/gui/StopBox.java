package gui;

import gui.*;
import javafx.scene.control.ComboBox;

import files.*;

public class StopBox {
    private ComboBox stopBox = new ComboBox();
    
    public StopBox(String name){
        this.stopBox.setPromptText(name);
    }

    public void addStop(String id){
        MyStop stop = new MyStop(id);
        this.stopBox.getItems().addAll(stop.toString());
    }

    public void addStopFile(MyStop stop){
        this.stopBox.getItems().addAll(stop.toString());
    }

    /**
     * @return the stopBox
     */
    public ComboBox getStopBox() {
        return this.stopBox;
    }  
}