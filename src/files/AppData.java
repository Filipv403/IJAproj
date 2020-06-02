package files;

import gui.MyPopup;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.paint.Color;

public class AppData {
    private Bus selectedBus;
    private MyStreet selectedStreet;
    private MyPopup popup;
    private boolean isEditingDetour;
    private Detour newDetour;
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

    public Street getSelectedStreet() {
        return selectedStreet;
    }

    public void setSelectedStreet(MyStreet selectedStreet) {
        this.selectedStreet = selectedStreet;
    }

    public AppData() {
        selectedBus = null;
        popup = null;
        isEditingDetour = false;
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

    public void deselectStreet() {
        if (selectedStreet != null) {
            selectedStreet.setTraffic(trafficSpinner.getValue());
            selectedStreet.deselect();
        }


        selectedStreet = null;
        checkBox.setSelected(false);
        trafficSpinner.getValueFactory().setValue(1);
        streetSetText.setText("Nastavení Ulice: Žádná nevybrána");
    }

    public boolean isEditingDetour() {
        return isEditingDetour;
    }

    public void toggleEditDetour() {
        isEditingDetour = !isEditingDetour;
    }

    public void createDetour() {
        newDetour = new Detour();
    }

    public void finalizeDetour() {
        if (newDetour.finalize(selectedBus.getLine())) {
            System.out.println("Objížďka přidána");
        } else {
            System.out.println("Objížďku se nepovedlo vytvořit");
        }
    }

    public MyLine getSelectedLine() {
        if (selectedBus != null) {
            return selectedBus.getLine();
        }

        return null;
    }

    public void cancleDetourEdit() {
        isEditingDetour = false;
        newDetour = null;
    }

    public Detour getNewDetour() {
        return newDetour;
    }
}
