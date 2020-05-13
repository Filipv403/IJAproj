package files;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MyStop implements Stop {
	private String id;
    private Coordinate c;
    private Street s;
    private Rectangle mapRect;
    
    public MyStop() {
        this.id = "";
    }

    public MyStop(String id) {
        this.id = id;
    }

    public MyStop(String id, Coordinate c) {
        this.id = id;
        this.c = c;
    }

    public String getId(){
        return this.id;
    }

    public Coordinate getCoordinate(){
        if(c != null){
            return this.c;
        }
        return null;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setStreet(Street s){
		this.s = s;
    }


    public Street getStreet(){
		return this.s;
    }
    //nastaví výchozí zastávku
    public static Stop defaultStop(String id, Coordinate c){
        Stop stop = new MyStop(id, c);
        return stop;
    }

    @Override
    public boolean equals(Object o){
        Stop stop = (Stop) o;
        return id.equals(stop.getId());
    }

    @Override
    public String toString(){
        try {
            return "" + this.id + " na ulici: " + s.getId();
        } catch (Exception e) {
            return "" + this.id + "";
        }
    }

    public void setMapRect(Rectangle mapRect) {
        this.mapRect = mapRect;
    }

    public void deselect() {
        if (mapRect != null) {
            mapRect.setFill(Color.RED);
            mapRect.setStroke(Color.RED);
        }
    }

    public void select() {
        if (mapRect != null) {
            mapRect.setFill(Color.rgb(14, 41, 0));
            mapRect.setStroke(Color.rgb(14, 41, 0));
        }
    }
}