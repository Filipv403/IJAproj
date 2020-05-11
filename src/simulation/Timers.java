package simulation;

import files.Bus;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Timers {
    private TextField textMinutes = null;
    private TextField textHours = null;
    private Label labelCurrentTime = null;
    private TextField textSimSpeed = null;

    private Timer currentTimeTimer;
    private LocalTime currentTime;

    private Timer busRedrawTimer;

    private ArrayList<Bus> busses;

    private float scale = 120;

    public void setGui(TextField textMinutes, TextField textHours, Label labelCurrentTime, TextField textSimSpeed) {
        this.textMinutes = textMinutes;
        this.textHours  = textHours;
        this.labelCurrentTime =labelCurrentTime;
        this.textSimSpeed = textSimSpeed;
    }

    public void setCurrentTime() {
        int newHour, newMinute;
        try {
            if (textMinutes == null || textHours == null)
                return;

            newHour = Integer.parseInt(textHours.getText());
            newMinute = Integer.parseInt(textMinutes.getText());

            if (newHour < 0 || newHour > 23 || newMinute < 0 || newMinute > 59)
                throw new NumberFormatException();

            currentTime = LocalTime.of(newHour, newMinute);
            updateTime();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid time format");
            alert.showAndWait();
        }
    }

    public void setSimSpeed() {
        float simSpeed;
        try {
            if (textSimSpeed == null)
                return;

            simSpeed = Float.parseFloat(textSimSpeed.getText());

            if (scale < 0.1 || scale > 1000)
                throw new NumberFormatException();

            scale = simSpeed;
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid speed format");
            alert.showAndWait();
        }
    }

    private void updateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        if (labelCurrentTime != null)
            labelCurrentTime.setText(currentTime.format(dtf));
        else
            return;
            //System.out.println(currentTime.format(dtf));
    }

    private void updateBusPos() {
        if (busses == null)
            return;

        for (Bus bus : busses) {
            bus.updatePos(currentTime);
        }
    }

    public void startTimers() {
        currentTime = LocalTime.of(10, 0);
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

        busRedrawTimer = new Timer(false);
        busRedrawTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateBusPos();
            }
        }, 0, 1000);
    }

    public void setBusses(ArrayList<Bus> busses) {
        this.busses = busses;
    }
}
