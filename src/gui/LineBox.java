package gui;

import gui.*;
import javafx.scene.control.ComboBox;
import javafx.scene.shape.Line;
import files.*;

public class LineBox {
    private ComboBox lineBox = new ComboBox();
    
    public LineBox(String name){
        this.lineBox.setPromptText(name);
    }

    public void addLine(String id){
        MyLine line = new MyLine(id);
        this.lineBox.getItems().addAll(line.toString());
    }

    /**
     * @return the lineBox
     */
    public ComboBox getLineBox() {
        return this.lineBox;
    }
}