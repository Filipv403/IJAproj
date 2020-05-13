package gui;

import files.MyStop;
import files.MyStreet;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import simulation.Timers;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import loaded.AddBoxItem;
import javafx.geometry.*;
import gui.*;
import loaded.*;

public class Template {

    public static void displayTemplate(String MHD){
        Stage window = new Stage();
        Loaded data = new Loaded();
        window.setTitle(MHD);

        //Menu
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> {
            window.close();
            System.exit(0);
        });//ukonceni pres Exit a ukonceni pres krizek
        window.setOnCloseRequest(e -> {
            window.close();
            System.exit(0);
        });
        fileMenu.getItems().addAll(exitItem);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);

        //Right Menu
        VBox rightMenu = new VBox();
        rightMenu.setBackground(Background.EMPTY);
        StopBox stops = new StopBox("Zast\u00e1vky"); //u00e1 => á
        LineBox linky = new LineBox("Linky");
        BusBox busBox = new BusBox("Autobusy");

        //plneni pres program
        //stops.addStop("Bayerova");
        //linky.addLine("\u010d\u00edslo 1"); //u010d => č | u00ed => í
        //busBox.addBus(1, "Iveco", "ARRIVA");

        //plneni pres soubor
        busBox = AddBoxItem.itemBus(data, busBox);
        stops = AddBoxItem.itemStop(data, stops);
        linky = AddBoxItem.itemLine(data, linky);

        //comboBox.setOnAction("po kliknutí zvýrazní na mapě");

        rightMenu.getChildren().addAll(stops.getStopBox(), linky.getLineBox(), busBox.getBusBox());
        rightMenu.setId("vbox");
        rightMenu.getStylesheets().addAll("gui/rightMenu.css");

        /*import mapy*/
        Group root = new Group();

        root = MapObjects.drawStreet(data);

        //BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setRight(rightMenu);
        borderPane.setCenter(root);

        //timer
        Timers myTimer = new Timers();
        //myTimer.setGui();
        myTimer.setBusses(data.getBuses());
        myTimer.startTimers();

        Scene scene = new Scene(borderPane, 1280, 720);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE){
                data.getStreets().forEach(MyStreet::deselect);
                data.getStops().forEach(MyStop::deselect);
            }
        });
        window.setScene(scene);
        window.show();
    }
}