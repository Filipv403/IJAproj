import javafx.application.Application;
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
import files.*;
import gui.*;

public class Main extends Application implements EventHandler<ActionEvent> {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //úvod
        Template.displayTemplate("MHD");

    }

    @Override
    public void handle(ActionEvent event) {
        // TODO Auto-generated method stub

    }
    
}