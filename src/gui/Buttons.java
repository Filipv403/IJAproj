package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.*;
import files.*;

public class Buttons {
    private Button button = new Button();

    public Buttons(String name){
        this.button.setText(name);
        this.button.setOnAction(e -> System.out.println("Hey its me"));
        this.button.setAlignment(Pos.CENTER_LEFT);
    }

    public Button getButton(){
        return this.button;
    }

}