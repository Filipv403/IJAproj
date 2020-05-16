package gui;

import javafx.scene.control.ComboBox;
import files.*;

/**
 * Třída s custom comboBoxem pro linky
 * 
 * @author Filip Václavík (xvacla30)
 */
public class LineBox {
    /**
     * ComboBox pro linky
     */
    private ComboBox lineBox = new ComboBox();
    
    /**
     * Konstruktor LineBoxu
     * 
     * @param name jméno comboBoxu
     */
    public LineBox(String name){
        this.lineBox.setPromptText(name);
    }

    /**
     * Vložení prvku do custom comboBoxu přes program
     * 
     * @param id název linky
     */
    public void addLine(String id){
        MyLine line = new MyLine(id);
        this.lineBox.getItems().addAll(line.toString());
    }

    /**
     * Vložení prvku do custom comboBoxu přes soubor
     * 
     * @param line instance třídy linky
     */
    public void addLineFile(MyLine line){
        this.lineBox.getItems().addAll(line.toString());
    }

    /**
     * @return custom lineBox
     */
    public ComboBox getLineBox() {
        return this.lineBox;
    }
}