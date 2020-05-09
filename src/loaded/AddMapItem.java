package loaded;

import files.*;
import gui.*;

public interface AddMapItem {
    public static Coordinate addCoordinate(int x, int y){
        Coordinate c = new Coordinate();

        //misto setteru se bude nacitat ze souboru
        c.setXY(x, y);

        return c;
    }

    public static MyStreet setStreet(String name, int StartX, int StartY, int EndX, int EndY){
        MyStreet street = new MyStreet(name, AddMapItem.addCoordinate(StartX, StartY), AddMapItem.addCoordinate(EndX, EndY));
        return street;
    }
}