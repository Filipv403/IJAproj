package gui;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import gui.*;
import loaded.*;

public class AlertBox {
    private List<File> files;

    public static AlertBox display(String display){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Warning");

        Label label1 = new Label();
        Label label2 = new Label();
        label1.setText("Nelze nacist datove soubory");
        label2.setText("Kliknutim na tlacitko open vyberte z adresare soubor s nazvem " + display);
        label2.wrapTextProperty();

        AlertBox click = new AlertBox();
        Button openBtn = new Button("Open");
        openBtn.setOnAction(e -> {
            click.openFiles(e);
            window.close();
        });
        window.setOnCloseRequest(e -> {
            window.close();
        });
        Button conBtn = new Button("Continue");
        conBtn.setOnAction(e -> window.close());

        HBox layout1 = new HBox(20);
        layout1.getChildren().addAll(openBtn, conBtn);
        layout1.setAlignment(Pos.CENTER);
        VBox layout2 = new VBox(10);
        layout2.getChildren().addAll(label1, label2);
        layout2.setAlignment(Pos.CENTER);
        label1.setAlignment(Pos.CENTER);
        label2.setAlignment(Pos.CENTER);

        layout1.getStylesheets().addAll("gui/alertBox.css");
        layout2.getStylesheets().addAll("gui/alertBox.css");

        BorderPane layout = new BorderPane();
        layout.setTop(layout2);
        layout.setCenter(layout1);

        Scene scene = new Scene(layout, 500, 250);
        window.setScene(scene);
        window.showAndWait();
        return click;
    }

    public static AlertBox displayA(String display){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Warning");

        Label label = new Label();
        label.setText("Vyberte z adresare soubory " + display);
        label.wrapTextProperty();

        AlertBox click = new AlertBox();
        Button openBtn = new Button("Continue");
        openBtn.setOnAction(e -> {
            click.openFiles(e);
            window.close();
        });
        window.setOnCloseRequest(e -> {
            window.close();
        });

        HBox layout1 = new HBox(20);
        layout1.getChildren().addAll(openBtn);
        layout1.setAlignment(Pos.CENTER);
        VBox layout2 = new VBox(10);
        layout2.getChildren().addAll(label);
        layout2.setAlignment(Pos.CENTER);
        label.setAlignment(Pos.CENTER);

        layout1.getStylesheets().addAll("gui/alertBox.css");
        layout2.getStylesheets().addAll("gui/alertBox.css");

        BorderPane layout = new BorderPane();
        layout.setTop(layout2);
        layout.setCenter(layout1);

        Scene scene = new Scene(layout, 400, 250);
        window.setScene(scene);
        window.showAndWait();
        return click;
    }

    public void openFiles(ActionEvent event){
        FileChooser fc = new FileChooser();
        String currentDir = Paths.get("../").toAbsolutePath().normalize().toString();
        fc.setInitialDirectory(new File(currentDir));
        fc.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"));
        List<File> files = fc.showOpenMultipleDialog(null);
        if (files != null) {
            this.files = files;
        }
    }

    /**
     * @return the files
     */
    public List<File> getFiles() {
        return this.files;
    }
}