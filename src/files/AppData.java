package files;

import gui.MyPopup;

public class AppData {
    private Bus selectedBus;
    private MyPopup popup;
    private boolean isEditingDetour;
    private Detour newDetour;

    public void setSelectedBus(Bus selectedBus) {
        this.selectedBus = selectedBus;
    }

    public void setPopup(MyPopup popup) {
        this.popup = popup;
    }

    public AppData() {
        selectedBus = null;
        popup = null;
        isEditingDetour = false;
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
