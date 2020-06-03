package files;

import gui.MyPopup;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

/**
 * Třída uchovávající informace o aplikaci
 *
 * @author Filip Václavík (xvacla30)
 * @author Michal Zobaník (xzoban01)
 */
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

    /**
     * Nastaví vybraný autobus
     * @param selectedBus nový vybraný autobus
     */
    public void setSelectedBus(Bus selectedBus) {
        this.selectedBus = selectedBus;
    }

    /**
     * Nastaví odkaz na popUp s informacemi o autobusu
     * @param popup odkaz na popup
     */
    public void setPopup(MyPopup popup) {
        this.popup = popup;
    }

    /**
     * Nastaví odkaz na checkbox pro uzavírání/otevírání ulic
     * @param checkBox odkaz na checkbox
     */
    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    /**
     * Nastaví odkaz na spinner pro nastavení úrovně provozu
     * @param trafficSpinner odkaz na spinner
     */
    public void setTrafficSpinner(Spinner<Integer> trafficSpinner){
        this.trafficSpinner = trafficSpinner;
    }

    /**
     * Nastaví odkaz na label s názvem vybrané ulice
     * @param streetSetText odkaz na label
     */
    public void setStreetSetText(Label streetSetText){
        this.streetSetText = streetSetText;
    }

    /**
     * Získá odkaz na checkbox s nastavením provozu
     * @return odkaz na checkbox
     */
    public CheckBox getCheckBox() {
        return this.checkBox;
    }

    /**
     * Získá odkaz na spinner s nastavením úrovně provozu
     * @return odkaz na spinner
     */
    public Spinner<Integer> getTrafficSpinner(){
        return this.trafficSpinner;
    }

    /**
     * Získá odkaz na label s názvem vybrané ulice
     * @return odkaz na label
     */
    public Label getStreetSetText(){
        return this.streetSetText;
    }

    /**
     * Získá aktuálně vybranou ulici
     * @return ulice
     */
    public Street getSelectedStreet() {
        return selectedStreet;
    }

    /**
     * Nastaví aktuálně vybranou ulici
     * @param selectedStreet vybraná ulice
     */
    public void setSelectedStreet(MyStreet selectedStreet) {
        this.selectedStreet = selectedStreet;
    }

    /**
     * Konstruktor
     */
    public AppData() {
        selectedBus = null;
        popup = null;
        isEditingDetour = false;
        this.checkBox = null;
        this.trafficSpinner = null;
    }

    /**
     * Provede aktualizaci popupu s informacemi o vybraném autobusu
     */
    public void updatePopUp() {
        if (popup != null) {
            popup.update(this);
        }
    }

    /**
     * Zruší vybrání a označení autobusu
     */
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

    /**
     * Zruší vybrání a označení vybrané silnice
     */
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

    /**
     * Získá informaci o tom jestli dochází k vytváření nové objížďky
     * @return true potom probíhá vytváření novéobjížďky
     */
    public boolean isEditingDetour() {
        return isEditingDetour;
    }

    /**
     * Přepne informaci o tom jestli dochází k vytváření nové objížďky
     */
    public void toggleEditDetour() {
        isEditingDetour = !isEditingDetour;
    }

    /***
     * Vytvoří novou objížďku
     */
    public void createDetour() {
        newDetour = new Detour();
    }

    /**
     * Provede finalizaci vytvářené objížďky a dokončení její editace
     */
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

    /**
     * Získá aktuálně vybranou linku
     * @return aktuálně vybraná linka
     */
    public MyLine getSelectedLine() {
        if (selectedBus != null) {
            return selectedBus.getLine();
        }

        return null;
    }

    /**
     * Zruší editaci nové objížďky
     */
    public void cancleDetourEdit() {
        isEditingDetour = false;
        if (newDetour != null)
            newDetour.remove();
        newDetour = null;
        removeDetour.setDisable(false);
        detourComboBox.setDisable(false);
    }

    /**
     * Získá aktuálně vytvářenou bjížďku
     * @return aktuálně vytvářená objížďka
     */
    public Detour getNewDetour() {
        return newDetour;
    }

    /**
     * Nataví odkaz na Label pro zobrazení vybrané linky
     * @param lineSetText odkaz na label
     */
    public void setLineSetText(Label lineSetText) {
        this.lineSetText = lineSetText;
    }

    /**
     * Získá odkaz na combobox se seznamem objížděk vybrané linky
     * @return odkaz na combobox
     */
    public ComboBox<Detour> getDetourComboBox() {
        return detourComboBox;
    }

    /**
     * Získá odkza na label pro zobrazení vybrané linky
     * @return odkza na label
     */
    public Label getLineSetText() {
        return lineSetText;
    }

    /**
     * Nastaví odkaz na combobox pro zobrazení seznamu objížděk
     * @param detourComboBox odkaz na combobox
     */
    public void setDetourComboBox(ComboBox<Detour> detourComboBox) {
        this.detourComboBox = detourComboBox;
    }

    /**
     * Nastaví odkaz na tlačítko pro odstranění objížďky
     * @param removeDetour odkza na tlačítko
     */
    public void setRemoveDetour(Button removeDetour) {
        this.removeDetour = removeDetour;
    }
}
