package gui;

import java.util.List;
import java.io.File;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import loaded.*;

/**
 * Prázdný template, který se zobrazí, když se nenačtou žádné soubory
 * 
 * @author Filip Václavík (xvacla30)
 */
public class EmptyTemplate {

    /**
     * Zobrazí prázdný template
     * 
     * @param MHD název prázdného templatu
     */
    public static void display(String MHD){
        Stage window = new Stage();
        window.setTitle(MHD);
    
        //Menu
        Menu fileMenuE = new Menu("File");
        MenuItem exitItemE = new MenuItem("Exit");
        exitItemE.setOnAction(e -> {
            window.close();
            System.exit(0);
        });//ukonceni pres Exit a ukonceni pres krizek
        window.setOnCloseRequest(e -> {
            window.close();
            System.exit(0);
        });
        MenuItem openItemE = new MenuItem("Open");
    
        //user input
        openItemE.setOnAction(e -> {
            List<File> files; 
            files = AlertBox.displayA("Stops.csv, Streets.csv, Lines.csv, Buses.csv, Schedules.csv").getFiles();
            if(files != null){
                final Loaded userData = new Loaded(files);
                window.close();
                Template.displayTemplate(userData, MHD);
            }
        });
    
        fileMenuE.getItems().addAll(openItemE, exitItemE);
        MenuBar menuBarE = new MenuBar();
        menuBarE.getMenus().add(fileMenuE);
    
        //Right Menu
        VBox rightMenuEmpty = new VBox();
        rightMenuEmpty.setBackground(Background.EMPTY);
        StopBox stops = new StopBox("Zast\u00e1vky"); //u00e1 => á
        LineBox linky = new LineBox("Linky");
        BusBox busBox = new BusBox("Autobusy");
    
        stops.addStop(" ");
        linky.addLine(" "); //u010d => č | u00ed => í
        busBox.addBus(0, " ", " ");
    
        rightMenuEmpty.getChildren().addAll(stops.getStopBox(), linky.getLineBox(), busBox.getBusBox());
        rightMenuEmpty.setId("empty-vbox");
        rightMenuEmpty.getStylesheets().addAll("gui/rightMenu.css");
    
        //GridPane
        GridPane gridPaneEmpty = new GridPane();
        gridPaneEmpty.add(menuBarE, 0, 0, 2, 1);
        gridPaneEmpty.add(rightMenuEmpty, 1, 1, 1, 2);
    
        gridPaneEmpty.getColumnConstraints().add(new ColumnConstraints(1160));
        gridPaneEmpty.getColumnConstraints().add(new ColumnConstraints(440));
        gridPaneEmpty.getRowConstraints().add(new RowConstraints(32));
        gridPaneEmpty.getRowConstraints().add(new RowConstraints(668));
    
    
    
        /*import mapy*/
        Group rootEmpty = new Group();
    
        gridPaneEmpty.add(rootEmpty, 0, 1);
    
        Scene scene = new Scene(gridPaneEmpty, 1600, 900);
        window.setScene(scene);
        window.showAndWait();
    }
    
}