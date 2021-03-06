package gui;

import files.*;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

import java.time.format.DateTimeFormatter;

/**
 * Vyskakovací okénko s údajema autobusu, po kliknutí na autobus
 *
 * @author Filip Václavík (xvacla30)
 * @author Michal Zobaník (xzoban01)
 */
public class MyPopup {
    /**
     * Deklarace vyskakovacího panelu
     */
    private SplitPane popup;
    private GridPane gridPane;
    private Text nextStopField;
    private Text delay;
    private Bus selectedBus;

    /**
     * Načte vyskakovací okénko a uloží ho 
     * 
     * @param bus načte autobus, ze kterého si bere informace
     * @return popup okno
     */
    public SplitPane load(Bus bus) {
        this.selectedBus = bus;
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
        Text nextStop = new Text("N\u00e1sleduj\u00edc\u00ed zast\u00e1vka: " + bus.getNextStop().getKey().getId() + " (" + bus.getNextStop().getValue().format(DateTimeFormatter.ofPattern("HH:mm")) + ")");
        this.delay = new Text("Zpoždění: " + bus.getActDelay() / 60 + " min.");
        this.nextStopField = nextStop;

        GridPane.setHalignment(busId, HPos.CENTER);
        GridPane.setHalignment(busType, HPos.CENTER);
        GridPane.setHalignment(busCarrier, HPos.CENTER);
        GridPane.setHalignment(busLine, HPos.CENTER);
        GridPane.setHalignment(firstStop, HPos.CENTER);
        GridPane.setHalignment(lastStop, HPos.CENTER);
        GridPane.setHalignment(nextStop, HPos.CENTER);
        GridPane.setHalignment(delay, HPos.CENTER);

        leftGrid.add(busId, 0, 0);
        leftGrid.add(busType, 1, 0);
        leftGrid.add(busCarrier, 0, 1);
        leftGrid.add(busLine, 1, 1);
        leftGrid.getColumnConstraints().add(new ColumnConstraints(290));
        leftGrid.getRowConstraints().add(new RowConstraints(75));

        rightGrid.add(firstStop, 0, 0);
        rightGrid.add(lastStop, 1, 0);
        rightGrid.add(nextStop, 0, 1, 2, 1);
        rightGrid.add(delay, 0, 2, 2, 1);
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
        delay.getStyleClass().add("text-id");

        this.popup = popup;

        return popup;
    }
   
    /**
     * @return popup okénko
     */
    public SplitPane getPopup(){
        return this.popup;
    }

    /**
     * 
     * @param gridPane panel, do kterého se má přidat vyskakovací okno
     */
    public void display(GridPane gridPane){
        this.gridPane = gridPane;
        gridPane.add(getPopup(), 0, 2);
    }

    /**
     * Zruší zobrazení vyskakovacího okna
     */
    public void notDisplay() {
        gridPane.getChildren().removeAll(getPopup());
    }

    /**
     * Aktualizuje informace o následující zastávce autobusu autobusu
     */
    public void update(AppData data) {
        if (!selectedBus.getCircle().isVisible()) {
            data.deselectBus();
            return;
        }

        nextStopField.setText("N\u00e1sleduj\u00edc\u00ed zast\u00e1vka: " + selectedBus.getNextStop().getKey().getId() + " (" + selectedBus.getNextStop().getValue().format(DateTimeFormatter.ofPattern("HH:mm")) + ")");
        delay.setText("Zpoždění: " + selectedBus.getActDelay() / 60 + " min.");
    }
}