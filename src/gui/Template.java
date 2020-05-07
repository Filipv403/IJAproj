package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.*;
import gui.*;

public class Template {

    public static void displayTemplate(String MHD){
        Stage window = new Stage();
        window.setTitle(MHD);

        VBox rightMenu = new VBox();
        rightMenu.setBackground(Background.EMPTY);
        Buttons linky = new Buttons("Linky");
        Buttons spoje = new Buttons("Spoje");
        Buttons bus = new Buttons("Autobusy");
        rightMenu.getChildren().addAll(linky.getButton(), spoje.getButton(), bus.getButton());
        /*rightMenu.setAlignment(Pos.CENTER);*/
        rightMenu.setId("vbox");
        rightMenu.getStylesheets().addAll("gui/rightMenu.css");

        BorderPane borderPane = new BorderPane();
        borderPane.setRight(rightMenu);

        Scene scene = new Scene(borderPane, 1280, 720);
        window.setScene(scene);
        window.show();
    }
}