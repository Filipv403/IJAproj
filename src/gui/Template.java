package gui;

import simulation.Timers;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Template {

    public static void displayTemplate(String MHD){
        Stage window = new Stage();
        window.setTitle(MHD);

        VBox rightMenu = new VBox();
        rightMenu.setBackground(Background.EMPTY);
        StopBox stops = new StopBox("Zast\u00e1vky"); //u00e1 => á
        LineBox linky = new LineBox("Linky");
        BusBox busBox = new BusBox("Autobusy");
        stops.addStop("Bayerova");
        linky.addLine("\u010d\u00edslo 1"); //u010d => č | u00ed => í
        busBox.addBus(1, "Iveco", "ARRIVA");

        //comboBox.setOnAction("po kliknutí zvýrazní na mapě");

        rightMenu.getChildren().addAll(stops.getStopBox(), linky.getLineBox(), busBox.getBusBox());
        rightMenu.setId("vbox");
        rightMenu.getStylesheets().addAll("gui/rightMenu.css");

        BorderPane borderPane = new BorderPane();
        borderPane.setRight(rightMenu);

        /*import mapy*/

        Timers myTimer = new Timers();
        //myTimer.setGui();
        myTimer.startTimers();

        Scene scene = new Scene(borderPane, 1280, 720);
        window.setScene(scene);
        window.show();
    }
}