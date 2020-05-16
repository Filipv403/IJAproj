package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;

/**
 * Třída pro custom tlačítko
 * 
 * @author Filip Václavík (xvacla30)
 */
public class Buttons {
    /**
     * nové tlačítko
     */
    private Button button = new Button();

    /**
     * Konstruktor tlačítka
     * 
     * @param name název tlačítka
     */
    public Buttons(String name){
        this.button.setText(name);
        this.button.setOnAction(e -> System.out.println(""));
        this.button.setAlignment(Pos.CENTER_LEFT);
    }

    /**
     * 
     * @return vytvořené tlačítko
     */
    public Button getButton(){
        return this.button;
    }

}