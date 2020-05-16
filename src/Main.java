import javafx.application.Application;
import javafx.stage.Stage;
import gui.*;
import loaded.*;

/**
 * Hlavní třída, která načte data a spustí výsledně vytvořené okno
 * 
 * @author Filip Václavík (xvacla30)
 * @author Michal Zobaník (xzoban01)
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //úvod
        Loaded data = new Loaded();
        Template.displayTemplate(data, "MHD");

    }
    
}