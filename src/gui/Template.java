package gui;

import java.awt.*;
import java.util.List;

import files.*;
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
        openCheckBox.setOnAction(ex-> {
            if (appData.getSelectedStreet() != null && appData.getSelectedStreet().isCloseable()) {
                appData.getSelectedStreet().setOpen(!appData.getSelectedStreet().isOpen());
            }
        });
        Spinner<Integer> trafficSpinner = new Spinner<>(1, 100, 1, 1);
        trafficSpinner.setEditable(true);
        trafficSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (appData.getSelectedStreet() != null) {
                appData.getSelectedStreet().setTraffic(newValue);
            }
        });
        rightMenu.getChildren().addAll(new Separator(), streetSetText, openCheckBox, trafficSpinner, new Separator());
        appData.setCheckBox(openCheckBox);
        appData.setTrafficSpinner(trafficSpinner);
        appData.setStreetSetText(streetSetText);

        //objížďky
        Label detourSetText = new Label("Nastavení objížďky pro linku:\n\tŽádná nevybrána");
        ComboBox<Detour> detourCBox = new ComboBox<Detour>();
        detourCBox.setPromptText("Seznam Objížděk");

        Button removeDetour = new Button("Odstranit objížďku");
        Button createDetour = new Button("Přidat objížďku");

        TextField delayDet = new TextField("30");
        HBox detourDelayBox = new HBox(new Label("Zpoždění(minuty): "), delayDet);

        createDetour.setOnMouseClicked(e -> {
            if (appData.isEditingDetour()) {
                appData.finalizeDetour();
                if (appData.getNewDetour() != null)
                    appData.getSelectedLine().getDetours().add(appData.getNewDetour());
                appData.getSelectedLine().highlight();

                appData.getDetourComboBox().getItems().remove(0, appData.getDetourComboBox().getItems().size());

                try {
                    appData.getNewDetour().setDelay((long)Double.parseDouble(delayDet.getText()) * 60);
                } catch (NumberFormatException exception) {
                    appData.getNewDetour().setDelay(30 * 60);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Přidání objížďky");
                    alert.setHeaderText(null);
                    alert.setContentText("Zpoždění je ve špatném formátu. Nastaveno defaultní zpoždění 30 minut.");

                    alert.showAndWait();
                }

                if (appData.getSelectedLine().getDetours().size() > 0)
                    appData.getDetourComboBox().getItems().addAll(appData.getSelectedLine().getDetours());
                removeDetour.setDisable(false);
                detourCBox.setDisable(false);
                appData.toggleEditDetour();
            }
            else if (appData.getSelectedLine() != null) {
                appData.createDetour();
                removeDetour.setDisable(true);
                detourCBox.setDisable(true);
                appData.toggleEditDetour();
            }
        });
        appData.setLineSetText(detourSetText);
        appData.setDetourComboBox(detourCBox);
        appData.setRemoveDetour(removeDetour);

        removeDetour.setOnMouseClicked(e -> {
            if (appData.getSelectedLine() != null && appData.getDetourComboBox().getValue() != null) {
                appData.getDetourComboBox().getValue().remove();
                appData.getSelectedLine().getDetours().remove(appData.getDetourComboBox().getValue());
                appData.getDetourComboBox().getItems().remove(appData.getDetourComboBox().getValue());

                appData.getSelectedLine().deselect();
                appData.getSelectedLine().highlight();
            }
        });

        rightMenu.getChildren().addAll(detourSetText, detourCBox,removeDetour, detourDelayBox, createDetour);

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
                if (appData.isEditingDetour()) {
                    appData.cancleDetourEdit();
                } else {
                    appData.deselectBus();
                    appData.deselectStreet();
                }
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