package loaded;

import files.*;
import gui.*;

public interface AddBoxItem {

    public static BusBox itemBus(BusBox busBox){
        Bus bus = new Bus();

        //misto setteru se bude nacitat ze souboru
        bus.setId(1);
        bus.setType("Volvo");
        bus.setCarrier("Vojtila");

        //vlozeni
        busBox.addBusFile(bus);

        return busBox;
    }

    public static StopBox itemStop(StopBox stopBox){
        MyStop stop = new MyStop();

        //misto setteru se bude nacitat ze souboru, souradnice a ulice jeste
        stop.setId("Deckerova");

        //vlozeni
        stopBox.addStopFile(stop);

        return stopBox;
    }

    public static LineBox itemLine(LineBox lineBox){
        MyLine line = new MyLine();

        //misto setteru se bude nacitat ze souboru, pridat seznam jeste
        line.setId("\u010d\u00edslo 2");

        //vlozeni
        lineBox.addLineFile(line);

        return lineBox;
    }

}