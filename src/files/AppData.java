package files;

import gui.MyPopup;

public class AppData {
    private Bus selectedBus;
    private MyPopup popup;

    public void setSelectedBus(Bus selectedBus) {
        this.selectedBus = selectedBus;
    }

    public void setPopup(MyPopup popup) {
        this.popup = popup;
    }

    public AppData() {
        selectedBus = null;
        popup = null;
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
