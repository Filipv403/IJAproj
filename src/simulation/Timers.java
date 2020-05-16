package simulation;

import files.Bus;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Třída starající se o aktualizaci pozice autobusů a času simulace
 *
 * @author Michal Zobaník (xzoban01)
 */
public class Timers {
    private TextField textTime = null;
    private Label labelCurrentTime = null;
    private Label labelSimSpeed = null;
    private Slider sliderSimSpeed = null;

    private Timer currentTimeTimer;
    private LocalTime currentTime;

    private Timer busRedrawTimer;

    private ArrayList<Bus> busses;

    private double scale = 1;

    /**
     * Nastací UI pro správu Timeru
     *
     * @param time Textové pole pro nastavení aktuálního času
     * @param labelSimSpeed Label pro nastavení rychlosti simulace
     * @param labelCurrentTime Label pro zobrazení aktuálního času simulace
     * @param textSimSpeed Testové pole pro nastavení aktuální rychlosti simulace
     */
    public void setGui(TextField time, Label labelSimSpeed, Label labelCurrentTime, Slider textSimSpeed) {
        this.textTime = time;
        this.labelSimSpeed = labelSimSpeed;
        this.labelCurrentTime =labelCurrentTime;
        this.sliderSimSpeed = textSimSpeed;
    }

    /**
     * Nastaví aktuální čas simulace
     */
    public void setCurrentTime() {
        LocalTime newTime;
        try {
            if (textTime == null)
                return;

            newTime = LocalTime.parse(textTime.getText());

            currentTime = newTime;
            updateTime();

        } catch (java.time.format.DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Očekáván čas ve formátu: hh:mm:ss");
            alert.showAndWait();
        }
    }

    /**
     * Nastaví akuální rychlost simulace v rozmezí (0 - 3600)
     */
    public void setSimSpeed() {
        double simSpeed;
        if (sliderSimSpeed == null)
            return;

        simSpeed = sliderSimSpeed.getValue();

        scale = simSpeed;

        if (labelSimSpeed == null)
            return;

        labelSimSpeed.setText("Rychlost simulace: " + (long)scale + "x");
    }

    public LocalTime getCurrentTime(){
        return this.currentTime;
    }

    /**
     * Aktualizuje zobrazovaný čas
     */
    private void updateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        if (labelCurrentTime != null)
            labelCurrentTime.setText("Akuální čas: " + currentTime.format(dtf));
        else
            return;
            //System.out.println(currentTime.format(dtf));
    }

    /**
     * Aktualizuje pozici všech autobusů na mapě
     */
    private void updateBusPos() {
        if (busses == null)
            return;

        for (Bus bus : busses) {
            bus.updatePos(currentTime);
        }
    }

    /**
     * Spustí časovač pro aktualizaci pozice autobusů a auktualizaci času simulace
     */
    public void startTimers() {
        currentTime = LocalTime.of(10,10);
        currentTimeTimer = new Timer(false);
        currentTimeTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    currentTime = currentTime.plusNanos((long)(100000000 * scale));
                    updateTime();
                });
            }
        }, 0, 100);

        //Timer pro aktualizaci pozice autobusu
        busRedrawTimer = new Timer(false);
        busRedrawTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateBusPos();
            }
        }, 0, 1000);
    }

    /**
     * Nastaví autobusy, jejichž pozice se bude aktualiovat
     *
     * @param busses Pole s autobusy pro aktualizaci polohy
     */
    public void setBusses(ArrayList<Bus> busses) {
        this.busses = busses;
    }
}
