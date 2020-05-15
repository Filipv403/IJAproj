package gui;

import java.time.LocalTime;

import files.*;
import simulation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MyPopup {
    private SplitPane popup;

    public SplitPane load(Bus bus) {
        SplitPane popup = new SplitPane();
        GridPane leftGrid = new GridPane();
        GridPane rightGrid = new GridPane();

        leftGrid.getStyleClass().add("grids");
        rightGrid.getStyleClass().add("grids");

        popup.getItems().addAll(leftGrid, rightGrid);
        popup.setId("popup");
        popup.getStylesheets().addAll("gui/popup.css");

        Text busId = new Text("\u010c\u00edslo vozu: " + "#" + String.valueOf(bus.getId()));
        Text busType = new Text("Typ vozidla: " + bus.getType());
        Text busCarrier = new Text("Dopravce: " + bus.getCarrier());
        Text busLine = new Text("Linka: " + bus.getLine().getId());
        Text firstStop = new Text("Po\u010d\u00e1te\u010dn\u00ed zast\u00e1vka: " + bus.getLine().getFirstStop().getId());
        Text lastStop = new Text("Kone\u010dn\u00e1 zast\u00e1vka: " + bus.getLine().getLastStop().getId());
        Text nextStop = new Text("N\u00e1sleduj\u00edc\u00ed zast\u00e1vka: ");

        leftGrid.add(busId, 0, 0);
        leftGrid.add(busType, 1, 0);
        leftGrid.add(busCarrier, 0, 1);
        leftGrid.add(busLine, 1, 1);
        leftGrid.getColumnConstraints().add(new ColumnConstraints(290));
        leftGrid.getRowConstraints().add(new RowConstraints(75));

        rightGrid.add(firstStop, 0, 0);
        rightGrid.add(lastStop, 1, 0);
        rightGrid.add(nextStop, 0, 1, 1, 1);
        rightGrid.getColumnConstraints().add(new ColumnConstraints(290));
        rightGrid.getRowConstraints().add(new RowConstraints(75));

        // styly
        busId.getStyleClass().add("text-id");
        busType.getStyleClass().add("text-id");
        busCarrier.getStyleClass().add("text-id");
        busLine.getStyleClass().add("text-id");
        firstStop.getStyleClass().add("text-id");
        lastStop.getStyleClass().add("text-id");
        nextStop.getStyleClass().add("text-id");

        // pozice
        /*
         * busId.setTranslateY(25); busId.setTranslateX(-90); busType.setTranslateY(25);
         * busType.setTranslateX(-90); busCarrier.setTranslateY(25);
         * busCarrier.setTranslateX(-90); busLine.setTranslateY(25);
         * busLine.setTranslateX(-90); bus5.setTranslateY(75); bus5.setTranslateX(-675);
         * firstStop.setTranslateY(75); firstStop.setTranslateX(-650);
         * bus6.setTranslateY(75); bus6.setTranslateX(-625); lastStop.setTranslateY(75);
         * lastStop.setTranslateX(-600);
         */

        this.popup = popup;

        return popup;
    }
   
   public SplitPane getPopup(){
       return this.popup;
   }

   public void display(GridPane gridPane){
       gridPane.add(getPopup(), 0, 2);
   }
}