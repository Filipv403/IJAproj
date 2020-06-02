package files;

import gui.MyPopup;
import javafx.scene.control.*;
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
    private Label lineSetText;
    private ComboBox<Detour> detourComboBox;
    private Button removeDetour;

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

            detourComboBox.getItems().remove(0, detourComboBox.getItems().size());
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
        checkBox.setText("Zavřená");
        checkBox.setDisable(false);
        trafficSpinner.getValueFactory().setValue(1);
        streetSetText.setText("Nastavení Ulice: Žádná nevybrána");
        lineSetText.setText("Nastavení objížďky pro linku:\n\tŽádná nevybrána");
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
            selectedBus.getLine().deselect();
        } else {
            newDetour.remove();
            newDetour = null;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Přidání objížďky");
            alert.setHeaderText(null);
            alert.setContentText("Objížďka byla chybně vytvořena a nebyla přidána");

            alert.showAndWait();
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
        if (newDetour != null)
            newDetour.remove();
        newDetour = null;
        removeDetour.setDisable(false);
        detourComboBox.setDisable(false);
    }

    public Detour getNewDetour() {
        return newDetour;
    }

    public void setLineSetText(Label lineSetText) {
        this.lineSetText = lineSetText;
    }

    public ComboBox<Detour> getDetourComboBox() {
        return detourComboBox;
    }

    public Label getLineSetText() {
        return lineSetText;
    }

    public void setDetourComboBox(ComboBox<Detour> detourComboBox) {
        this.detourComboBox = detourComboBox;
    }

    public void setRemoveDetour(Button removeDetour) {
        this.removeDetour = removeDetour;
    }
}
