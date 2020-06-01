package gui;

import java.awt.*;
import java.util.List;

import files.AppData;
import files.Bus;
import files.MyStop;
import files.MyStreet;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import simulation.Timers;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;

import loaded.*;

/**
 * Třídá s templatem programu
 *
 * @author Filip Václavík (xvacla30)
 * @author Michal Zobaník (xzoban01)
 */
public class Template {
    /**
     * Zobrazí template
     * 
     * @param data načtená data ze souborů
     * @param MHD název okna pro template
     */
    public static void displayTemplate(Loaded data, AppData appData, String MHD){
        Stage window = new Stage();
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
        MenuItem openItem = new MenuItem("Open");

        //user input
        openItem.setOnAction(e -> {
            List<File> files; 
            files = AlertBox.displayA("Stops.csv, Streets.csv, Lines.csv, Buses.csv, Schedules.csv").getFiles();
            if(files != null){
                final Loaded userData = new Loaded(files);
                data.userInput(userData);
                window.close();
                displayTemplate(userData, appData, MHD);
            }
        });

        fileMenu.getItems().addAll(openItem, exitItem);
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

        //zobrazeni casu, zrychování
        Timers myTimer = new Timers(appData);

        Label currentTimeLabel = new Label("Aktuální čas: 10:00");
        Label currentSimSpeed = new Label("Rychlost simulace: 1x");
        TextField time = new TextField();
        Button setTime = new Button("Nastavit čas");
        setTime.setOnMouseClicked(e -> myTimer.setCurrentTime());
        Slider simSpeedSlider = new Slider();
        simSpeedSlider.setMin(1);
        simSpeedSlider.setMax(3600);
        simSpeedSlider.setOnMouseDragged(e -> myTimer.setSimSpeed());

        rightMenu.getChildren().addAll(currentTimeLabel, time, setTime, currentSimSpeed, simSpeedSlider);
        myTimer.setGui(time, currentSimSpeed, currentTimeLabel, simSpeedSlider);

        //nastavení ulice
        Label streetSetText = new Label("Nastavení Ulice: Žádná nevybrána");
        CheckBox openCheckBox = new CheckBox("Zavřená");
        Spinner<Integer> trafficSpinner = new Spinner<>(1, 100, 1, 1);
        SpinnerValueFactory.IntegerSpinnerValueFactory intFactory =
                (SpinnerValueFactory.IntegerSpinnerValueFactory) trafficSpinner.getValueFactory();
        rightMenu.getChildren().addAll(new Separator(), streetSetText, openCheckBox, trafficSpinner, new Separator());
        appData.setCheckBox(openCheckBox);
        appData.setTrafficSpinner(trafficSpinner);
        appData.setStreetSetText(streetSetText);

        appData.getCheckBox().setOnAction(ex-> {
            if(appData.getCheckBox().isSelected()){
                appData.getCheckBox().setText("Otevřená");
            }else{
                appData.getCheckBox().setText("Zavřená");
            }
        });

        //objížďky
        Label detourSetText = new Label("Nastavení objížďky pro linku:\n\tŽádná nevybrána");
        ComboBox<Label> detourCBox = new ComboBox<Label>();
        detourCBox.setPromptText("Seznam Objížděk");
        detourCBox.getItems().addAll(new Label("Test label"));
        Button createDetour = new Button("Přidat objížďku");
        Button removeDetour = new Button("Odstranit objížďku");


        rightMenu.getChildren().addAll(detourSetText, detourCBox, createDetour, removeDetour);

        //rightMenu.getChildren().addAll(stops.getStopBox(), linky.getLineBox(), busBox.getBusBox());
        rightMenu.setId("vbox");
        rightMenu.getStylesheets().addAll("gui/rightMenu.css");

        //GridPane
        GridPane gridPane = new GridPane();
        gridPane.add(menuBar, 0, 0, 2, 1);
        gridPane.add(rightMenu, 1, 1, 1, 2);

        gridPane.getColumnConstraints().add(new ColumnConstraints(1160));
        gridPane.getColumnConstraints().add(new ColumnConstraints(440));
        gridPane.getRowConstraints().add(new RowConstraints(32));
        gridPane.getRowConstraints().add(new RowConstraints(668));

        /*import mapy*/
        Group root = new Group();

        root = MapObjects.drawStreet(gridPane, data, appData);

        myTimer.setBusses(data.getBuses());
        myTimer.startTimers();

        ScrollPane map = new ScrollPane();
        map.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        map.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        map.setContent(root);
        map.setPannable(true);
        map.getStylesheets().addAll("gui/map.css");

        gridPane.add(map, 0, 1);

        createZoom(map, root);

        Scene scene = new Scene(gridPane, 1600, 900);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE){
                appData.deselectBus();
            }
        });
        window.setScene(scene);
        window.show();
    }

    /**
     * Statická metoda, která přidá zoom k mapě, která se nachází uvnitř scroll panu
     * 
     * @param map mapa pro zoomovaní
     * @param group plátno, na které jsou objekty uvnitř map
     */
    public static void createZoom(ScrollPane map, final Group group) {
        final double SCALE_DELTA = 1.1;

        map.setOnScroll(new EventHandler<ScrollEvent>() {
          @Override public void handle(ScrollEvent event) {
            event.consume();
    
            double scaleFactor =
              (event.getDeltaY() > 0)
                ? SCALE_DELTA
                : 1/SCALE_DELTA;
    
            group.setScaleX(group.getScaleX() * scaleFactor);
            group.setScaleY(group.getScaleY() * scaleFactor);
          }
        });
    }
}