package files;

import gui.MyPopup;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;

public class AppData {
    private Bus selectedBus;
    private MyPopup popup;
    private CheckBox checkBox;
    private Spinner<Integer> trafficSpinner;
    private Label streetSetText;

    public void setSelectedBus(Bus selectedBus) {
        this.selectedBus = selectedBus;
    }

    public void setPopup(MyPopup popup) {
        this.popup = popup;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public void setTrafficSpinner(Spinner<Integer> trafficSpinner){
        this.trafficSpinner = trafficSpinner;
    }

    public void setStreetSetText(Label streetSetText){
        this.streetSetText = streetSetText;
    }

    public CheckBox getCheckBox() {
        return this.checkBox;
    }

    public Spinner<Integer> getTrafficSpinner(){
        return this.trafficSpinner;
    }

    public Label getStreetSetText(){
        return this.streetSetText;
    }

    public AppData() {
        selectedBus = null;
        popup = null;
        this.checkBox = null;
        this.trafficSpinner = null;
    }

    public void updatePopUp() {
        if (popup != null) {
            popup.update(this);
        }
    }

    public void deselectBus() {
        if (selectedBus != null) {
            selectedBus.deselect();
            selectedBus = null;
        }

        if (popup != null) {
            popup.notDisplay();
            popup = null;
        }
    }
}
